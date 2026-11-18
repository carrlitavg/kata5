package software.ulpgc.katas.infrastructure.adapters.endpoints;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class HealthEndPoint implements Handler {
    @Override
    public void handle(Context context) {
        context.result("OK");
    }
}
