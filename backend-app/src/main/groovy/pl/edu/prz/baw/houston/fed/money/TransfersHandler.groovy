package pl.edu.prz.baw.houston.fed.money


import org.jooq.impl.DefaultDSLContext
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import pl.edu.prz.baw.houston.fed.RouteHandler
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

        Flux<TransferRequestInfo> response = Flux.from(
                ctx.select(
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
                        .where(TRANSFER_REQUEST.STATUS.eq("PENDING"))
        ).map {
            return new TransferRequestInfo(
                    "${it.get('from_firstname')} ${it.get('from_lastname')}",
                    "${it.get('to_firstname')} ${it.get('to_lastname')}",
                    it.get('amount') as String,
                    (it.get('requested_at') as LocalDateTime).atZone(ZoneId.of('UTC')),
                    status
            )
        }

        return ServerResponse.ok().body(response, TransferRequestInfo.class)
    }

    @Override
    Mono<ServerResponse> post(ServerRequest request) {
    }


}
