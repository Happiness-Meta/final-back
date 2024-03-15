package org.happinessmeta.last.user.service;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.common.exception.UserNameDuplicatedException;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.dto.UserResponse;
import org.happinessmeta.last.user.dto.UserUpdate;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserService {
    UserRepository repository;
    PasswordEncoder passwordEncoder;
    public UserResponse findUserByEmail(String email) {
        return UserResponse.convertUserToDto(repository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    public List<UserResponse> findAllUser() {
        return repository.findAll().stream().map(UserResponse::convertUserToDto).collect(toList());
    }

    public UserResponse updateUser(String email, UserUpdate request) {
        User findUser = repository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        validateDuplicatedName(request.getName());
        findUser.changeName(request.getName());
        findUser.changePassword(passwordEncoder.encode(request.getPassword()));
        findUser.changePosition(request.getPosition());
        findUser.changeTechStack(request.getTechStack());
        return UserResponse.convertUserToDto(findUser);
    }

    private void validateDuplicatedName(String name) {
        if (repository.findByName(name).isPresent()) {
            throw new UserNameDuplicatedException();
        }
    }
}
