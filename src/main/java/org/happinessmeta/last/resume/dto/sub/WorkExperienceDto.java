package org.happinessmeta.last.resume.dto.sub;

import jakarta.validation.constraints.AssertTrue;
import org.happinessmeta.last.resume.domain.entity.WorkExperience;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link WorkExperience}
 */
public record WorkExperienceDto(String companyName,
                                String position,
                                String department,
                                String currentStatus,
                                LocalDate employmentStartDate,
                                LocalDate employmentEndDate,
                                String responsibilities) implements Serializable {
    @AssertTrue(message = "End date must be after start date")
    public boolean isEndDateAfterStartDate() {
        return employmentEndDate.isEqual(employmentStartDate) || employmentEndDate.isAfter(employmentStartDate);
    }

    public WorkExperience toEntity(){
        return WorkExperience.builder()
                .companyName(companyName)
                .position(position)
                .department(department)
                .currentStatus(currentStatus)
                .employmentStartDate(employmentStartDate)
                .employmentEndDate(employmentEndDate)
                .responsibilities(responsibilities)
                .build();
    }

}