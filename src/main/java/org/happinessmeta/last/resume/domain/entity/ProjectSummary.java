package org.happinessmeta.last.resume.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project_summary")
@JsonIgnoreProperties("resume")
@Entity
public class ProjectSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_summary_id")
    private Long id;

    private String projectName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate projectEndDate;

    @Lob
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume = null;

    @Builder
    public ProjectSummary(String projectName, String description,
                          LocalDate projectEndDate, LocalDate projectStartDate, Resume resume){
        this.projectName = projectName;
        this.description = description;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.resume = resume;
    }

}