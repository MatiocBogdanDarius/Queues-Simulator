package Controller;

import Exceptions.InvalidInputDataExceptions;
import Model.InOutData.InputData;
import Model.InOutData.OutputData;
import Model.SimulationManeger;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private OutputData outputData;
    private SimulationManeger simulationManeger;
    @FXML
    private TextField noClientsInput;
    @FXML
    private TextField  noQueuesInput;
    @FXML
    private TextField  minTimeArrivalInput;
    @FXML
    private TextField  maxTimeArrivalInput;
    @FXML
    private TextField  minTimeServiceInput;
    @FXML
    private TextField  maxTimeServiceInput;
    @FXML
    private TextField  limitTimeInput;
    @FXML
    private TextArea reportTextArea;
    @FXML
    private Button startButton;
    @FXML
    private Button clearButton;
    @FXML
    private ImageView imageView;
    @FXML
    private Label exceptionLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            startButton.setOnAction(e -> {
                try {
                    InputData inputData = read();
                    inputData.checkInputData();
                    createLogicalThread(inputData);
                    createUpdateUIThread(inputData);
                    setExceptionLabel("");
                    startButton.setDisable(true);
                } catch (NumberFormatException exception) {
                    setExceptionLabel("All fields must be filled in with numbers only!");
                } catch (InvalidInputDataExceptions exception) {
                    setExceptionLabel(exception.getMessage());
                }
            });
            clearButton.setOnAction(e -> {
                outputData.reset();
                setReportTextArea();
            });
            addTranslationForImageView();
    }

    public Controller() {
        outputData = new OutputData();
    }

    private InputData read() throws NumberFormatException{
        InputData inputData = new InputData();
        inputData.setTimeLimit(Integer.parseInt(limitTimeInput.getText()));
        inputData.setMaxTimeArrival( Integer.parseInt(maxTimeArrivalInput.getText()));
        inputData.setMinTimeArrival(Integer.parseInt(minTimeArrivalInput.getText()));
        inputData.setMaxTimeService(Integer.parseInt(maxTimeServiceInput.getText()));
        inputData.setMinTimeService(Integer.parseInt(minTimeServiceInput.getText()));
        inputData.setNumberOfClients(Integer.parseInt(noClientsInput.getText()));
        inputData.setNumberOfServers(Integer.parseInt(noQueuesInput.getText()));
        return inputData;
    }

    public void setReportTextArea() {
        reportTextArea.setText(outputData.getReport());
    }

    public void setEnableStartButton() {
        startButton.setDisable(simulationManeger.isRun());
    }

    public void addTranslationForImageView(){
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setToX(80);
        transition.setCycleCount(Integer.MAX_VALUE);
        transition.setNode(imageView);
        transition.play();
    }

    void createLogicalThread(InputData inputData){
        simulationManeger = new SimulationManeger(inputData, outputData);
        Thread logicalThread = new Thread(simulationManeger);
        logicalThread.setDaemon(true);
        logicalThread.start();

    }

    void createUpdateUIThread(InputData inputData){
        Thread updateUIThread = new Thread(() -> {
            int currentTime = 0;
            while(currentTime <= inputData.getTimeLimit()){
                setReportTextArea();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                currentTime++;
                setEnableStartButton();
                if(!simulationManeger.isRun()) break;
            }
            setReportTextArea();
        });
        updateUIThread.setDaemon(true);
        updateUIThread.start();
    }

    public void setExceptionLabel(String exceptionMessage) {
        exceptionLabel.setText(exceptionMessage);
    }
}
