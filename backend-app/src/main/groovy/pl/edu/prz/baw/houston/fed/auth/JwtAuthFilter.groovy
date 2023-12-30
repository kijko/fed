package pl.edu.prz.baw.houston.fed.auth

import org.springframework.core.env.Environment
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class JwtAuthFilter implements WebFilter {

    private final Environment env
    private final UserDetailsService userDetailsService

    JwtAuthFilter(Environment env, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService
        this.env = env
    }

    @Override
    Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return Mono.just(exchange.getRequest().getHeaders().getFirst("Authorization") ?: '')
            .flatMap { rawToken ->
                if (!rawToken.isEmpty() && rawToken.startsWith('Bearer ')) {
                    JWT token = JWT.parseAndVerify(rawToken.replace('Bearer ', ''), env.getProperty('fed.auth.jwt-secret-b64'))

                    if (token != null) {

                        return userExistsAndIsNotBlocked(token.uuid)
                            .flatMap { existsAndNotBlocked ->
                                if (existsAndNotBlocked) {

//                                    SecurityContextImpl securityContext = new SecurityContextImpl();
//                                    securityContext.setAuthentication(new JwtAuthentication(token));
//                                    return this.securityContextRepository.save(exchange, securityContext)
//                                            .then(this.authenticationSuccessHandler.onAuthenticationSuccess(webFilterExchange, authentication))
//                                            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
                                    return chain.filter(exchange)
                                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(new JwtAuthentication(token)))
                                }

                                return chain.filter(exchange)
                            }

                    }
                }

                return chain.filter(exchange)
            }
    }

    private Mono<Boolean> userExistsAndIsNotBlocked(String uuid) {
        return userDetailsService.findUser(uuid).defaultIfEmpty(NullUser.INSTANCE)
            .map { user -> user !== NullUser.INSTANCE && !user.blocked }
    }
}
