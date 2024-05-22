package server.http;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

class TestRequest implements Request {
    @Override    
    public void handle(HttpExchange httpExchange) throws IOException {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<data>").append("\n");
        htmlBuilder.append("<get>").append("\n");
        for (var entry: getParams(httpExchange).entrySet()) {
            htmlBuilder.append("<")
                .append(entry.getKey())
                .append(">")
                .append(entry.getValue())
                .append("</")
                .append(entry.getKey())
                .append(">")
                .append("\n");
        }
        htmlBuilder.append("</get>")
            .append("\n")
            .append("<body>")
            .append("\n")
            .append(getRequestBody(httpExchange))
            .append("\n")
            .append("</body>")
            .append("\n")
            .append("</data>");

        byte[] htmlResponse = htmlBuilder.toString().getBytes();
        httpExchange.sendResponseHeaders(200, htmlResponse.length);
        try (OutputStream outputStream = httpExchange.getResponseBody()) {
            outputStream.write(htmlResponse);
            outputStream.flush();
        }
    }

}
