package com.example.serversentevents;

import com.example.serversentevents.client.StreamClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerSentEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerSentEventsApplication.class, args);
        StreamClient streamClient = new StreamClient();
        streamClient.consumeServerSentEvent();
    }

}
