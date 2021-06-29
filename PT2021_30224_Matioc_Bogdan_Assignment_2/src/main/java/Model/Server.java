package Model;

import java.util.Iterator;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable, Comparable<Server>{
    private final int id;
    private BlockingDeque<Client> clients;
    private AtomicInteger waitingPeriod;

    public Server(int id){
        this.id = id;
        clients = new LinkedBlockingDeque<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public void addClient (Client client){
        client.setTimeWaitingInQueue(waitingPeriod.get());
        clients.addLast(client);
        waitingPeriod.addAndGet(client.getTimeService());
        client.setTimeInQueue(waitingPeriod.get());
    }

    public boolean isInWork(){
        return waitingPeriod.get() != 0;
    }

    @Override
    public void run() {
        while(true){
            Iterator<Client> iteratorClients = clients.iterator();
            if(iteratorClients.hasNext()) {
                Client currentClient = iteratorClients.next();
                waitingPeriod.addAndGet(- 1);
                currentClient.setTimeService();
                if(currentClient.getTimeService() == 0)
                    clients.remove(currentClient);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int compareTo(Server o) {
        return Integer.compare(waitingPeriod.intValue(), o.waitingPeriod.intValue());
    }

    @Override
    public String toString() {
        return "\nServer "+ id +" {" +
                "clients=" + clients.toString() +
                ", waitingPeriod=" + waitingPeriod +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getNumberClients(){
        return clients.size();
    }
}
