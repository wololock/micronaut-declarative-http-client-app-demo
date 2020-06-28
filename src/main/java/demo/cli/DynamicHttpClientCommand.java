package demo.cli;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Ansi;

import javax.inject.Inject;
import java.util.Map;

@Command(name = "dynamic-http-client")
final class DynamicHttpClientCommand implements Runnable {

    @Inject
    @Client("https://api.chucknorris.io")
    HttpClient httpClient;

    @Override
    public void run() {
        var response = httpClient.toBlocking().retrieve("/jokes/random", Map.class);

        System.out.println(Ansi.ON.string(String.format("@|bold,fg(green) %s |@", response.get("value"))));
    }
}
