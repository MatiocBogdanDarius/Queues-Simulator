package Model.InOutData;

import Controller.Controller;
import Exceptions.InvalidInputDataExceptions;

import java.util.List;

public class InputData {
    private int timeLimit;
    private int minTimeArrival;
    private int maxTimeArrival;
    private int minTimeService;
    private int maxTimeService;
    private int numberOfServers;
    private int numberOfClients;

    public void checkInputData() throws InvalidInputDataExceptions {
        if(timeLimit < 0 ||  minTimeArrival < 0 || maxTimeArrival < 0 || minTimeService < 0 || maxTimeService < 0 ||
                numberOfServers < 0 || numberOfClients < 0)
            throw new InvalidInputDataExceptions("Exception: All data entered must be natural numbers");
        if(minTimeArrival > maxTimeArrival){
            throw new InvalidInputDataExceptions("Exception: minimum  time arrival must be last than maxim time arrival");
        }
        if(minTimeService > maxTimeService){
            throw new InvalidInputDataExceptions("Exception: minimum  time service must be last than maxim time service");
        }
        if(numberOfServers == 0){
            throw new InvalidInputDataExceptions("Exception: the numbers of servers must be greater than 0");
        }
        if(timeLimit == 0){
            throw new InvalidInputDataExceptions("Exception: time limit must be greater than 0");
        }
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getMinTimeArrival() {
        return minTimeArrival;
    }

    public int getMaxTimeArrival() {
        return maxTimeArrival;
    }

    public int getMinTimeService() {
        return minTimeService;
    }

    public int getMaxTimeService() {
        return maxTimeService;
    }

    public int getNumberOfServers() {
        return numberOfServers;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public void setMinTimeArrival(int minTimeArrival) {
        this.minTimeArrival = minTimeArrival;
    }

    public void setMaxTimeArrival(int maxTimeArrival) {
        this.maxTimeArrival = maxTimeArrival;
    }

    public void setMinTimeService(int minTimeService) {
        this.minTimeService = minTimeService;
    }

    public void setMaxTimeService(int maxTimeService) {
        this.maxTimeService = maxTimeService;
    }

    public void setNumberOfServers(int numberOfServers) {
        this.numberOfServers = numberOfServers;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }
}
