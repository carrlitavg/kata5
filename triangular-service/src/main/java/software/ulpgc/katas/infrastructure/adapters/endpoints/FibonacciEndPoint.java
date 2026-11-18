package software.ulpgc.katas.infrastructure.adapters.endpoints;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import software.ulpgc.katas.architecture.model.FibonacciSeries;

public class FibonacciEndPoint implements Handler {
    private final String id;

    public FibonacciEndPoint(String id) {
        this.id = id;
    }

    @Override
    public void handle(Context context) {
        int terms = Integer.parseInt(context.queryParam("n"));
        context.header("X-Node", id);
        context.result(new Gson().toJson(FibonacciSeries.calculate(terms)));
    }
}
