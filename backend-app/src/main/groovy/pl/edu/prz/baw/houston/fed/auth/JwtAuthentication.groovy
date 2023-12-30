package pl.edu.prz.baw.houston.fed.auth

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class JwtAuthentication extends AbstractAuthenticationToken {
    static class JwtAuthGrantedAuthority implements GrantedAuthority {

        private String type

        JwtAuthGrantedAuthority(String type) {
            this.type = type
        }

        @Override
        String getAuthority() {
            return "ROLE_$type"
        }
    }

    private final JWT jwt

    JwtAuthentication(JWT jwt) {
        super(Arrays.asList(new JwtAuthGrantedAuthority(jwt.type)))

        setAuthenticated(true)
        this.jwt = jwt
    }

    @Override
    Object getCredentials() {
        return null
    }

    @Override
    Object getPrincipal() {
        return jwt.uuid
    }

    JWT getJwt() {
        return jwt
    }
}
