package pl.edu.prz.baw.houston.fed

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import pl.edu.prz.baw.houston.fed.auth.JwtAuthFilter

import static pl.edu.prz.baw.houston.fed.AppRouter.API_BASE_PATH

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(
            ServerHttpSecurity http,
            JwtAuthFilter jwtAuthFilter
    ) {
        http
            .httpBasic { it.disable() }
            .formLogin { it.disable()}
            .csrf { it.disable() }
            .logout { it.disable() }
            .cors {
                it.configurationSource {
                    def cors = new CorsConfiguration()
                    cors.setAllowedOrigins(List.of('http://localhost:3000', 'https://fed-app.azurewebsites.net'))
                    cors.setAllowedMethods(List.of('*'))
                    cors.setAllowedHeaders(List.of('*'))
                    cors.setExposedHeaders(List.of('Authorization'))

                    return cors
                }
            }
            .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("${API_BASE_PATH}/sign-in").permitAll()
                        .pathMatchers("${API_BASE_PATH}/users").hasRole('ADMIN')
                        .anyExchange().authenticated()
            )
            .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()
    }
}
