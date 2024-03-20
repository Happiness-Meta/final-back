package org.happinessmeta.last.resume.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "work_experience")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonIgnoreProperties("resume")
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_experience_id")
    private Long id;

    private String companyName;
    private String position;
    private String department;
    private String currentStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate employmentStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate employmentEndDate;

    private String responsibilities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume = null;

    @Builder
    public WorkExperience(String companyName, String department,
                          String currentStatus, String position,
                          LocalDate employmentStartDate, LocalDate employmentEndDate,
                          String responsibilities,
                          Resume resume) {
        this.companyName = companyName;
        this.department = department;
        this.position = position;
        this.currentStatus = currentStatus;
        this.employmentStartDate = employmentStartDate;
        this.employmentEndDate = employmentEndDate;
        this.responsibilities = responsibilities;
        this.resume = resume;
    }
}
