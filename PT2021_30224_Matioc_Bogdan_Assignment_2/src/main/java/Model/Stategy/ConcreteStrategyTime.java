package Model.Stategy;

import Model.Client;
import Model.Server;

import java.util.Collections;
import java.util.List;

public class ConcreteStrategyTime implements Strategy {

    @Override
    public void addClient(List<Server> servers, Client client) {
        Collections.sort(servers);
        servers.get(0).addClient(client);
    }
}
