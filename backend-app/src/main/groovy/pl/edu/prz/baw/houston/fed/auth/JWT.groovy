package pl.edu.prz.baw.houston.fed.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.crypto.SecretKey
import java.time.ZoneId
import java.time.ZonedDateTime

class JWT {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWT.class)
    ZonedDateTime expireDateTimeUTC
    String uuid
    String raw
    String type

    JWT(String uuid, String type, String base64Secret) {
        this.raw = 'todo'
        this.expireDateTimeUTC = ZonedDateTime.now(ZoneId.of('UTC')).plusMinutes(60)
        this.type = type

//        SecretKey key = Jwts.SIG.HS512.key().build()
//        SecretKey key2 = Jwts.SIG.HS512.key().random(new SecureRandom('changeit'.getBytes('UTF-8'))).build()
//        System.out.println(Encoders.BASE64.encode(key.getEncoded()))
//        System.out.println(Encoders.BASE64.encode(key2.getEncoded()))

        SecretKey k = parseSecret(base64Secret)
        def compact = Jwts.builder()
                .subject(uuid)
                .claim('type', type)
                .expiration(Date.from(expireDateTimeUTC.toInstant()))
                .signWith(k, Jwts.SIG.HS512)
                .compact()

        this.raw = compact
    }

    JWT(String uuid, String type, ZonedDateTime expireDateTimeUTC) {
        this.uuid = uuid
        this.type = type
        this.expireDateTimeUTC = expireDateTimeUTC
    }

    static JWT parseAndVerify(String rawToken, String base64Secret) {
        try {
            Claims parsedToken = Jwts.parser().verifyWith(parseSecret(base64Secret)).build().parse(rawToken).getPayload() as Claims

            String uuid = parsedToken.getSubject()
            String type = parsedToken.get('type', String.class)
            ZonedDateTime expireAt = ZonedDateTime.ofInstant(parsedToken.getExpiration().toInstant(), ZoneId.of('UTC'))

            return new JWT(uuid, type, expireAt)
        } catch (Exception e) {
            LOGGER.error('exception occurred parsing jwt', e)

            return null
        }
    }

    private static SecretKey parseSecret(String b64Secret) {
       return Keys.hmacShaKeyFor(Decoders.BASE64.decode(b64Secret))
    }

    @Override
    String toString() {
        return raw
    }

}
