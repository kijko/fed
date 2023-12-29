package pl.edu.prz.baw.houston.fed.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys

import javax.crypto.SecretKey
import java.time.ZoneId
import java.time.ZonedDateTime

class JWT {
    ZonedDateTime expireDateTimeUTC
    String raw
    String type

    JWT(String uuid, String type) {
        this.raw = 'todo'
        this.expireDateTimeUTC = ZonedDateTime.now(ZoneId.of('UTC')).plusMinutes(5)
        this.type = type

        // todo change me to static password
//        SecretKey key = Jwts.SIG.HS512.key().build()
//        SecretKey key2 = Jwts.SIG.HS512.key().random(new SecureRandom('changeit'.getBytes('UTF-8'))).build()
//        System.out.println(Encoders.BASE64.encode(key.getEncoded()))
//        System.out.println(Encoders.BASE64.encode(key2.getEncoded()))

        SecretKey k = Keys.hmacShaKeyFor(Decoders.BASE64.decode('get secret from props here'))
        def compact = Jwts.builder()
                .subject(uuid)
                .claim('type', type)
                .expiration(Date.from(expireDateTimeUTC.toInstant()))
                .signWith(k, Jwts.SIG.HS512)
                .compact()

        this.raw = compact
    }

    @Override
    String toString() {
        return raw
    }
}
