package Model.InOutData;

import Controller.Controller;
import Model.Client;
import Model.Server;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 */
public class OutputData {
    private StringBuilder report = new StringBuilder();

    public void write(int currentTime, List<Server> servers){
        report.append("current time: ").append(currentTime).append("\n");
        servers.sort(Comparator.comparingInt(Server::getId));
        report.append(servers).append("\n\n");
        System.out.println(report);
    }

    public String getReport() {
        return String.valueOf(report);
    }

    public void reset() {
        report = new StringBuilder();
    }

    public void writeAverageWaitingTime(float averageTimeInQueue) {
        report.append("Average waiting time: ");
        report.append(averageTimeInQueue);
        report.append("\n\n");
    }

    public void writeAverageTimeInQueue(float averageTimeInQueue) {
        report.append("Average processing time: ");
        report.append(averageTimeInQueue);
        report.append("\n\n");
    }

    public void writePeakHour(int peakHour) {
        report.append("Peak Hour: ");
        report.append(peakHour);
        report.append("\n\n");
    }

    public void writeInFile() {
        try {
            File fileOutput = new File("src/main/resources/log.txt");
            FileWriter write =  new FileWriter(fileOutput);
            PrintWriter pw = new PrintWriter(write);
            pw.println(report);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeInitialClientsList(List<Client> generatedClients) {
        report.append("Clients:\n ");
        generatedClients.forEach(client -> report.append(client).append("\n"));
        report.append("\n\n");
    }
}
