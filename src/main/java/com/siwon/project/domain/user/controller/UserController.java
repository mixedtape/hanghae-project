package com.siwon.project.domain.user.controller;

import com.siwon.project.domain.user.dto.RefreshTokenDTO;
import com.siwon.project.domain.user.service.AuthService;

import com.siwon.project.domain.user.UserDetailsImpl;
import com.siwon.project.domain.user.dto.ChangePasswordDTO;
import com.siwon.project.domain.user.dto.EmailCheckDto;
import com.siwon.project.domain.user.dto.EmailRequestDto;
import com.siwon.project.domain.user.dto.UpdateUserProfileRequestDTO;
import com.siwon.project.domain.user.dto.UserInfoResponseDTO;
import com.siwon.project.domain.user.dto.UserLoginRequestDTO;
import com.siwon.project.domain.user.dto.UserSignupRequestDTO;
import com.siwon.project.domain.user.entity.User;
import com.siwon.project.domain.user.service.EmailService;
import com.siwon.project.domain.user.service.UserService;
import com.siwon.project.global.common.CommonResponse;
import com.siwon.project.global.exception.user.InvalidEmailCodeException;
import com.siwon.project.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    private final AuthService authService;
    private final EmailService mailService;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> signup(
            @Valid @RequestBody UserSignupRequestDTO requestDTO) {

        userService.signup(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of("회원가입 성공", null));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> getProfile(
            @PathVariable Long userId) {

        UserInfoResponseDTO responseDTO = userService.getProfile(userId);
        return ResponseEntity.ok().body(CommonResponse.of("프로필 조회 성공", responseDTO));
    }

    @PostMapping("/mail")
    public String mailSend(@RequestBody @Valid EmailRequestDto emailDto) {
        System.out.println("이메일 인증 이메일 :" + emailDto.getEmail());
        return mailService.joinEmail(emailDto.getEmail());
    }

    @PostMapping("/mailAuth")
    public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        boolean Checked = mailService.CheckAuthNum(emailCheckDto.getEmail(),
                emailCheckDto.getAuthNum());
        if (Checked) {
            return "ok";
        } else {
            throw new InvalidEmailCodeException();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserInfoResponseDTO>> login(
            @RequestBody UserLoginRequestDTO requestDTO, HttpServletResponse response) {

        UserInfoResponseDTO userInfo = userService.login(requestDTO);

        // 액세스 토큰 생성 및 헤더에 저장
        String accessToken = jwtUtil.createToken(requestDTO.getUsername());
        response.setHeader("Access-Token", accessToken);

        // 리프레시 토큰 생성 및 DB에 저장
        String refreshToken = authService.createRefreshToken(userInfo.getUserId());
        response.setHeader("Refresh-Token", refreshToken);

        return ResponseEntity.ok().body(CommonResponse.of("로그인 성공", userInfo));
    }

    @GetMapping("{userId}/password")
    public ResponseEntity<CommonResponse<Void>> checkPassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordDTO changePasswordDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        userService.passwordCheck(userId,changePasswordDTO,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("패스워드 확인 완료", null));
    }

    @PatchMapping("/{userId}/new-password")
    public ResponseEntity<CommonResponse<Void>> changePassword(
            @PathVariable Long userId,
            @RequestBody ChangePasswordDTO changePasswordDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        userService.changePassword(userId,changePasswordDTO,userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.of("패스워드 변경 완료", null));
    }
    @PatchMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> updateProfile(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserProfileRequestDTO requestDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User loginUser = userDetails.getUser();
        userService.updateProfile(userId, requestDTO, loginUser);

        return ResponseEntity.ok().body(CommonResponse.of("프로필 수정 성공", null));
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<CommonResponse<String>> refreshToken(
            @RequestBody RefreshTokenDTO refreshTokenDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String newAccessToken = authService.createAccessTokenWithRefreshToken(refreshTokenDTO.getRefreshToken(),userDetails.getUsername());

        return ResponseEntity.ok().body(CommonResponse.of("액세스 토큰 갱신 성공", newAccessToken));
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        userService.deleteUser(userDetails.getUser().getId());
        return ResponseEntity.ok().body(CommonResponse.of("유저 삭제 성공",null));
    }
}
