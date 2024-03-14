package org.happinessmeta.last.resume.domain.repository;

import org.happinessmeta.last.resume.domain.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
}
