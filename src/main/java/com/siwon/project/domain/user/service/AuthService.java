package com.siwon.project.domain.user.service;


import com.siwon.project.domain.user.entity.User;
import com.siwon.project.domain.user.repository.UserRepository;
import com.siwon.project.global.exception.jwt.ExpiredRefreshTokenException;
import com.siwon.project.global.exception.jwt.RevokedRefreshTokenException;
import com.siwon.project.global.exception.user.UserNotFoundException;
import com.siwon.project.global.jwt.JwtUtil;
import com.siwon.project.global.redis.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;


    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, RedisUtil redisUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
    }


    public String createRefreshToken(Long userId) {

        // DB에서 User 객체를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // 토큰 만료시간 7일
        long TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000L;

        // JwtUtil 클래스의 메서드를 호출하여 리프레시 토큰을 생성
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), TOKEN_VALIDITY);

        redisUtil.setToken(user.getUsername(), refreshToken);

        return "Bearer " + refreshToken;
    }


    public String createAccessTokenWithRefreshToken(String refreshToken, String userName) {

        try {
            // 리프레시 토큰을 검증
            jwtUtil.validationToken(refreshToken);

            // 리프레시 토큰이 사용 중지되었다면 예외를 발생
            if (!redisUtil.isTokenExists(userName)) {
                throw new RevokedRefreshTokenException();
            }
            String storedRefreshToken = redisUtil.getData(userName);
            if(!Objects.equals(storedRefreshToken, refreshToken)){

                throw new RevokedRefreshTokenException();
            }
            return jwtUtil.createToken(userName);

        } catch (ExpiredJwtException e) {
            // 리프레시 토큰이 만료되었다면 예외를 발생
            throw new ExpiredRefreshTokenException();
        }
    }
}