package com.siwon.project.domain.user.service;

import com.siwon.project.domain.user.dto.ChangePasswordDTO;
import com.siwon.project.domain.user.dto.UpdateUserProfileRequestDTO;
import com.siwon.project.domain.user.dto.UserInfoResponseDTO;
import com.siwon.project.domain.user.dto.UserLoginRequestDTO;
import com.siwon.project.domain.user.dto.UserSignupRequestDTO;
import com.siwon.project.domain.user.entity.User;
import com.siwon.project.domain.user.repository.UserRepository;
import com.siwon.project.global.exception.user.AlreadyExistEmailException;
import com.siwon.project.global.exception.user.AlreadyExistUsernameException;
import com.siwon.project.global.exception.user.AuthenticationMismatchException;
import com.siwon.project.global.exception.user.PasswordConfirmationException;
import com.siwon.project.global.exception.user.UserNotFoundException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(UserSignupRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String password = requestDTO.getPassword();
        String checkPassword = requestDTO.getCheckPassword();
        String email = requestDTO.getEmail();
        String address = requestDTO.getAddress();
        String phoneNum = requestDTO.getPhoneNum();

        // 비밀번호 != 비밀번호 확인
        if (!Objects.equals(password, checkPassword)) {
            throw new PasswordConfirmationException();
        }

        String encodePassword = passwordEncoder.encode(password);

        // 유저네임 중복확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AlreadyExistUsernameException();
        }

        // 이메일 중복확인
        if (userRepository.findByEmail(email).isPresent()) {
            throw new AlreadyExistEmailException();
        }

        User user = new User(username, encodePassword, email, address, phoneNum);
        userRepository.save(user);
    }

    public UserInfoResponseDTO getProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return new UserInfoResponseDTO(user);
    }

    public UserInfoResponseDTO login(UserLoginRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        String password = requestDTO.getPassword();

        // 저장된 회원이 없을 때
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new UserNotFoundException();
        }

        // 로그인 성공시 UserInfoResponseDTO로 변환하여 반환
        return new UserInfoResponseDTO(user);
    }

    @Transactional
    public void updateProfile(Long userId, UpdateUserProfileRequestDTO requestDTO, User loginUser) {

        User user = getUser(userId);

        if (!loginUser.getId().equals(user.getId())) {
            throw new AuthenticationMismatchException();
        }

        user.updateProfile(requestDTO);
    }

    public void passwordCheck(Long userId, ChangePasswordDTO changePasswordDTO, User loginUser) {
        User user = getUser(userId);
        if (!loginUser.getId().equals(user.getId())) {
            throw new AuthenticationMismatchException();
        }
        if (!passwordEncoder.matches(changePasswordDTO.getPassword(), user.getPassword())) {
            throw new UserNotFoundException();
        }
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordDTO changePasswordDTO, User loginUser) {
        User user = getUser(userId);
        if (!loginUser.getId().equals(user.getId())) {
            throw new AuthenticationMismatchException();
        }
        String encodePassword = passwordEncoder.encode(changePasswordDTO.getPassword());
        user.changePassword(encodePassword);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
