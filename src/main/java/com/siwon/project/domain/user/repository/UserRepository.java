package com.siwon.project.domain.user.repository;

import com.siwon.project.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.is_deleted = false and u.email = :email")
    Optional<User> findUserByEmailAndIs_deletedFalse(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.is_deleted = false and u.username = :username")
    Optional<User> findUserByUsernameAndIs_deletedFalse(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.is_deleted = :isDeleted AND u.deletedAt < :dateTime")
    List<User> findByIsDeletedAndDeletedAtBefore(@Param("isDeleted") boolean is_deleted, @Param("dateTime") LocalDateTime dateTime);

}
