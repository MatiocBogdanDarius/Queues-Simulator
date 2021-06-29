package Model;

import Model.InOutData.InputData;
import Model.InOutData.OutputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 */
public class SimulationManeger implements Runnable {
    private InputData inputData;
    private OutputData outputData;
    private Planner planner;
    private List<Client> generatedClients = Collections.synchronizedList(new ArrayList<>());
    private boolean isRun;

    public SimulationManeger(InputData inputData, OutputData outputData) {
        isRun = false;
        this.inputData = inputData;
        this.outputData = outputData;
        this.planner = new Planner(inputData.getNumberOfServers());
        generateNRandomClients();
    }

    @Override
    public void run() {
        outputData.writeInitialClientsList(generatedClients);
        isRun= true;
        int currentTime = 0, peakHour = 0, maxNumberClientsPerMoment = 0;
        int timeLimit = inputData.getTimeLimit();
        while (currentTime < timeLimit ) {
            for (Client client : generatedClients) {
                if (client.getTimeArrival() == currentTime) {
                    planner.dispatchClient(client);
                }
            }
            if(getCurrentNumberClient(planner.getServers()) > maxNumberClientsPerMoment){
                maxNumberClientsPerMoment = getCurrentNumberClient(planner.getServers());
                peakHour = currentTime;
            }
            outputData.write(currentTime, planner.getServers());
            currentTime++;
            if(areAllClientsServed(currentTime))
                break;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        outputData.writeAverageWaitingTime(calculateAverageWaitingTime());
        outputData.writeAverageTimeInQueue(calculateAverageTimeInQueue());
        outputData.writePeakHour(peakHour);
        outputData.writeInFile();
        isRun = false;
    }

    private int getCurrentNumberClient(List<Server> servers) {
        return servers.stream().mapToInt(Server::getNumberClients).sum();
    }

    private boolean areAllClientsServed(int currentTime) {
        for(Client client:generatedClients)
            if(client.getTimeArrival() >= currentTime)
                return false;
        for(Server server: planner.getServers())
            if(server.isInWork())
                return !server.isInWork();
        return true;
    }

    private float calculateAverageTimeInQueue() {
        float sum =0;
        for(Client client:generatedClients)
            sum += client.getTimeInQueue();
        return sum / generatedClients.size();
    }

    private float calculateAverageWaitingTime() {
        float sum =0;
        for(Client client:generatedClients)
            sum += client.getTimeWaitingInQueue();
        return sum / generatedClients.size();
    }

    private void generateNRandomClients() {
        for (int i = 0; i < inputData.getNumberOfClients(); i++) {
            Random random = new Random();
            int timeArrival = inputData.getMinTimeArrival() +
                    random.nextInt(inputData.getMaxTimeArrival() - inputData.getMinTimeArrival() + 1);
            int timeService = inputData.getMinTimeService() +
                    random.nextInt(inputData.getMaxTimeService() - inputData.getMinTimeService() + 1);
            generatedClients.add(new Client(i + 1, timeArrival, timeService));
        }
    }

    public boolean isRun() {
        return isRun;
    }
}
