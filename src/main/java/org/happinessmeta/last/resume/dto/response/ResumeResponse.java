package org.happinessmeta.last.resume.dto.response;

import org.happinessmeta.last.portfolio.dto.response.PortfolioSummaryDto;
import org.happinessmeta.last.resume.domain.entity.Resume;
import org.happinessmeta.last.resume.dto.sub.WorkExperienceDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link Resume}
 */
public record ResumeResponse(LocalDateTime lastModifiedAt, Long id, List<PortfolioSummaryDto> portfolioComponents,
                             String name, String contactCellphone, String contactEmail, String intro,
                             List<WorkExperienceDto> workExperience, List<String> education, List<String> certificate,
                             List<String> activities, List<String> awards) implements Serializable {
}