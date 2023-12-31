package pl.edu.prz.baw.houston.fed.auth

import org.jooq.impl.DSL
import org.jooq.impl.DefaultDSLContext
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserDetailsService {

    private final DefaultDSLContext ctx

    UserDetailsService(DefaultDSLContext ctx) {
        this.ctx = ctx
    }

    Mono<FedUser> findUser(String uuid) {
        return Mono.from(ctx.select().from('fed_user').where(DSL.field('uuid').eq(uuid)))
            .map { rec ->
                FedUser.ofType(rec.get('type') as String, rec.get('uuid') as String, rec.get('blocked') as boolean)
            }
    }
}
