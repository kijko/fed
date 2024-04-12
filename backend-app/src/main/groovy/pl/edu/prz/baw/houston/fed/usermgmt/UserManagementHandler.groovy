package pl.edu.prz.baw.houston.fed.usermgmt

import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
import pl.edu.prz.baw.houston.fed.auth.AuthUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class UserManagementHandler implements RouteHandler {
    private final DefaultDSLContext ctx

    UserManagementHandler(DefaultDSLContext ctx) {
        this.ctx = ctx
    }

    @Override
    Mono<ServerResponse> get(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(Flux.from(ctx.select().from('fed_user'))
                .map { rec ->
                    return new UserResponse(
                            rec.get('uuid') as String,
                            rec.get('firstname') as String,
                            rec.get('lastname') as String,
                            rec.get('type') as String,
                            rec.get('blocked') as boolean
                    )
                }, UserResponse.class)
    }

    @Override
    Mono<ServerResponse> patch(ServerRequest request) {
        String uuid = request.pathVariable('id')
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(request.bodyToMono(BlockUserRequest.class).flatMap {
            return Mono.from(ctx.update(DSL.table("fed_user"))
                    .set(DSL.field('blocked'), it.blocked)
                    .where(DSL.field('uuid').eq(uuid))
            ).defaultIfEmpty(0)
        }.flatMap {
            return Mono.from(ctx.select().from('fed_user').where(DSL.field('uuid').eq(uuid)))
        }.map { rec ->
            return new UserResponse(
                    rec.get('uuid') as String,
                    rec.get('firstname') as String,
                    rec.get('lastname') as String,
                    rec.get('type') as String,
                    rec.get('blocked') as boolean
            )
        }, UserResponse.class)
    }

    @Override
    Mono<ServerResponse> post(ServerRequest request) {
        String uuid = UUID.randomUUID().toString()

        Mono<UserResponse> responseBody = request.bodyToMono(UserRequest.class).flatMap {
            Mono<UserResponse> res = Mono.from(ctx.transactionPublisher { trx ->
                return Mono.from(
                        trx.dsl()
                                .insertInto(
                                        DSL.table("fed_user"),
                                        DSL.field('uuid'),
                                        DSL.field('login_hash'),
                                        DSL.field('password_hash'),
                                        DSL.field('firstname'),
                                        DSL.field('lastname'),
                                        DSL.field('blocked'),
                                        DSL.field('type')
                                ).values(
                                uuid,
                                AuthUtils.getSHA256(it.login),
                                AuthUtils.getSHA256(it.password),
                                it.firstname,
                                it.lastname,
                                false,
                                it.type
                        )
                ).flatMap { resultInt ->
                    return Mono.from(trx.dsl()
                            .insertInto(DSL.table('account'), DSL.field('account_number'), DSL.field('owner_id'), DSL.field('balance'))
                            .values(getRandom28DigitsAsString(), uuid, new BigDecimal("0.0")))
                }.flatMap { resultInt ->
                    return Mono.from(trx.dsl()
                            .select().from('fed_user').where(DSL.field('uuid').eq(uuid)))
                }.map { rec ->
                    return new UserResponse(
                            rec.get('uuid') as String,
                            rec.get('firstname') as String,
                            rec.get('lastname') as String,
                            rec.get('type') as String,
                            rec.get('blocked') as boolean
                    )
                }
            })

            return res
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(responseBody, UserResponse.class)
    }

    @Override
    Mono<ServerResponse> post2(ServerRequest request) {
        String uuid = UUID.randomUUID().toString()

        Mono<UserResponse> responseBody = request.bodyToMono(UserRegisterRequest.class).flatMap {
            if (it.firstname == null || it.firstname.trim().isEmpty() || it.lastname == null || it.lastname.trim().isEmpty() || it.login == null || it.login.trim().isEmpty() || it.password == null || it.password.trim().isEmpty()) {
                throw new RuntimeException("you fucked up")
            }

            Mono<UserResponse> res = Mono.from(ctx.transactionPublisher { trx ->
                return Mono.from(
                        trx.dsl()
                                .insertInto(
                                        DSL.table("fed_user"),
                                        DSL.field('uuid'),
                                        DSL.field('login_hash'),
                                        DSL.field('password_hash'),
                                        DSL.field('firstname'),
                                        DSL.field('lastname'),
                                        DSL.field('blocked'),
                                        DSL.field('type')
                                ).values(
                                uuid,
                                AuthUtils.getSHA256(it.login),
                                AuthUtils.getSHA256(it.password),
                                it.firstname,
                                it.lastname,
                                true,
                                "CLIENT"
                        )
                ).flatMap { resultInt ->
                    return Mono.from(trx.dsl()
                            .insertInto(DSL.table('account'), DSL.field('account_number'), DSL.field('owner_id'), DSL.field('balance'))
                            .values(getRandom28DigitsAsString(), uuid, new BigDecimal("0.0")))
                }.flatMap { resultInt ->
                    return Mono.from(trx.dsl()
                            .select().from('fed_user').where(DSL.field('uuid').eq(uuid)))
                }.map { rec ->
                    return new UserResponse(
                            rec.get('uuid') as String,
                            rec.get('firstname') as String,
                            rec.get('lastname') as String,
                            rec.get('type') as String,
                            rec.get('blocked') as boolean
                    )
                }
            })

            return res
        }

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(responseBody, UserResponse.class)
    }

    private static getRandom28DigitsAsString() {
        // Create a random instance
        Random random = new Random()

        // Generate a 28-character long string of random digits
        String randomDigits = (1..28).collect { random.nextInt(10) }.join()

        return randomDigits
    }
}
