package pl.edu.prz.baw.houston.fed

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.auth.SignInHandler
import pl.edu.prz.baw.houston.fed.usermgmt.UserManagementHandler

import static org.springframework.web.reactive.function.server.RequestPredicates.*

@Configuration
class AppRouter {
    static final String API_BASE_PATH = "/api"

    @Autowired
    SignInHandler signInHandler
    @Autowired
    UserManagementHandler userManagementHandler
    @Value("classpath:/assets/index.html")
    Resource indexHtml

    @Bean
    RouterFunction<ServerResponse> router() {
        return RouterFunctions
                .route()
                .route(
                        POST("$API_BASE_PATH/sign-in").and(accept(MediaType.APPLICATION_JSON)),
                        signInHandler::post
                )
                .route(
                        GET("$API_BASE_PATH/users"),
                        userManagementHandler::get
                )
                .route(
                        POST("$API_BASE_PATH/users"),
                        userManagementHandler::post
                )
                .route(
                        PATCH("$API_BASE_PATH/users/{id}"),
                        userManagementHandler::patch
                )
                .route(GET("/"), {
                    ServerResponse.ok().contentType(MediaType.TEXT_HTML).bodyValue(indexHtml)
                })
                .resources('/static/**', new ClassPathResource("assets/static/"))
                .resources('/*.ico', new ClassPathResource('assets/'))
                .resources('/*.png', new ClassPathResource('assets/'))
                .resources('/*.json', new ClassPathResource('assets/'))
                .resources('/*.txt', new ClassPathResource('assets/'))
        .build()
    }

}
