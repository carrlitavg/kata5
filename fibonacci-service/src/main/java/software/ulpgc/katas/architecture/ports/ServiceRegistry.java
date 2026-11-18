package software.ulpgc.katas.architecture.ports;

import java.io.IOException;

public interface ServiceRegistry {
    void register() throws IOException, InterruptedException;
}
