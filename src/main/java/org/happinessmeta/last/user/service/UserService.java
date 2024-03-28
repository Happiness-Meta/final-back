package org.happinessmeta.last.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.UserNameDuplicatedException;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.dto.UserResponse;
import org.happinessmeta.last.user.dto.UserUpdateRequest;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;


    public UserResponse findUserByEmail(String email) {
        return UserResponse.toDto(repository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    public List<UserResponse> findAllUser() {
        return repository.findAll().stream().map(UserResponse::toDto).collect(toList());
    }

    // todo: 사용자별로 개인 정보 업데이트 메서드를 구현하지 않았을 때 발생할 수 있는 문제점 파악하기.
    // todo: 비밀번호를 바꾸지 않은 상태에서 put 요청을 보낸다면? 분명히 null이 받아질 것이다.
    // todo: 변경된 부분만 바꾸고 싶을 때는? dirty check 하는 방법은?
    @Transactional
    public UserResponse updateUser(String email, UserUpdateRequest request) {
        User findUser = repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        validateDuplicatedName(request.getName());
        findUser.changeName(request.getName());
        findUser.changePassword(passwordEncoder.encode(request.getPassword()));
        findUser.changePosition(request.getPosition());
        findUser.changeTechStack(request.getTechStack());
        findUser.changeIndustry(request.getIndustry());
        findUser.changeTelephone(request.getTelephone());
        findUser.changeAddress(request.getAddress());
        return UserResponse.toDto(findUser);
    }

    private void validateDuplicatedName(String name) {
        if (repository.findByName(name).isPresent()) {
            throw new UserNameDuplicatedException();
        }
    }
}
