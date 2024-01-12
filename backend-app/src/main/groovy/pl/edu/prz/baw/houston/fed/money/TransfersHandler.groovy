package pl.edu.prz.baw.houston.fed.money

import org.jooq.Record7
import org.jooq.Record8
import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
import pl.edu.prz.baw.houston.fed.auth.AuthUtils
import pl.edu.prz.baw.houston.fed.usermgmt.UserResponse
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

import java.time.LocalDateTime
import java.time.ZoneId

import static pl.edu.prz.baw.houston.fed.db.Tables.*

@Component
class TransfersHandler implements RouteHandler {
    private final DefaultDSLContext ctx

    TransfersHandler(DefaultDSLContext ctx) {
        this.ctx = ctx
    }

    @Override
    Mono<ServerResponse> get(ServerRequest request) {
        MoneyTransferStatus status = MoneyTransferStatus.valueOf(request.queryParam('status').orElseThrow { new RuntimeException('unknown status') })

        Flux<TransferRequestInfo> response = getTransferRequestsByStatus(status).map {
            return new TransferRequestInfo(
                    it.get('req_id'),
                    "${it.get('from_firstname')} ${it.get('from_lastname')}",
                    "${it.get('to_firstname')} ${it.get('to_lastname')}",
                    it.get('amount') as String,
                    (it.get('requested_at') as LocalDateTime).atZone(ZoneId.of('UTC')),
                    status
            )
        }

        return ServerResponse.ok().body(response, TransferRequestInfo.class)
    }

    void asd() {
        Mono.from(ctx.transactionPublisher { trx ->
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
    }

    @Override
    Mono<ServerResponse> post(ServerRequest request) {
        return request.bodyToMono(ChangeTransferStatusRequest.class)
                .flatMap {
                    final String transRequestId = request.pathVariable('id')
                    final MoneyTransferStatus desiredStatus = MoneyTransferStatus.valueOf(it.status)

                    if (!(desiredStatus in [MoneyTransferStatus.APPROVED, MoneyTransferStatus.REJECTED])) {
                        throw new RuntimeException('status could be only APPROVED || REJECTED')
                    }

                    return Mono.from(
                            ctx.transactionPublisher { trx ->
                                final def dsl = trx.dsl()

                                return Mono.from(dsl.select(TRANSFER_REQUEST.asterisk()).from(TRANSFER_REQUEST).where(TRANSFER_REQUEST.ID.eq(transRequestId)))
                                    .flatMap { transRequestRecord ->

                                        // fail if approved or rejected (maybe not fail, just return obj)
                                        return Mono.from(dsl.update(TRANSFER_REQUEST).set(TRANSFER_REQUEST.STATUS, desiredStatus.toString()).where(TRANSFER_REQUEST.ID.eq(transRequestId)))
                                            .flatMap {
                                                if (desiredStatus == MoneyTransferStatus.APPROVED) {
                                                    return Mono.from(dsl.select(ACCOUNT.asterisk()).from(ACCOUNT).where(ACCOUNT.ACCOUNT_NUMBER.eq(transRequestRecord.get('from_account'))))
                                                            .flatMap { senderAccountRecord ->
                                                                return Mono.from(dsl.select(ACCOUNT.asterisk()).from(ACCOUNT).where(ACCOUNT.ACCOUNT_NUMBER.eq(transRequestRecord.get('to_account'))))
                                                                        .flatMap { recipientAccountRecord ->

                                                                            // UPDATE ACCOUNTS
                                                                            final BigDecimal amount = transRequestRecord.get('amount') as BigDecimal
                                                                            final BigDecimal senderBalance = senderAccountRecord.get('balance') as BigDecimal
                                                                            final BigDecimal recipientBalance = recipientAccountRecord.get('balance') as BigDecimal

                                                                            final BigDecimal senderBalanceAfterTrans = senderBalance.minus(amount)
                                                                            final BigDecimal recipientBalanceAfterTrans = recipientBalance.plus(amount)

                                                                            return Mono.from(dsl.update(ACCOUNT).set(ACCOUNT.BALANCE, senderBalanceAfterTrans).where(ACCOUNT.ACCOUNT_NUMBER.eq(transRequestRecord.get('from_account'))))
                                                                                    .flatMap {
                                                                                        return Mono.from(dsl.update(ACCOUNT).set(ACCOUNT.BALANCE, recipientBalanceAfterTrans).where(ACCOUNT.ACCOUNT_NUMBER.eq(transRequestRecord.get('to_account'))))
                                                                                            .flatMap {
                                                                                                Mono<TransferRequestInfo> res = getTransferRequestsById(transRequestId)
                                                                                                        .map {
                                                                                                            return new TransferRequestInfo(
                                                                                                                    it.get('req_id'),
                                                                                                                    "${it.get('from_firstname')} ${it.get('from_lastname')}",
                                                                                                                    "${it.get('to_firstname')} ${it.get('to_lastname')}",
                                                                                                                    it.get('amount') as String,
                                                                                                                    (it.get('requested_at') as LocalDateTime).atZone(ZoneId.of('UTC')),
                                                                                                                    MoneyTransferStatus.valueOf(it.get('status'))
                                                                                                            )
                                                                                                        }

                                                                                                return ServerResponse.ok().body(res, TransferRequestInfo.class)
                                                                                            }
                                                                                    }
                                                                        }
                                                            }
                                                }

                                                Mono<TransferRequestInfo> res = getTransferRequestsById(transRequestId)
                                                        .map {
                                                            return new TransferRequestInfo(
                                                                    it.get('req_id'),
                                                                    "${it.get('from_firstname')} ${it.get('from_lastname')}",
                                                                    "${it.get('to_firstname')} ${it.get('to_lastname')}",
                                                                    it.get('amount') as String,
                                                                    (it.get('requested_at') as LocalDateTime).atZone(ZoneId.of('UTC')),
                                                                    MoneyTransferStatus.valueOf(it.get('status'))
                                                            )
                                                        }

                                                return ServerResponse.ok().body(res, TransferRequestInfo.class)
                                            }
                                    }
                                // END TRANSACTION
                            }
                    )
                }
    }

    private Flux<Record7<?,?,?,?,?,?,?>> getTransferRequestsByStatus(MoneyTransferStatus status) {
        return Flux.from(
                ctx.select(
                        TRANSFER_REQUEST.ID.as('req_id'),
                        FED_USER.as("u1").FIRSTNAME.as('from_firstname'),
                        FED_USER.as("u1").LASTNAME.as('from_lastname'),
                        FED_USER.as("u2").FIRSTNAME.as('to_firstname'),
                        FED_USER.as("u2").LASTNAME.as('to_lastname'),
                        TRANSFER_REQUEST.AMOUNT.as('amount'),
                        TRANSFER_REQUEST.REQUESTED_AT.as('requested_at')
                )
                        .from(TRANSFER_REQUEST)
                        .innerJoin(ACCOUNT.as("ac1"))
                        .on(ACCOUNT.as("ac1").ACCOUNT_NUMBER.eq(TRANSFER_REQUEST.FROM_ACCOUNT))
                        .innerJoin(FED_USER.as("u1"))
                        .on(FED_USER.as("u1").UUID.eq(ACCOUNT.as("ac1").OWNER_ID))
                        .innerJoin(ACCOUNT.as("ac2"))
                        .on(ACCOUNT.as("ac2").ACCOUNT_NUMBER.eq(TRANSFER_REQUEST.TO_ACCOUNT))
                        .innerJoin(FED_USER.as("u2"))
                        .on(FED_USER.as("u2").UUID.eq(ACCOUNT.as("ac2").OWNER_ID))
                        .where(TRANSFER_REQUEST.STATUS.eq(status.toString()))
        )
    }

    private Mono<Record8<?,?,?,?,?,?,?,?>> getTransferRequestsById(String id) {
        return Mono.from(
                ctx.select(
                        TRANSFER_REQUEST.ID.as('req_id'),
                        FED_USER.as("u1").FIRSTNAME.as('from_firstname'),
                        FED_USER.as("u1").LASTNAME.as('from_lastname'),
                        FED_USER.as("u2").FIRSTNAME.as('to_firstname'),
                        FED_USER.as("u2").LASTNAME.as('to_lastname'),
                        TRANSFER_REQUEST.AMOUNT.as('amount'),
                        TRANSFER_REQUEST.REQUESTED_AT.as('requested_at'),
                        TRANSFER_REQUEST.STATUS.as('status')
                )
                        .from(TRANSFER_REQUEST)
                        .innerJoin(ACCOUNT.as("ac1"))
                        .on(ACCOUNT.as("ac1").ACCOUNT_NUMBER.eq(TRANSFER_REQUEST.FROM_ACCOUNT))
                        .innerJoin(FED_USER.as("u1"))
                        .on(FED_USER.as("u1").UUID.eq(ACCOUNT.as("ac1").OWNER_ID))
                        .innerJoin(ACCOUNT.as("ac2"))
                        .on(ACCOUNT.as("ac2").ACCOUNT_NUMBER.eq(TRANSFER_REQUEST.TO_ACCOUNT))
                        .innerJoin(FED_USER.as("u2"))
                        .on(FED_USER.as("u2").UUID.eq(ACCOUNT.as("ac2").OWNER_ID))
                        .where(TRANSFER_REQUEST.ID.eq(id))
        )
    }
}
