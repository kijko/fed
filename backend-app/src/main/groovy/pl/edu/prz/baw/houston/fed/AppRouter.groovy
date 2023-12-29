package pl.edu.prz.baw.houston.fed

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.auth.SignInHandler

import static org.springframework.web.reactive.function.server.RequestPredicates.GET
import static org.springframework.web.reactive.function.server.RequestPredicates.accept

@Configuration
class AppRouter {
    static final String API_BASE_PATH = "/api"

    @Autowired
    SignInHandler signInHandler

    @Bean
    RouterFunction<ServerResponse> router() {
        return RouterFunctions
                .route(
                        GET("$API_BASE_PATH/sign-in").and(accept(MediaType.APPLICATION_JSON)),
                        signInHandler::handle
                )
    }
}
