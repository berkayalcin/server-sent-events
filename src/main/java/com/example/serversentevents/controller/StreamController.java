package com.example.serversentevents.controller;

import com.example.serversentevents.model.Stock;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;

@RequestMapping("/streams")
@RestController
public class StreamController {
    @GetMapping(path = "/currencies/{currency}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Stock> streamFlux(@PathVariable String currency) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> Stock.builder()
                        .close(new BigDecimal("10.5"))
                        .open(new BigDecimal("11.5"))
                        .high(new BigDecimal("13.5"))
                        .low(new BigDecimal("10.2"))
                        .currency(currency)
                        .build());
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build());
    }
}
