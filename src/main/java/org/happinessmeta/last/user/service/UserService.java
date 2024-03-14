package org.happinessmeta.last.user.service;

import lombok.RequiredArgsConstructor;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.dto.UserResponse;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class UserService {
    UserRepository repository;
    public UserResponse findUserByEmail(String email) {
        return UserResponse.convertUserToDto(repository.findByEmail(email).orElseThrow(UserNotFoundException::new));
    }

    public List<UserResponse> findAllUser() {
        return repository.findAll().stream().map(UserResponse::convertUserToDto).collect(toList());
    }
}
