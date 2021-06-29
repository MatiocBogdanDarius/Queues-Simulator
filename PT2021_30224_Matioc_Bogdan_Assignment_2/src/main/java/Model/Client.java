package Model;

/**
 * Shapes a customer from reality
 */
public class Client implements Comparable<Client>{
    private int id;
    private int timeArrival;
    private int timeService;
    private int timeInQueue;
    private int timeWaitingInQueue;

    public Client(int id, int timeArrival, int timeService) {
        this.id = id;
        this.timeArrival = timeArrival;
        this.timeService = timeService;
    }

    @Override
    public int compareTo(Client o) {
        return Integer.compare(timeArrival, o.timeArrival);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", timeArrival=" + timeArrival +
                ", timeService=" + timeService +
                '}';
    }

    public int getTimeArrival() {
        return timeArrival;
    }

    public int getTimeService() {
        return timeService;
    }

    public void setTimeService() {
        timeService--;
    }

    public int getTimeInQueue() {
        return timeInQueue;
    }

    public void setTimeInQueue(int timeInQueue) {
        this.timeInQueue = timeInQueue;
    }

    public int getTimeWaitingInQueue() {
        return timeWaitingInQueue;
    }

    public void setTimeWaitingInQueue(int timeWaitingInQueue) {
        this.timeWaitingInQueue = timeWaitingInQueue;
    }
}
