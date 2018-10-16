package com.zeiss.patient.client.gui.openpatient;

import com.google.inject.Inject;
import com.zeiss.document.service.api.Document;
import com.zeiss.document.service.api.DocumentService;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.StyledTextArea;
import org.fxmisc.richtext.TextExt;
import org.fxmisc.richtext.model.*;
import org.reactfx.SuspendableNo;
import org.reactfx.util.Tuple2;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.fxmisc.richtext.model.Codec.styledTextCodec;
import static org.fxmisc.richtext.model.TwoDimensional.Bias.Backward;
import static org.fxmisc.richtext.model.TwoDimensional.Bias.Forward;

public class PatientDiagnose {

    @FXML
    private TextField patientName;
    @FXML
    private TextField visitDate;
    @FXML
    private TextField documentName;
    @FXML
    private CheckBox wrapToggle;
    @FXML
    private ComboBox<Integer> sizeCombo;
    @FXML
    private ComboBox<String> familyCombo;
    @FXML
    private ColorPicker textColorPicker;
    @FXML
    private ColorPicker backgroundColorPicker;
    @FXML
    private BorderPane borderPaneWithTextArea;
    @FXML
    private Button save;
    @FXML
    private Button cancel;
    @FXML
    private Button undoBtn;
    @FXML
    private Button redoBtn;
    @FXML
    private Button cutBtn;
    @FXML
    private Button copyBtn;
    @FXML
    private Button pasteBtn;
    @FXML
    private Button boldBtn;
    @FXML
    private Button italicBtn;
    @FXML
    private Button underlineBtn;
    @FXML
    private Button strikeBtn;
    @FXML
    private Button loadBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private ColorPicker paragraphBackgroundPicker;
    @FXML
    private ToggleButton alignLeftBtn;
    @FXML
    private ToggleButton alignCenterBtn;
    @FXML
    private ToggleButton alignRightBtn;
    @FXML
    private ToggleButton alignJustifyBtn;

    @Inject
    private PatientService patientService;

    // the saved/loaded files and their format are arbitrary and may change across
    // versions
    private static final String RTFX_FILE_EXTENSION = "rtfx";

    private final TextOps<String, TextStyle> styledTextOps = SegmentOps.styledTextOps();

    private final GenericStyledArea<ParStyle, String, TextStyle> area = new GenericStyledArea<>(ParStyle.EMPTY, // default
            // paragraph
            // style
            (paragraph, style) -> paragraph.setStyle(style.toCss()), // paragraph style setter

            TextStyle.EMPTY.updateFontSize(12).updateFontFamily("Serif").updateTextColor(Color.BLACK), // default
            // segment style
            styledTextOps, // segment operations
            seg -> createNode(seg, (text, style) -> text.setStyle(style.toCss()))); // Node creator and segment style

    // setter
    {
        area.setWrapText(true);
        area.setStyleCodecs(ParStyle.CODEC, styledTextCodec(TextStyle.CODEC));
    }

    private Stage mainStage;

    private BorderPane borderPane;

    @Inject
    private LocaleService localeService;

    @Inject
    private DocumentService documentService;

    private Dialog<String> dialog;

    private final SuspendableNo updatingToolbar = new SuspendableNo();

    private Document selectedDocument;

    public void showPatientDialog(Stage parentStage, String selectedItem, Document selectedDocument, byte[] richTextContent) {
        this.mainStage = parentStage;

        loadFxml();

        this.selectedDocument = selectedDocument;

        if (richTextContent.length > 0)
            load(richTextContent);

        Patient patient = patientService.getPatientsById(selectedDocument.getPatientId().toString());

        PatientVisit patientVisit = patientService.getPatientVisitById(selectedDocument.getPatientVisitId().toString());

        patientName.setText(patient.getFirstName() + patient.getLastName());

        visitDate.setText(patientVisit.getPatientVisitDate().toString());

        documentName.setText(selectedItem);

        dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Open Patient Dialog");

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().setPrefSize(1100, 900);

        setButtonAction(save, this::save);
        setButtonAction(cancel, this::closeDocument);
        setButtonAction(loadBtn, this::loadDocument);
        setButtonAction(saveBtn, this::save);
        area.wrapTextProperty().bind(wrapToggle.selectedProperty());
        setButtonAction(undoBtn, area::undo);
        setButtonAction(redoBtn, area::redo);
        setButtonAction(cutBtn, area::cut);
        setButtonAction(copyBtn, area::copy);
        setButtonAction(pasteBtn, area::paste);
        setButtonAction(boldBtn, this::toggleBold);
        setButtonAction(italicBtn, this::toggleItalic);
        setButtonAction(underlineBtn, this::toggleUnderline);
        setButtonAction(strikeBtn, this::toggleStrikethrough);
        ToggleGroup alignmentGrp = new ToggleGroup();
        alignLeftBtn = createToggleButton(alignmentGrp, this::alignLeft);
        alignCenterBtn = createToggleButton(alignmentGrp, this::alignCenter);
        alignRightBtn = createToggleButton(alignmentGrp, this::alignRight);
        alignJustifyBtn = createToggleButton(alignmentGrp, this::alignJustify);

        sizeCombo.setItems(FXCollections.observableArrayList(5, 6, 7, 8, 9, 10, 11, 12, 13,
                14, 16, 18, 20, 22, 24, 28, 32, 36, 40, 48, 56, 64, 72));
        sizeCombo.getSelectionModel().select(Integer.valueOf(12));
        sizeCombo.setTooltip(new Tooltip("Font size"));
        familyCombo.setItems(FXCollections.observableList(Font.getFamilies()));
        familyCombo.getSelectionModel().select("Serif");
        familyCombo.setTooltip(new Tooltip("Font family"));
        textColorPicker.setValue(Color.BLACK);

        textColorPicker.setTooltip(new Tooltip("Text color"));
        backgroundColorPicker.setTooltip(new Tooltip("Text background"));

        paragraphBackgroundPicker.valueProperty().addListener((o, old, color) -> updateParagraphBackground(color));


        sizeCombo.setOnAction(evt -> {
            try {
                updateFontSize(sizeCombo.getValue());
            } catch (Exception e) {
            }
        });
        familyCombo.setOnAction(evt -> updateFontFamily(familyCombo.getValue()));
        textColorPicker.valueProperty().addListener((o, old, color) -> updateTextColor(color));
        backgroundColorPicker.valueProperty().addListener((o, old, color) -> updateBackgroundColor(color));

        undoBtn.disableProperty().bind(area.undoAvailableProperty().map(x -> !x));
        redoBtn.disableProperty().bind(area.redoAvailableProperty().map(x -> !x));

        BooleanBinding selectionEmpty = new BooleanBinding() {
            {
                bind(area.selectionProperty());
            }

            @Override
            protected boolean computeValue() {
                return area.getSelection().getLength() == 0;
            }
        };

        cutBtn.disableProperty().bind(selectionEmpty);
        copyBtn.disableProperty().bind(selectionEmpty);

        area.beingUpdatedProperty().addListener((o, old, beingUpdated) -> {
            if (!beingUpdated) {
                boolean bold, italic, underline, strike;
                Integer fontSize;
                String fontFamily;
                Color textColor;
                Color backgroundColor;

                IndexRange selection = area.getSelection();
                if (selection.getLength() != 0) {
                    StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
                    bold = styles.styleStream().anyMatch(s -> s.bold.orElse(false));
                    italic = styles.styleStream().anyMatch(s -> s.italic.orElse(false));
                    underline = styles.styleStream().anyMatch(s -> s.underline.orElse(false));
                    strike = styles.styleStream().anyMatch(s -> s.strikethrough.orElse(false));
                    int[] sizes = styles.styleStream().mapToInt(s -> s.fontSize.orElse(-1)).distinct().toArray();
                    fontSize = sizes.length == 1 ? sizes[0] : -1;
                    String[] families = styles.styleStream().map(s -> s.fontFamily.orElse(null)).distinct()
                            .toArray(String[]::new);
                    fontFamily = families.length == 1 ? families[0] : null;
                    Color[] colors = styles.styleStream().map(s -> s.textColor.orElse(null)).distinct()
                            .toArray(Color[]::new);
                    textColor = colors.length == 1 ? colors[0] : null;
                    Color[] backgrounds = styles.styleStream().map(s -> s.backgroundColor.orElse(null)).distinct()
                            .toArray(i -> new Color[i]);
                    backgroundColor = backgrounds.length == 1 ? backgrounds[0] : null;
                } else {
                    int p = area.getCurrentParagraph();
                    int col = area.getCaretColumn();
                    TextStyle style = area.getStyleAtPosition(p, col);
                    bold = style.bold.orElse(false);
                    italic = style.italic.orElse(false);
                    underline = style.underline.orElse(false);
                    strike = style.strikethrough.orElse(false);
                    fontSize = style.fontSize.orElse(-1);
                    fontFamily = style.fontFamily.orElse(null);
                    textColor = style.textColor.orElse(null);
                    backgroundColor = style.backgroundColor.orElse(null);
                }

                int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
                int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
                List<Paragraph<ParStyle, String, TextStyle>> pars = area.getParagraphs().subList(startPar, endPar + 1);

                @SuppressWarnings("unchecked")
                Optional<TextAlignment>[] alignments = pars.stream().map(p -> p.getParagraphStyle().alignment)
                        .distinct().toArray(Optional[]::new);
                Optional<TextAlignment> alignment = alignments.length == 1 ? alignments[0] : Optional.empty();

                @SuppressWarnings("unchecked")
                Optional<Color>[] paragraphBackgrounds = pars.stream().map(p -> p.getParagraphStyle().backgroundColor)
                        .distinct().toArray(Optional[]::new);
                Optional<Color> paragraphBackground = paragraphBackgrounds.length == 1 ? paragraphBackgrounds[0]
                        : Optional.empty();

                updatingToolbar.suspendWhile(() -> {
                    if (bold) {
                        if (!boldBtn.getStyleClass().contains("pressed")) {
                            boldBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        boldBtn.getStyleClass().remove("pressed");
                    }

                    if (italic) {
                        if (!italicBtn.getStyleClass().contains("pressed")) {
                            italicBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        italicBtn.getStyleClass().remove("pressed");
                    }

                    if (underline) {
                        if (!underlineBtn.getStyleClass().contains("pressed")) {
                            underlineBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        underlineBtn.getStyleClass().remove("pressed");
                    }

                    if (strike) {
                        if (!strikeBtn.getStyleClass().contains("pressed")) {
                            strikeBtn.getStyleClass().add("pressed");
                        }
                    } else {
                        strikeBtn.getStyleClass().remove("pressed");
                    }

                    if (alignment.isPresent()) {
                        TextAlignment al = alignment.get();
                        switch (al) {
                            case LEFT:
                                alignmentGrp.selectToggle(alignLeftBtn);
                                break;
                            case CENTER:
                                alignmentGrp.selectToggle(alignCenterBtn);
                                break;
                            case RIGHT:
                                alignmentGrp.selectToggle(alignRightBtn);
                                break;
                            case JUSTIFY:
                                alignmentGrp.selectToggle(alignJustifyBtn);
                                break;
                        }
                    } else {
                        alignmentGrp.selectToggle(null);
                    }

                    paragraphBackgroundPicker.setValue(paragraphBackground.orElse(null));

                    if (fontSize != -1) {
                        sizeCombo.getSelectionModel().select(fontSize);
                    } else {
                        sizeCombo.getSelectionModel().clearSelection();
                    }

                    if (fontFamily != null) {
                        familyCombo.getSelectionModel().select(fontFamily);
                    } else {
                        familyCombo.getSelectionModel().clearSelection();
                    }

                    if (textColor != null) {
                        textColorPicker.setValue(textColor);
                    }

                    backgroundColorPicker.setValue(backgroundColor);
                });
            }
        });

        VirtualizedScrollPane<GenericStyledArea<ParStyle, String, TextStyle>> vsPane = new VirtualizedScrollPane<>(
                area);

        borderPaneWithTextArea.setCenter(vsPane);

        vsPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        vsPane.setPrefSize(500, 400);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        dialog.getDialogPane().getScene().getStylesheets().
                add(getClass().getResource("/sample/rich-text.css").toExternalForm());

        area.requestFocus();

        dialog.showAndWait();

    }

    private void closeDocument() {
        dialog.setResult("true");
        dialog.close();
    }

    private Node createNode(StyledSegment<String, TextStyle> seg, BiConsumer<? super TextExt, TextStyle> applyStyle) {
        String s = seg.getSegment();
        return StyledTextArea.createStyledTextNode(s, seg.getStyle(), applyStyle);
    }

    private void setButtonAction(Button button, Runnable action) {
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
    }

    private ToggleButton createToggleButton(ToggleGroup grp, Runnable action) {
        ToggleButton button = new ToggleButton();
        button.setToggleGroup(grp);
        button.setOnAction(evt -> {
            action.run();
            area.requestFocus();
        });
        return button;
    }

    private void toggleBold() {
        updateStyleInSelection(
                spans -> TextStyle.bold(!spans.styleStream().allMatch(style -> style.bold.orElse(false))));
    }

    private void toggleItalic() {
        updateStyleInSelection(
                spans -> TextStyle.italic(!spans.styleStream().allMatch(style -> style.italic.orElse(false))));
    }

    private void toggleUnderline() {
        updateStyleInSelection(
                spans -> TextStyle.underline(!spans.styleStream().allMatch(style -> style.underline.orElse(false))));
    }

    private void toggleStrikethrough() {
        updateStyleInSelection(spans -> TextStyle
                .strikethrough(!spans.styleStream().allMatch(style -> style.strikethrough.orElse(false))));
    }

    private void alignLeft() {
        updateParagraphStyleInSelection(ParStyle.alignLeft());
    }

    private void alignCenter() {
        updateParagraphStyleInSelection(ParStyle.alignCenter());
    }

    private void alignRight() {
        updateParagraphStyleInSelection(ParStyle.alignRight());
    }

    private void alignJustify() {
        updateParagraphStyleInSelection(ParStyle.alignJustify());
    }

    private void loadDocument() {
        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setSelectedExtensionFilter(
                new FileChooser.ExtensionFilter("Arbitrary RTFX file", "*" + RTFX_FILE_EXTENSION));
        File selectedFile = fileChooser.showOpenDialog(mainStage);
        if (selectedFile != null) {
            area.clear();
        }
    }

    private void load(byte[] file) {
        if (area.getStyleCodecs().isPresent()) {
            Tuple2<Codec<ParStyle>, Codec<StyledSegment<String, TextStyle>>> codecs = area.getStyleCodecs().get();
            Codec<StyledDocument<ParStyle, String, TextStyle>> codec = ReadOnlyStyledDocument.codec(codecs._1,
                    codecs._2, area.getSegOps());

            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(file);
                DataInputStream dis = new DataInputStream(bis);
                StyledDocument<ParStyle, String, TextStyle> doc = codec.decode(dis);

                doc.getText();
                bis.close();

                area.replaceSelection(doc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveDocument() {
        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save document");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.setInitialFileName("example rtfx file" + RTFX_FILE_EXTENSION);
        File selectedFile = fileChooser.showSaveDialog(mainStage);
        if (selectedFile != null) {
        }
    }

    private void save() {
        StyledDocument<ParStyle, String, TextStyle> doc = area.getDocument();

        // Use the Codec to save the document in a binary format
        area.getStyleCodecs().ifPresent(codecs -> {
            Codec<StyledDocument<ParStyle, String, TextStyle>> codec = ReadOnlyStyledDocument.codec(codecs._1,
                    codecs._2, area.getSegOps());
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                codec.encode(dos, doc);
                byte[] bytes = baos.toByteArray();
                documentService.uploadDocument(selectedDocument.getPatientId(), selectedDocument.getPatientVisitId(), bytes,
                        FilenameUtils.removeExtension(selectedDocument.getFileName()) + "_metadata", RTFX_FILE_EXTENSION);
                closeDocument();
            } catch (IOException fnfe) {
                fnfe.printStackTrace();
            }
        });
    }

    private void updateStyleInSelection(Function<StyleSpans<TextStyle>, TextStyle> mixinGetter) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            TextStyle mixin = mixinGetter.apply(styles);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateStyleInSelection(TextStyle mixin) {
        IndexRange selection = area.getSelection();
        if (selection.getLength() != 0) {
            StyleSpans<TextStyle> styles = area.getStyleSpans(selection);
            StyleSpans<TextStyle> newStyles = styles.mapStyles(style -> style.updateWith(mixin));
            area.setStyleSpans(selection.getStart(), newStyles);
        }
    }

    private void updateParagraphStyleInSelection(Function<ParStyle, ParStyle> updater) {
        IndexRange selection = area.getSelection();
        int startPar = area.offsetToPosition(selection.getStart(), Forward).getMajor();
        int endPar = area.offsetToPosition(selection.getEnd(), Backward).getMajor();
        for (int i = startPar; i <= endPar; ++i) {
            Paragraph<ParStyle, String, TextStyle> paragraph = area.getParagraph(i);
            area.setParagraphStyle(i, updater.apply(paragraph.getParagraphStyle()));
        }
    }

    private void updateParagraphStyleInSelection(ParStyle mixin) {
        updateParagraphStyleInSelection(style -> style.updateWith(mixin));
    }

    private void updateFontSize(Integer size) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontSize(size));
        }
    }

    private void updateFontFamily(String family) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.fontFamily(family));
        }
    }

    private void updateTextColor(Color color) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.textColor(color));
        }
    }

    private void updateBackgroundColor(Color color) {
        if (!updatingToolbar.get()) {
            updateStyleInSelection(TextStyle.backgroundColor(color));
        }
    }

    private void updateParagraphBackground(Color color) {
        if (!updatingToolbar.get()) {
            updateParagraphStyleInSelection(ParStyle.backgroundColor(color));
        }
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/PatientDiagone.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
