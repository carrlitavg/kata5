package software.ulpgc.katas;

import software.ulpgc.katas.architecture.ports.ServiceRegistry;
import software.ulpgc.katas.infrastructure.adapters.ConsulServiceRegistry;
import software.ulpgc.katas.infrastructure.adapters.Webservice;
import software.ulpgc.katas.infrastructure.adapters.endpoints.TriangularEndPoint;

public class Main {

    static void main(String[] args) throws Exception {
        String id = args[0];
        String host = args[1];
        int port = Integer.parseInt(args[2]);
        String consul = args[3];
        ServiceRegistry registry = new ConsulServiceRegistry(id, host, port, consul);
        new Webservice(host, port, new TriangularEndPoint(id), registry).start();
        System.out.println("Registered : " + id + " as " + host + ":" + port);
    }
}
