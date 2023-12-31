package pl.edu.prz.baw.houston.fed.auth

import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
import reactor.core.publisher.Mono

import java.security.MessageDigest

@Component
class SignInHandler implements RouteHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SignInHandler.class)

    private final Environment env
    private final DefaultDSLContext ctx

    SignInHandler(DefaultDSLContext ctx, Environment env) {
        this.env = env
        this.ctx = ctx
    }

    @Override
    Mono<ServerResponse> handle(ServerRequest request) {
        ReactiveSecurityContextHolder.getContext().map { secCtx ->
            LOGGER.info(secCtx)
        }

        return request
                .bodyToMono(SignInRequest.class)
                .flatMap {
                    def loginHash = getSHA256(it.login)
                    def passhash = getSHA256(it.password)

//                    LOGGER.info(loginHash)
//                    LOGGER.info(loginHash.length().toString())
//                    LOGGER.info(passhash)
//                    LOGGER.info(passhash.length().toString())

                    Mono.from(
                            ctx.select().from('fed_user')
                                    .where(DSL.field('login_hash').eq(loginHash)
                                            .and(DSL.field('password_hash').eq(passhash)))
                    ).map { rec ->
                        FedUser.ofType(rec.get('type') as String, rec.get('uuid') as String, rec.get('blocked') as boolean)
                    }.defaultIfEmpty(NullUser.INSTANCE)
                }
        .flatMap { user ->
            if (user !== NullUser.INSTANCE && !user.blocked) {
                JWT jwt = new JWT(user.uuid, getUserType(user), env.getProperty('fed.auth.jwt-secret-b64'))
                def signInRes = new SignInResponse(jwt.toString(), jwt.expireDateTimeUTC, jwt.type)
                return ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(signInRes))
            } else {
                return ServerResponse.status(HttpStatusCode.valueOf(403)).build()
            }
        }
    }

    private static String getUserType(FedUser user) {
        if (user instanceof FedAdmin)
            return 'ADMIN'
        else if (user instanceof FedBankEmployee)
            return 'BANK_EMPLOYEE'
        else return 'CLIENT'
    }

    private static String getSHA256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256")
            byte[] hash = digest.digest(base.getBytes("UTF-8"))
            StringBuilder hexString = new StringBuilder()

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b)
                if (hex.length() == 1) hexString.append('0')
                hexString.append(hex)
            }

            return hexString.toString()
        } catch (Exception ex) {
            throw new RuntimeException(ex)
        }
    }

}
