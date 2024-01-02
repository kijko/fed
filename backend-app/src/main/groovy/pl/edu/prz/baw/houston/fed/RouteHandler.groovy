package pl.edu.prz.baw.houston.fed

import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

interface RouteHandler {
    default Mono<ServerResponse> get(ServerRequest request) { return Mono.empty() }
    default Mono<ServerResponse> post(ServerRequest request) { return Mono.empty() }
    default Mono<ServerResponse> patch(ServerRequest request) { return Mono.empty() }
}