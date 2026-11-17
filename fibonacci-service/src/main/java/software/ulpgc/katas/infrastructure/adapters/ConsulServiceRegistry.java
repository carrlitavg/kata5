package software.ulpgc.katas.infrastructure.adapters;

import com.google.gson.Gson;
import software.ulpgc.katas.architecture.ports.ServiceRegistry;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public class ConsulServiceRegistry implements ServiceRegistry {
    private final String id;
    private final String host;
    private final int port;
    private final String consul;

    public ConsulServiceRegistry(String id, String host, int port, String consul) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.consul = consul;
    }

    @Override
    public void register() throws IOException, InterruptedException {
        try (HttpClient client = client()) {
            HttpResponse<String> response = client.send(requestWith(json()), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() / 100 != 2) {
                throw new IOException("Consul rechazó el registro (HTTP " + response.statusCode() + "): " + response.body());
            }
        }
    }

    private static HttpClient client() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
                .build();
    }

    private HttpRequest requestWith(String json) {
        return HttpRequest
                .newBuilder(URI.create(consul).resolve("/v1/agent/service/register"))
                .timeout(Duration.ofSeconds(5))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
    }

    private String host() {
        return isLocalHost() ? "host.docker.internal" : host;
    }

    private boolean isLocalHost() {
        return "localhost".equals(host) ||
                "127.0.0.1".equals(host) ||
                "0.0.0.0".equals(host);
    }

    private String healthUrl() {
        return "http://" + host() + ":" + port + "/health";
    }

    private String json() {
        return new Gson().toJson(Map.of(
                "ID", id,
                "Name", "fibonacci_service_v1",
                "Address", host(),
                "Port", port,
                "Check", Map.of(
                        "HTTP", healthUrl(),
                        "Interval", "5s",
                        "Timeout", "2s",
                        "DeregisterCriticalServiceAfter", "10m"
                )
        ));
    }
}
