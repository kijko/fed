package pl.edu.prz.baw.houston.fed.usermgmt

import org.springframework.http.MediaType
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
import pl.edu.prz.baw.houston.fed.auth.Greeting
import reactor.core.publisher.Mono

@Component
class UserManagementHandler implements RouteHandler {
    @Override
    Mono<ServerResponse> handle(ServerRequest request) {
        return ReactiveSecurityContextHolder.getContext().flatMap {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(new Greeting("Hello ${it.authentication.principal as String}")))
        }

    }
}
