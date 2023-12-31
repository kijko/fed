package pl.edu.prz.baw.houston.fed.auth

import java.time.LocalDateTime
import java.time.ZonedDateTime

class SignInResponse {
    String jwt
    ZonedDateTime expireAt
    String type

    SignInResponse(String jwt, ZonedDateTime expireAt, String type) {
        this.jwt = jwt
        this.expireAt = expireAt
        this.type = type
    }
}
