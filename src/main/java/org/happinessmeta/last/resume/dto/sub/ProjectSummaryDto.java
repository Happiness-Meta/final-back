package org.happinessmeta.last.resume.dto.sub;

import jakarta.validation.constraints.AssertTrue;
import org.happinessmeta.last.resume.domain.entity.ProjectSummary;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link ProjectSummary}
 */
public record ProjectSummaryDto(String projectName,
                                LocalDate projectStartDate,
                                LocalDate projectEndDate,
                                String description) implements Serializable {
    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return projectEndDate.isEqual(projectStartDate) || projectEndDate.isAfter(projectStartDate);
    }

    public ProjectSummary toEntity(){
        return ProjectSummary.builder()
                .projectName(projectName)
                .description(description)
                .projectStartDate(projectStartDate)
                .projectEndDate(projectEndDate)
                .build();
    }
}