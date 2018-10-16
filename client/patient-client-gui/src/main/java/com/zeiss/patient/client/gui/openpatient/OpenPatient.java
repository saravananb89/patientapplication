package com.zeiss.patient.client.gui.openpatient;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.zeiss.document.service.api.Document;
import com.zeiss.document.service.api.DocumentService;
import com.zeiss.patient.client.gui.PatientModel;
import com.zeiss.patient.client.gui.update.PatientUpdate;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

import static com.zeiss.patient.client.gui.openpatient.UploadDocument.PROPERTIES;

public class OpenPatient {

    public static final String RTFX_FILE_EXTENSION = "rtfx";
    public static final String METADATA = "_metadata.";
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField ageText;
    @FXML
    private DatePicker dobText;
    @FXML
    private Button edit;
    @FXML
    private TreeView treeView;
    @FXML
    private Button uploadDocument;
    @FXML
    private Button createVisit;
    @FXML
    private Button close;
    @FXML
    private Button diagnose;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Slider slider;
    @FXML
    private ChoiceBox<String> chooseDocumentChoiceBox;
    @FXML
    private TextField widthInMm;
    @FXML
    private TextField heightInMm;


    private BorderPane borderPane;

    private PatientModel patientModel;

    public OpenPatient() {
    }

    @Inject
    private Provider<PatientUpdate> patientUpdate;
    @Inject
    private Provider<PatientVisit> patientVisitProvider;
    @Inject
    private Provider<PatientDiagnose> patientDiagnoseProvider;
    @Inject
    private Provider<UploadDocument> uploadDocumentProvider;
    @Inject
    private Provider<LineMeasurement> lineMeasurementProvider;
    @Inject
    private PatientService patientService;
    @Inject
    private LocaleService localeService;
    @Inject
    private DocumentService documentService;

    @FXML
    public void initialize() {
        //do nothing
    }

    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);

    public void showPatientDialog(Patient patient, Stage parentStage,
                                  Runnable runnable, PatientModel patientModel) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Open Patient Dialog");

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        this.patientModel = patientModel;

        chooseDocumentChoiceBox.getItems().clear();
        firstNameText.textProperty().bindBidirectional(patient.firstNameProperty());
        lastNameText.textProperty().bindBidirectional(patient.lastNameProperty());
        dobText.valueProperty().bindBidirectional(patient.dobProperty());
        ageText.textProperty().bind(Bindings.createStringBinding(() -> {
            LocalDate value = dobText.getValue();
            if (value == null) {
                return null;
            }

            LocalDate now = LocalDate.now();

            return "" + Period.between(value, now).getYears();

        }, dobText.valueProperty()));
        patient.ageProperty().bind(ageText.textProperty());

        firstNameText.editableProperty().set(false);
        lastNameText.editableProperty().set(false);
        dobText.editableProperty().set(false);
        ageText.editableProperty().set(false);

        edit.setOnAction(event -> patientUpdate.get().showPatientDialog(patient, runnable, parentStage));

        List<? extends PatientVisit> visitPatientsByFirstNameAndLastName = patientService.getVisitPatientsByFirstNameAndLastName
                (patient.getFirstName(), patient.getLastName());

        checkForCurrentDate(visitPatientsByFirstNameAndLastName, LocalDate.now());

        createVisit.setOnAction(event -> {
            PatientVisit patientVisit = patientVisitProvider.get();
            patientVisit.setVisitPatientFirstName(patient.getFirstName());
            patientVisit.setVisitPatientLastName(patient.getLastName());
            LocalDate now = LocalDate.now();
            patientVisit.patientVisitDateProperty().set(now);
            patientService.createVisit(patientVisit);
            reload(patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()), documentService.getDocuments(patient.getId()));
            checkForCurrentDate(patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()), now);
        });
        uploadDocument.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                uploadDocumentProvider.get().showPatientDialog(patient, parentStage, chooseDocumentChoiceBox, treeView,
                        slider, scrollPane, OpenPatient.this::loadImagePixel);
            }
        });
        chooseDocumentChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    loadImageView(newValue, documentService.getDocuments(patient.getId()));
                });

        close.setOnAction(event -> close(dialog));

        diagnose.disableProperty().bind(chooseDocumentChoiceBox.getSelectionModel().selectedItemProperty().isNull());

        diagnose.setOnAction(event -> {
            Map<String, Document> fileNameToDocument = new HashMap<>();
            List<Document> documents = documentService.getDocuments(patient.getId());
            documents.forEach(document -> {
                fileNameToDocument.put(document.getFileName(), document);
            });
            String selectedItem = chooseDocumentChoiceBox.getSelectionModel().getSelectedItem();
            byte[] richTextContent = new byte[0];
            for (Document document : fileNameToDocument.values()) {
                if (document.getFileExtension().equals(RTFX_FILE_EXTENSION) && (FilenameUtils.removeExtension(selectedItem) +
                        METADATA + RTFX_FILE_EXTENSION).equals(document.getFileName())) {
                    richTextContent = document.getFileContent();
                }
            }
            patientDiagnoseProvider.get().showPatientDialog(parentStage, selectedItem, fileNameToDocument.get(selectedItem), richTextContent);
        });

        List<Document> documents = documentService.getDocuments(patient.getId());

        if (documents != null && documents.size() > 0) {

            List<String> fileNames = documents.stream().filter(document -> !document.getFileExtension().equalsIgnoreCase(RTFX_FILE_EXTENSION)
                    && !document.getFileExtension().equalsIgnoreCase(PROPERTIES))
                    .map(Document::getFileName).collect(Collectors.toList());

            chooseDocumentChoiceBox.getItems().addAll(fileNames);

            chooseDocumentChoiceBox.getSelectionModel().select(fileNames.get(0));

            loadImageView(chooseDocumentChoiceBox.getSelectionModel().getSelectedItem(), documentService.getDocuments(patient.getId()));

            reload(visitPatientsByFirstNameAndLastName, documents);
        } else {
            reload(visitPatientsByFirstNameAndLastName, documents);
        }

        dialog.getDialogPane().setPrefSize(1100, 900);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        dialog.showAndWait();

    }

    public void loadImagePixel() {
        Map<String, Document> fileNameToDocument = new HashMap<>();
        List<Document> documents = documentService.getDocuments(patientModel.getSelectedPatient().getId());
        documents.forEach(document -> {
            fileNameToDocument.put(document.getFileName(), document);
        });
        String selectedItem = chooseDocumentChoiceBox.getSelectionModel().getSelectedItem();

        for (Document document : fileNameToDocument.values()) {
            if (document.getFileExtension().equals(PROPERTIES) && (FilenameUtils.removeExtension(selectedItem) + "_properties."
                    + PROPERTIES).equals(document.getFileName())) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(document.getFileContent());
                    Properties properties = new Properties();
                    properties.load(bis);
                    widthInMm.setText(properties.getProperty("widthInMm"));
                    heightInMm.setText(properties.getProperty("heightinMM"));
                    bis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void checkForCurrentDate(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, LocalDate now) {
        Optional<? extends PatientVisit> any = getCurrentPatientVisit(visitPatientsByFirstNameAndLastName, now);
        boolean currentDatePresent = any.isPresent();
        createVisit.setDisable(currentDatePresent || !patientModel.isHasPatientWriteAccess());
        uploadDocument.setDisable(!currentDatePresent || !patientModel.isHasPatientWriteAccess());
        edit.setDisable(!patientModel.isHasPatientWriteAccess());
    }

    public Optional<? extends PatientVisit> getCurrentPatientVisit(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, LocalDate now) {
        return visitPatientsByFirstNameAndLastName.stream().filter(patientVisit ->
                patientVisit.getPatientVisitDate().isEqual(now)).findAny();
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }


    private void loadImageView(String item, List<Document> documents) {

        Map<String, Document> fileNameToDocument = new HashMap<>();

        if (item != null && !item.isEmpty()) {

            fileNameToDocument.clear();

            documents.forEach(document -> {
                fileNameToDocument.put(document.getFileName(), document);
            });

            byte[] fileContent = fileNameToDocument.get(item).getFileContent();
            ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
            //fileNameToDocument.clear();
            BufferedImage bImage2 = null;
            ImageView imageView = new ImageView();
            try {
                bImage2 = ImageIO.read(bis);
                Image image = SwingFXUtils.toFXImage(bImage2, null);
                imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);


      /*      Group zoomNode = new Group(imageView);
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

            Rectangle rect = new Rectangle(80, 60);

            rect.setStroke(Color.NAVY);
            rect.setFill(Color.NAVY);
            rect.setStrokeType(StrokeType.INSIDE);

            final Group group = new Group();

            StackPane stackPane = new StackPane();
            Button drawLine = new Button();
            drawLine.setTooltip(new Tooltip("drawLine"));
            stackPane.setAlignment(Pos.TOP_RIGHT);
            stackPane.getChildren().add(drawLine);

            drawLine.setOnAction(event -> lineMeasurementProvider.get().drawLine(imageView));

            group.getChildren().addAll(imageView, stackPane);
            // create canuser   uservas
            PanAndZoomPane panAndZoomPane = new PanAndZoomPane();
            zoomProperty.bind(panAndZoomPane.myScale);
            deltaY.bind(panAndZoomPane.deltaY);
            panAndZoomPane.getChildren().add(group);

            SceneGestures sceneGestures = new SceneGestures(panAndZoomPane);

            scrollPane.setContent(panAndZoomPane);

            stackPane.getStylesheets().
                    add(getClass().getResource("/sample/image-display.css").toExternalForm());
            drawLine.getStyleClass().add("drawline");
            panAndZoomPane.toBack();
            scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
            scrollPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
            scrollPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
            scrollPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        }
    }

    private void reload(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, List<Document> documents) {
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


    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/OpenPatient.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
