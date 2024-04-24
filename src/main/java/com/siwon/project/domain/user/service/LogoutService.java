package com.siwon.project.domain.user.service;

import com.siwon.project.domain.user.entity.Token;
import com.siwon.project.global.redis.RedisUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogoutService implements LogoutHandler {

    private final RedisUtil redisUtil;

    public LogoutService(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();


        redisUtil.deleteData(username);
    }
}
