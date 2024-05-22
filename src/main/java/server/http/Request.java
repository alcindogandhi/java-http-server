package server.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public interface Request extends HttpHandler {
    default Map<String,String> getParams(HttpExchange httpExchange) {
        final Map<String,String> params = new HashMap<>();
        final String uri = httpExchange.getRequestURI().toString();
        final String[] uriParts = uri.split("\\?");
        if (uriParts.length < 2) {
            return params;
        }
        final String[] uriParams = uriParts[1].split("&");
        for (final String uriParam: uriParams) {
            final String[] param = uriParam.split("=");
            if (param.length == 2) {
                params.put(param[0], param[1]);
            }
        }
        return params;
    }

    default String getRequestBody(HttpExchange httpExchange) throws IOException {
        try (InputStream is = httpExchange.getRequestBody()) {
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
