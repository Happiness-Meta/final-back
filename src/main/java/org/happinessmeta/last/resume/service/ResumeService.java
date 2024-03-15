package org.happinessmeta.last.resume.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.happinessmeta.last.common.exception.UserNotFoundException;
import org.happinessmeta.last.resume.domain.entity.Resume;
import org.happinessmeta.last.resume.domain.repository.ResumeRepository;
import org.happinessmeta.last.resume.dto.CreateResumeDto;
import org.happinessmeta.last.user.domain.User;
import org.happinessmeta.last.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public Long createResume(CreateResumeDto requestDto, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        Resume newResume = requestDto.toEntity();
        newResume.putUser(user);
        Resume savedResume = resumeRepository.save(newResume);

        return savedResume.getId();

    }
}
