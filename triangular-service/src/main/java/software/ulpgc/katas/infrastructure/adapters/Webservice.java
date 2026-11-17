package software.ulpgc.katas.infrastructure.adapters;

import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Handler;
import software.ulpgc.katas.architecture.ports.ServiceRegistry;
import software.ulpgc.katas.infrastructure.adapters.endpoints.HealthEndPoint;

import java.io.IOException;

public class Webservice {
    private final String host;
    private final int port;
    private final Handler seriesEndPoint;
    private final ServiceRegistry registry;

    public Webservice(String host, int port, Handler seriesEndPoint, ServiceRegistry registry) {
        this.host = host;
        this.port = port;
        this.seriesEndPoint = seriesEndPoint;
        this.registry = registry;
    }

    public void start() throws IOException, InterruptedException {
        Javalin app = Javalin.create(this::init).start(host, port);
        try {
            registry.register();
        } catch (IOException | InterruptedException | RuntimeException failure) {
            app.stop();
            throw failure;
        }
    }

    private void init(JavalinConfig config) {
        config.routes.get("/", seriesEndPoint);
        config.routes.get("/health", new HealthEndPoint());
    }
}
