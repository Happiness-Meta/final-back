package org.happinessmeta.last.resume.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.happinessmeta.last.resume.domain.entity.Resume;
import org.happinessmeta.last.resume.dto.sub.WorkExperienceDto;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record CreateResumeDto(
        @NotBlank(message = "Name is required")
        String name,
        String contactEmail,
        String contactCellphone,
        String intro,
        @NotNull(message = "Education field is required.")
        List<String> education,
        List<String> certificate,
        List<String> activities,
        List<String> awards,
        List<WorkExperienceDto> workExperience,
        List<Long> projectId


) {
    public Resume toEntity() {
        return Resume.builder()
                .name(name)
                .contactEmail(contactEmail)
                .contactCellphone(contactCellphone)
                .intro(intro)
                .awards(awards)
                .education(education)
                .activities(activities)
                .certificate(certificate)
                .workExperience(workExperience.stream().map(WorkExperienceDto::toEntity).collect(Collectors.toList()))
                .build();
    }
}
