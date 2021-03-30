package controller;

import algorithm.FunctionType;
import algorithm.Swarm;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.UnaryOperator;

public class Controller {

    private final static double MAX_INERTIA = 3.0;
    private final static double MAX_COGNITIVE = 3.0;
    private final static double MAX_SOCIAL = 3.0;

    private final static int DECIMAL_PLACES = 6;

    @FXML
    private Button startButton;

    @FXML
    private Button saveButton;

    @FXML
    private Slider socialSlider;

    @FXML
    private Slider inertiaSlider;

    @FXML
    private Slider cognitiveSlider;

    @FXML
    private TextField epochsTextField;

    @FXML
    private TextField particlesTextField;

    @FXML
    private TextArea logTextArea;

    @FXML
    private ComboBox<FunctionType> functionsComboBox;

    @FXML
    public Label inertiaValueLabel;

    @FXML
    public Label socialValueLabel;

    @FXML
    public Label cognitiveValueLabel;

    @FXML
    public Button clearButton;

    @FXML
    public void initialize() {
        logTextArea.setEditable(false);

        FunctionType[] functionTypes = FunctionType.values();
        functionsComboBox.setConverter(new StringConverter<FunctionType>() {
            @Override
            public String toString(FunctionType functionType) {
                return functionType.getName();
            }

            @Override
            public FunctionType fromString(String s) {
                return null;
            }
        });

        functionsComboBox.setItems(FXCollections.observableArrayList(functionTypes));
        functionsComboBox.getSelectionModel().selectFirst();

        inertiaSlider.setMin(0);
        inertiaSlider.setMax(MAX_INERTIA);
        inertiaSlider.setValue(Swarm.DEFAULT_INERTIA);

        cognitiveSlider.setMin(0);
        cognitiveSlider.setMax(MAX_COGNITIVE);
        cognitiveSlider.setValue(Swarm.DEFAULT_COGNITIVE);

        socialSlider.setMin(0);
        socialSlider.setMax(MAX_SOCIAL);
        socialSlider.setValue(Swarm.DEFAULT_COGNITIVE);

        inertiaValueLabel.textProperty().bind(
                Bindings.format(
                        "%.2f", inertiaSlider.valueProperty()
                )
        );
        cognitiveValueLabel.textProperty().bind(
                Bindings.format(
                        "%.2f", cognitiveSlider.valueProperty()
                )
        );
        socialValueLabel.textProperty().bind(
                Bindings.format(
                        "%.2f", socialSlider.valueProperty()
                )
        );

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }

            return null;
        };
        TextFormatter<String> textFormatterParticles = new TextFormatter<>(filter);
        particlesTextField.setTextFormatter(textFormatterParticles);

        TextFormatter<String> textFormatterEpochs = new TextFormatter<>(filter);
        epochsTextField.setTextFormatter(textFormatterEpochs);

        startButton.disableProperty().bind(
                Bindings.isEmpty(epochsTextField.textProperty())
                        .or(Bindings.isEmpty(particlesTextField.textProperty()))
        );

        saveButton.disableProperty().bind(
                Bindings.isEmpty(logTextArea.textProperty())
        );

        clearButton.disableProperty().bind(
                Bindings.isEmpty(logTextArea.textProperty())
        );
    }

    @FXML
    void startButtonOnAction(ActionEvent event) {
        FunctionType function = functionsComboBox.getValue();
        int particles = Integer.parseInt(particlesTextField.getText());
        int epochs = Integer.parseInt(epochsTextField.getText());

        double inertia = inertiaSlider.getValue();
        double cognitive = cognitiveSlider.getValue();
        double social = socialSlider.getValue();

        Swarm swarm = new Swarm(function, particles, epochs, inertia, cognitive, social);
        logTextArea.clear();
        new Thread(() -> swarm.run(logTextArea)).start();
    }

    @FXML
    void saveButtonOnAction(ActionEvent event) {
        String directory = "log/";
        String fileName = directory + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss'.txt'").format(new Date());

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
            Files.createDirectories(Paths.get(directory));
            writer.write(logTextArea.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Created a new file");
            alert.setHeaderText(null);
            alert.setContentText("A file with logs has been saved to: " + System.lineSeparator() + fileName);
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clearButtonOnAction(ActionEvent actionEvent) {
        logTextArea.clear();
    }

    public static String print(double x) {
        return String.format("%." + DECIMAL_PLACES + "f", x);
    }


}
