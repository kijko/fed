package pl.edu.prz.baw.houston.fed.auth

import org.jooq.impl.DefaultDSLContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
import reactor.core.publisher.Mono

@Component
class SignInHandler implements RouteHandler {

    private final DefaultDSLContext ctx

    SignInHandler(DefaultDSLContext ctx) {
        this.ctx = ctx
    }

    @Override
    Mono<ServerResponse> handle(ServerRequest request) {
        return Mono.from(
                ctx.select().from("hello_table")
        )
                .map(r -> (Integer) r.get("my_int"))
                .flatMap(num ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(new Greeting("Hi! " + num)))
                );
    }

}
