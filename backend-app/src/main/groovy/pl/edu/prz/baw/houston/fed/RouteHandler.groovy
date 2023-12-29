package pl.edu.prz.baw.houston.fed

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface RouteHandler {
    Mono<ServerResponse> handle(ServerRequest request)
}