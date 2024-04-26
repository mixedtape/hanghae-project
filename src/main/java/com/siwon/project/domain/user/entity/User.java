package com.siwon.project.domain.user.entity;

import com.siwon.project.domain.user.dto.UpdateUserProfileRequestDTO;
import com.siwon.project.global.auditing.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(nullable = false)
    private boolean is_deleted = false;

    private LocalDateTime deletedAt;

    @Column(nullable = false)
    private String address;


    @Column
    private String emailVerificationCode;

    public User(String username, String password, String email, String address, String phoneNum) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public void updateProfile(UpdateUserProfileRequestDTO requestDTO) {
        this.address = requestDTO.getAddress();
        this.phoneNum = requestDTO.getPhoneNum();
    }

    public void changePassword(String password) {
        this.password = password;
    }
    public void deleteUser(){
        this.is_deleted=true;
    }



}
