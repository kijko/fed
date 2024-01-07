package pl.edu.prz.baw.houston.fed.money

import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class AccountHandler implements RouteHandler {
    private final DefaultDSLContext ctx

    AccountHandler(DefaultDSLContext ctx) {
        this.ctx = ctx
    }

    @Override
    Mono<ServerResponse> post(ServerRequest request) {
        return ReactiveSecurityContextHolder.getContext().flatMap { secContext ->
            String clientId = secContext.authentication.principal as String

            Mono<String> clientAccountNumberMono = Mono.from(
                    ctx.select().from(DSL.table('account')).where(DSL.field('owner_id').eq(clientId))
            ).map {
                return it.get('account_number') as String
            }

            return clientAccountNumberMono.flatMap { clientAccountNumber ->
                return request.bodyToMono(TransferMoneyRequest.class).flatMap { req ->
                    return Mono.from(
                            ctx.select().from(DSL.table('account')).where(DSL.field('account_number').eq(req.toAccountNumber))
                    ).map {
                        return it.get('account_number') as String
                    }.defaultIfEmpty('NOT_FOUND')
                    .flatMap { toAccountNumber ->
                        return Mono.from(
                                ctx.insertInto(
                                        DSL.table('transfer_request'),
                                        DSL.field('id'),
                                        DSL.field('from_account'),
                                        DSL.field('to_account'),
                                        DSL.field('status'),
                                        DSL.field('finalized_by'),
                                        DSL.field('amount'),
                                        DSL.field('requested_at')
                                ).values(
                                        UUID.randomUUID().toString(),
                                        clientAccountNumber,
                                        toAccountNumber,
                                        MoneyTransferStatus.PENDING.toString(),
                                        null,
                                        new BigDecimal(req.amount),
                                        ZonedDateTime.now(ZoneId.of('UTC')).toInstant()
                                )
                        ).map {
                            it == 1
                        }
                    }
                }
            }

        }.flatMap { success ->
                if (success) {
                    return ServerResponse.noContent().build()
                } else {
                    return ServerResponse.badRequest().build()
                }
        }
    }

    @Override
    Mono<ServerResponse> get(ServerRequest request) {
        return ReactiveSecurityContextHolder.getContext().flatMap { secContext ->
            String clientId = secContext.authentication.principal as String

            return Mono.from(ctx.select().from(DSL.table('fed_user')).where(DSL.field('uuid').eq(clientId)))
                .flatMap { userSqlRecord ->
                    return Mono.from(ctx.select().from(DSL.table('account')).where(DSL.field('owner_id').eq(clientId)))
                        .flatMap { accountSqlRecord ->
                            String clientAccountNumber = accountSqlRecord.get('account_number') as String

                            return Flux.from(ctx.select().from(DSL.table('transfer_request')).where(DSL.field('from_account').eq(clientAccountNumber)))
                                .collectList()
                                .flatMap { outgoingTransferSqlRecords ->
                                    return Flux.from(ctx.select().from(DSL.table('transfer_request')).where(DSL.field('to_account').eq(clientAccountNumber)))
                                        .collectList()
                                        .map { incomingTransferSqlRecords ->
                                            String firstname = userSqlRecord.get('firstname') as String
                                            String lastname = userSqlRecord.get('lastname') as String

                                            return new ClientInfo(
                                                    clientAccountNumber,
                                                    "${firstname} ${lastname}",
                                                    firstname,
                                                    lastname,
                                                    (accountSqlRecord.get('balance') as BigDecimal).toString(),
                                                    packMoneyTransfer(outgoingTransferSqlRecords),
                                                    packMoneyTransfer(incomingTransferSqlRecords)
                                            )
                                        }
                                }
                        }
                }
        }.flatMap {
            return ServerResponse.ok().body(Mono.just(it), ClientInfo.class)
        }
    }

    private static List<MoneyTransferDetails> packMoneyTransfer(List<org.jooq.Record> moneyTransferSqlRecords) {
        return moneyTransferSqlRecords.collect {
            return new MoneyTransferDetails(
                    (it.get('amount') as BigDecimal).toString(),
                    it.get('from_account') as String,
                    it.get('to_account') as String,
                    (it.get('requested_at') as java.sql.Timestamp).toInstant().atZone(ZoneId.of("UTC")),
                    MoneyTransferStatus.valueOf(it.get('status') as String)
            )
        }
    }
}
