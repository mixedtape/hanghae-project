package com.siwon.project.domain.user.controller;

import com.siwon.project.domain.user.Service.UserService;
import com.siwon.project.domain.user.dto.UserInfoResponseDTO;
import com.siwon.project.domain.user.dto.UserSignupRequestDTO;
import com.siwon.project.global.common.CommonResponse;
import com.siwon.project.global.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<Void>> signup(
            @Valid @RequestBody UserSignupRequestDTO requestDTO) {

        userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("회원가입 성공", null));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> getProfile(@PathVariable Long userId) {

        UserInfoResponseDTO responseDTO = userService.getProfile(userId);
        return ResponseEntity.ok().body(CommonResponse.of("프로필 조회 성공", responseDTO));
    }
}
