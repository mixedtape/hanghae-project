package com.siwon.project.domain.user.Service;

import com.siwon.project.domain.user.dto.UserInfoResponseDTO;
import com.siwon.project.domain.user.dto.UserSignupRequestDTO;
import com.siwon.project.domain.user.entity.User;
import com.siwon.project.domain.user.repository.UserRepository;
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

        // 비밀번호 != 비밀번호 확인
        if (!Objects.equals(password, checkPassword)) {
            throw new PasswordConfirmationException();
        }

        String encodePassword = passwordEncoder.encode(password);

        // 유저네임 중복확인

        User user = new User(username, encodePassword, email);
        userRepository.save(user);
    }
    public UserInfoResponseDTO getProfile(Long userId) {
        User user =  userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return new UserInfoResponseDTO(user);
    }
}
