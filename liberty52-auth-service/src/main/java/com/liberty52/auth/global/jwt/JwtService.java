package com.liberty52.auth.global.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.liberty52.auth.global.exception.external.unauthorized.AuthNotFoundException;
import com.liberty52.auth.user.entity.Auth;
import com.liberty52.auth.user.entity.Role;
import com.liberty52.auth.user.repository.AuthRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.access.expiration}")
    private Long ACCESS_TOKEN_EXPIRATION;

    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_TOKEN_EXPIRATION;

    @Value("${jwt.access.header}")
    private String ACCESS_HEADER;

    @Value("${jwt.refresh.header}")
    private String REFRESH_HEADER;

    @Value("${jwt.value.prefix.bearer}")
    private String PREFIX_BEARER;

    @Value("${jwt.value.prefix.basic}")
    private String PREFIX_BASIC;

    @Value("${jwt.value.claim.auth-id}")
    private String CLAIM_AUTH_ID;

    @Value("${jwt.value.claim.role}")
    private String CLAIM_ROLE;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";

    private final AuthRepository authRepository;

    public String createAccessToken(String id, Role role) {
        Date now = new Date();
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION))
                .withClaim(CLAIM_AUTH_ID, id)
                .withClaim(CLAIM_ROLE, role.getKey())
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(ACCESS_HEADER, accessToken);
        log.debug("재발급된 Access Token : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.debug("Access Token, Refresh Token 헤더 설정 완료");
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(REFRESH_HEADER))
                .filter(refreshToken -> refreshToken.startsWith(PREFIX_BEARER))
                .map(refreshToken -> refreshToken.replace(PREFIX_BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(ACCESS_HEADER))
                .filter(token -> token.startsWith(PREFIX_BEARER))
                .map(token -> token.replace(PREFIX_BEARER, ""));
    }

    public Optional<String> extractAuthId(String accessToken) {
        try {
            Optional<String> result = Optional.ofNullable(JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(accessToken)
                    .getClaim(CLAIM_AUTH_ID)
                    .asString());

            return result;
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_HEADER, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_HEADER, refreshToken);
    }

    public void updateRefreshToken(String email, String updateRefreshToken) {
        authRepository.findByEmail(email)
            .ifPresentOrElse(
                user -> {
                    user.updateRefreshToken(updateRefreshToken);
                    authRepository.save(user);
                }, AuthNotFoundException::new
            );
    }

    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(SECRET_KEY)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public String createTokensAndAddHeaders(Auth auth, boolean isAutoLogin, HttpServletResponse response) {
        final String access = "access"; // 위 상수와 일치시키고 싶지만, FE와 맞춰야 하기 때문에 일단 유지. 추후 수정 예정.
        final String refresh = "refresh";

        String accessToken = this.createAccessToken(auth.getId(), auth.getRole());
        response.addHeader(access, PREFIX_BEARER + accessToken);
        String refreshToken = null;
        if (isAutoLogin) {
            refreshToken = this.createRefreshToken();
            response.addHeader(refresh, refreshToken);
        }
        return refreshToken;
    }
}
