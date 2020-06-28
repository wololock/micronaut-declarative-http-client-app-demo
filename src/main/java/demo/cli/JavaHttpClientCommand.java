package demo.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Command(name = "java-http-client")
final class JavaHttpClientCommand implements Runnable {

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void run() {
        var httpClient = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder()
                .GET()
                .header("Accept", "application/json")
                .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                .build();

        try {
            var response = httpClient.send(request, HttpResponse.BodyHandlers.ofInputStream());

            var jokes = objectMapper.readValue(response.body(), Map.class);

            System.out.println(Ansi.ON.string(String.format("@|bold,fg(green) %s |@", jokes.get("value"))));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
