package com.zeiss.patient.client.gui.openpatient;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.patient.client.gui.localeservice.LocaleService;
import com.zeiss.patient.client.gui.update.PatientUpdate;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class OpenPatient {

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
    private ScrollPane scrollPane;
    @FXML
    private Slider slider;
    @FXML
    private ChoiceBox<String> chooseDocumentChoiceBox;


    private BorderPane borderPane;

    public OpenPatient() {
    }

    @Inject
    private Provider<PatientUpdate> patientUpdate;
    @Inject
    private Provider<PatientVisit> patientVisitProvider;
    @Inject
    private PatientService patientService;
    @Inject
    private LocaleService localeService;

    private File file;

    @FXML
    public void initialize() {
        //do nothing
    }

    Map<PatientVisit, List<File>> patientVisitFileMap = new HashMap<>();

    public void showPatientDialog(Patient patient, Stage parentStage,
                                  Runnable runnable) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Open Patient Dialog");

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

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
            reload(patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()));
            checkForCurrentDate(patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()), now);
        });
        uploadDocument.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showOpenDialog(parentStage);
                if (file != null) {
                    Optional<? extends PatientVisit> patientVisit = getCurrentPatientVisit(patientService.getVisitPatientsByFirstNameAndLastName(patient.getFirstName(), patient.getLastName()), LocalDate.now());
                    if (patientVisit.isPresent()) {
                        List<File> files = patientVisitFileMap.get(patientVisit.get());
                        if (files == null || files.size() == 0) {
                            files = new ArrayList<>();
                        }
                        files.add(file);
                        patientVisitFileMap.put(patientVisit.get(), files);
                        reload(visitPatientsByFirstNameAndLastName);
                        chooseDocumentChoiceBox.getItems().clear();
                        List<String> collect = patientVisitFileMap.values().stream()
                                .flatMap(Collection::stream).map(file1 -> file1.toURI().toString())
                                .collect(Collectors.toList());
                        chooseDocumentChoiceBox.getItems().addAll(collect);
                        chooseDocumentChoiceBox.getSelectionModel().select(file.toURI().toString());
                        loadImageView(chooseDocumentChoiceBox.getSelectionModel().getSelectedItem());
                    }
                }
                System.out.println(file);

            }
        });

        chooseDocumentChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    loadImageView(newValue);
                });

        close.setOnAction(event -> close(dialog));


        List<String> collect = patientVisitFileMap.values().stream()
                .flatMap(Collection::stream).map(file1 -> file1.toURI().toString())
                .collect(Collectors.toList());
        chooseDocumentChoiceBox.getItems().addAll(collect);
        loadImageView(chooseDocumentChoiceBox.getSelectionModel().getSelectedItem());

        reload(visitPatientsByFirstNameAndLastName);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        dialog.showAndWait();

    }

    public void checkForCurrentDate(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, LocalDate now) {
        Optional<? extends PatientVisit> any = getCurrentPatientVisit(visitPatientsByFirstNameAndLastName, now);
        if (any.isPresent()) {
            createVisit.setDisable(true);
            uploadDocument.setDisable(false);
        } else {
            createVisit.setDisable(false);
            uploadDocument.setDisable(true);
        }
    }

    public Optional<? extends PatientVisit> getCurrentPatientVisit(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName, LocalDate now) {
        return visitPatientsByFirstNameAndLastName.stream().filter(patientVisit ->
                patientVisit.getPatientVisitDate().isEqual(now)).findAny();
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void loadImageView(String item) {
        if (item != null && !item.isEmpty()) {
            Image image = new Image(item);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            slider.valueProperty().addListener((ev, oldv, newvalue) -> {
                imageView.setScaleX((double) newvalue);
                imageView.setScaleY((double) newvalue);
            });

            Group zoomNode = new Group(imageView);
            scrollPane.setContent(zoomNode);

            scrollPane.setOnScroll(e -> {
                e.consume();
                onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()), zoomNode, imageView);
            });
        }
    }

    private void reload(List<? extends PatientVisit> visitPatientsByFirstNameAndLastName) {
        TreeItem<Object> treeItem = new TreeItem();
        List<TreeItem<Object>> treeItemList = visitPatientsByFirstNameAndLastName.stream().map(patientVisit -> {
            TreeItem<Object> child = new TreeItem<Object>();
            child.setValue(patientVisit);
            patientVisitFileMap.entrySet().stream().forEach(patientVisitListEntry -> {
                if (patientVisit.getPatientVisitDate().compareTo(LocalDate.now()) == 0) {
                    TreeItem<Object> childIten = new TreeItem<Object>();
                    childIten.setValue(patientVisitListEntry.getValue());
                    child.getChildren().addAll(childIten);
                }
            });
            return child;
        }).collect(Collectors.toList());
        treeItem.getChildren().addAll(treeItemList);
        treeView.setRoot(treeItem);
    }

    private double scaleValue = 0.7;

    private void updateScale(ImageView imageView) {
        imageView.setScaleX(scaleValue);
        imageView.setScaleY(scaleValue);
    }

    private double zoomIntensity = 0.02;

    private void onScroll(double wheelDelta, Point2D mousePoint, Node zoomNode, ImageView imageView) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = scrollPane.getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = scrollPane.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = scrollPane.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale(imageView);
        scrollPane.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = imageView.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = imageView.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        scrollPane.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        scrollPane.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
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
