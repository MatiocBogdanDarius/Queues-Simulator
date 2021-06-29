package Model;

import Model.Stategy.ConcreteStrategyTime;
import Model.Stategy.Strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Planner {
    private List<Server> servers;
    private Strategy strategy = new ConcreteStrategyTime();

    public Planner(int maxNoServers) {
        this.servers = Collections.synchronizedList(new ArrayList<>());
        for(int i = 0; i < maxNoServers; i++){
            Server newServer  = new Server(i + 1);
            servers.add(newServer);
            Thread newThread = new Thread(newServer);
            newThread.setDaemon(true);
            newThread.start();
        }
    }

    public void dispatchClient(Client client){
        strategy.addClient(servers, client);
    }

    public List<Server> getServers() {
        return servers;
    }
}

