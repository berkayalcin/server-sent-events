package com.example.serversentevents.client;

import com.example.serversentevents.model.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.LocalTime;

@Component
@Slf4j
public class StreamClient {
    public void consumeServerSentEvent() {
        WebClient client = WebClient.create("http://localhost:8080/streams/");
        ParameterizedTypeReference<ServerSentEvent<Stock>> type
                = new ParameterizedTypeReference<>() {
        };

        Flux<ServerSentEvent<Stock>> eventStream = client.get()
                .uri("/currencies/usd")
                .retrieve()
                .bodyToFlux(type);

        eventStream.subscribe(
                content -> log.info("Time: {} - event: name[{}], id [{}], content[{}] ",
                        LocalTime.now(), content.event(), content.id(), content.data().toString()),
                error -> log.error("Error receiving SSE: {}", error),
                () -> log.info("Completed!!!"));
    }
}
