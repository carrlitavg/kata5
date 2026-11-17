package software.ulpgc.katas.infrastructure.adapters.endpoints;

import com.google.gson.Gson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import software.ulpgc.katas.architecture.model.TriangularSeries;

public class TriangularEndPoint implements Handler {
    private final String id;

    public TriangularEndPoint(String id) {
        this.id = id;
    }

    @Override
    public void handle(Context context) {
        int terms = Integer.parseInt(context.queryParam("n"));
        context.header("X-Node", id);
        context.result(new Gson().toJson(TriangularSeries.calculate(terms)));
    }
}
