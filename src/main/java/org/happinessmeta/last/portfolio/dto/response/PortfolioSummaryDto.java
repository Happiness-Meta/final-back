package org.happinessmeta.last.portfolio.dto.response;

import org.happinessmeta.last.portfolio.domain.entity.PortfolioComponent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link PortfolioComponent}
 */
public record PortfolioSummaryDto(Long id,
                                  String projectName,
                                  String description,
                                  LocalDate projectStartDate,
                                  LocalDate projectEndDate
) implements Serializable {
}