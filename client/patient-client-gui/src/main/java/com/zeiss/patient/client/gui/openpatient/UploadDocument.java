package com.zeiss.patient.client.gui.openpatient;

import com.google.inject.Inject;
import com.zeiss.document.service.api.Document;
import com.zeiss.document.service.api.DocumentService;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.zeiss.patient.client.gui.openpatient.OpenPatient.RTFX_FILE_EXTENSION;

public class UploadDocument {

    public static final String PROPERTIES = "properties";
    private BorderPane borderPane;

    @FXML
    private Button save;
    @FXML
    private Button uploadDocument;
    @FXML
    private Button cancel;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextField columnSpacing;
    @FXML
    private TextField uploadDocumentTextField;
    @FXML
    private TextField rowSpacing;
    @FXML
    private TextField widthInMm;
    @FXML
    private TextField heightinMM;
    @FXML
    private Slider slider;

    @Inject
    private LocaleService localeService;
    @Inject
    private DocumentService documentService;
    @Inject
    private PatientService patientService;
    private File file;

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showPatientDialog(Patient patient, Stage parentStage,
                                  ChoiceBox<String> chooseDocumentChoiceBox, TreeView treeView, Slider parentSlider, ScrollPane parentScrollPane, Runnable runnable) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Open Patient Dialog");

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        uploadDocument.setOnAction(arg0 -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            file = fileChooser.showOpenDialog(parentStage);
            if (file != null) {
                try {

                    uploadDocumentTextField.textProperty().set(file.getPath());
                    byte[] fileContent = FileUtils.readFileToByteArray(file);
                    loadImage(fileContent, slider, scrollPane);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        cancel.setOnAction(event -> close(dialog, runnable));

        save.setOnAction(event -> saveDocument(patient, chooseDocumentChoiceBox, treeView, parentSlider,
                parentScrollPane, dialog, runnable));

        dialog.getDialogPane().setPrefSize(1100, 900);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        dialog.showAndWait();
    }

    public void saveDocument(Patient patient, ChoiceBox<String> chooseDocumentChoiceBox, TreeView treeView,
                             Slider parentSlider, ScrollPane parentScrollPane, Dialog<String> dialog, Runnable runnable) {
        Optional<? extends PatientVisit> patientVisit = getCurrentPatientVisit(patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(),
                patient.getLastName()), LocalDate.now());
        if (patientVisit.isPresent()) {

            try {
                documentService.uploadDocument(Integer.parseInt(patient.getId()),
                        Integer.parseInt(patientVisit.get().getId()), FileUtils.readFileToByteArray(file),
                        FilenameUtils.removeExtension(file.getName()), FilenameUtils.getExtension(file.getName()));

                Properties prop = new Properties();

                prop.put("columnSpacing", columnSpacing.getText());
                prop.put("rowSpacing", rowSpacing.getText());
                prop.put("widthInMm", widthInMm.getText());
                prop.put("heightinMM", heightinMM.getText());

                documentService.uploadDocument(Integer.parseInt(patient.getId()),
                        Integer.parseInt(patientVisit.get().getId()), getBytes(prop),
                        FilenameUtils.removeExtension(file.getName()) + "_properties", PROPERTIES);

            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Document> documents = documentService.getDocuments(patient.getId());
            reload(treeView, patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()), documents);
            chooseDocumentChoiceBox.getItems().clear();
            List<String> collect = documents.stream().filter(document -> !document.getFileExtension().equalsIgnoreCase(RTFX_FILE_EXTENSION)
                    && !document.getFileExtension().equalsIgnoreCase(PROPERTIES))
                    .map(Document::getFileName).collect(Collectors.toList());


            chooseDocumentChoiceBox.getItems().addAll(collect);
            chooseDocumentChoiceBox.getSelectionModel().select(file.getName());
            loadImageView(parentScrollPane, chooseDocumentChoiceBox.getSelectionModel().getSelectedItem(), documents, parentSlider);
            close(dialog, runnable);
        }
    }

    private byte[] getBytes(Properties properties) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            properties.store(byteArrayOutputStream, "");
        } catch (IOException e) {
        }

        return byteArrayOutputStream.toByteArray();
    }

    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);

    public void loadImage(byte[] fileContent, Slider slider, ScrollPane scrollPane) {
        ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
        //fileNameToDocument.clear();
        BufferedImage bImage2 = null;
        ImageView imageView = new ImageView();

        final Image image = getImage(bis, imageView);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        save.disableProperty().bind(columnSpacing.textProperty().isEmpty().and(rowSpacing.textProperty().isEmpty().and(widthInMm.textProperty().isEmpty().and(heightinMM.textProperty().isEmpty()).and(imageView.imageProperty().isNull()))));

        columnSpacing.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 0.0) {
                widthInMm.setText(String.valueOf(image.getWidth() * Double.parseDouble(newValue)));
            } else {
                widthInMm.clear();
            }
        });

        rowSpacing.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 0.0) {
                heightinMM.setText(String.valueOf(image.getHeight() * Double.parseDouble(newValue)));
            } else {
                heightinMM.clear();
            }
        });

        widthInMm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 0.0) {
                columnSpacing.setText(String.valueOf(Double.parseDouble(newValue) /
                        (image.getWidth())));
            } else columnSpacing.clear();
        });

        heightinMM.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && Double.parseDouble(newValue) > 0.0) {
                rowSpacing.setText(String.valueOf(Double.parseDouble(newValue) /
                        (image.getHeight())));
            } else rowSpacing.clear();
        });


/*        Group zoomNode = new Group(imageView);
        scrollPane.setContent(zoomNode);

        scrollPane.setOnScroll(e -> {
            e.consume();
            OnScrollEvent.onScroll(scrollPane, e.getTextDeltaY(), new Point2D(e.getX(), e.getY()), zoomNode, imageView);
        });*/

        slider.valueProperty().addListener((ev, oldv, newvalue) -> {
            imageView.setScaleX((double) newvalue);
            imageView.setScaleY((double) newvalue);
        });

        scrollPane.setPannable(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        AnchorPane.setTopAnchor(scrollPane, 10.0d);
        AnchorPane.setRightAnchor(scrollPane, 10.0d);
        AnchorPane.setBottomAnchor(scrollPane, 10.0d);
        AnchorPane.setLeftAnchor(scrollPane, 10.0d);

        AnchorPane root = new AnchorPane();

        Rectangle rect = new Rectangle(80, 60);

        rect.setStroke(Color.NAVY);
        rect.setFill(Color.NAVY);
        rect.setStrokeType(StrokeType.INSIDE);

        final Group group = new Group(imageView);
        // create canuser   uservas
        PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
        zoomProperty.bind(panAndZoomPane.myScale);
        deltaY.bind(panAndZoomPane.deltaY);
        panAndZoomPane.getChildren().add(group);

        SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

        scrollPane.setContent(panAndZoomPane);
        panAndZoomPane.toBack();
        scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scrollPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scrollPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

    }

    public Image getImage(ByteArrayInputStream bis, ImageView imageView) {
        Image image = null;
        BufferedImage bImage2;
        try {
            bImage2 = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(bImage2, null);
            imageView.setImage(image);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private void loadImageView(ScrollPane parentScrollPane, String item, List<Document> documents, Slider slider) {

        Map<String, Document> fileNameToDocument = new HashMap<>();

        if (item != null && !item.isEmpty()) {

            fileNameToDocument.clear();

            documents.forEach(document -> {
                fileNameToDocument.put(document.getFileName(), document);
            });

            byte[] fileContent = fileNameToDocument.get(item).getFileContent();
            loadImage(fileContent, slider, parentScrollPane);
        }
    }

    public Optional<? extends PatientVisit> getCurrentPatientVisit(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, LocalDate now) {
        return visitPatientsByFirstNameAndLastName.stream().filter(patientVisit ->
                patientVisit.getPatientVisitDate().isEqual(now)).findAny();
    }

    private void reload(TreeView treeView, List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, List<Document> documents) {
        TreeItem<Object> treeItem = new TreeItem();
        treeView.setShowRoot(false);
        List<TreeItem<Object>> treeItemList = visitPatientsByFirstNameAndLastName.stream().map(patientVisit -> {
            TreeItem<Object> child = new TreeItem<Object>();
            child.setValue(patientVisit.getPatientVisitDate());
            documents.forEach(document -> {
                if (Integer.parseInt(patientVisit.getId()) == (document.getPatientVisitId()) && !document.getFileExtension().equals(RTFX_FILE_EXTENSION)) {
                    TreeItem<Object> childIten = new TreeItem<Object>();
                    childIten.setValue(document.getFileName());
                    child.getChildren().addAll(childIten);
                }
            });
            child.setExpanded(true);
            return child;
        }).collect(Collectors.toList());
        treeItem.getChildren().addAll(treeItemList);
        treeView.setRoot(treeItem);
        treeView.getRoot().setExpanded(true);
    }

    private void close(Dialog<String> dialog, Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/UploadDocument.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
