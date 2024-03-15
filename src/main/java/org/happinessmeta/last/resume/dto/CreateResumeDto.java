package org.happinessmeta.last.resume.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.happinessmeta.last.resume.domain.entity.Resume;

import java.util.List;

@Builder
public record CreateResumeDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotNull(message = "Education field is required.")
        List<String> education,
        List<String> certificate,
        List<String> activities,
        List<String> awards

) {
    public Resume toEntity() {
        return Resume.builder()
                .name(name)
                .awards(awards)
                .education(education)
                .activities(activities)
                .certificate(certificate)
                .build();
    }
}
