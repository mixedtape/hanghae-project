package com.siwon.project.domain.user.repository;


import com.siwon.project.domain.user.entity.Token;
import com.siwon.project.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Repository
public interface TokenRepository {

    List<Token> findByUser(User user);


    Token findByRefreshToken(String token);

    @Modifying
    @Transactional
    @Query("update Token t set t.expired = true, t.revoked = true where t.refreshToken = :token")
    void setExpiredAndRevoked(String token);
}
