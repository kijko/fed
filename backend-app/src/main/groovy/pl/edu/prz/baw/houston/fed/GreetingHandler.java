package pl.edu.prz.baw.houston.fed;

import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    @Autowired
    DefaultDSLContext dslContext;

    public Mono<ServerResponse> hello(ServerRequest request) {
        Result<Record> helloTable = dslContext.select().from("hello_table").fetch();
        Object myInt = (Integer) helloTable.get(0).get("my_int");

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Greeting("Hello, Spring! Integer from db: " + myInt)));
    }
}