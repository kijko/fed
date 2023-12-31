package pl.edu.prz.baw.houston.fed

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
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
            .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("${API_BASE_PATH}/sign-in").permitAll()
                        .pathMatchers("${API_BASE_PATH}/users").hasRole('ADMIN')
                        .anyExchange().authenticated()
            )
            .addFilterAt(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        return http.build()
    }
}
