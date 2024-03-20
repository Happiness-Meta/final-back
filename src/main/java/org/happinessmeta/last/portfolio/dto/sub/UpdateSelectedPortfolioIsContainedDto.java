package org.happinessmeta.last.portfolio.dto.sub;

import lombok.Builder;

@Builder
public record UpdateSelectedPortfolioIsContainedDto(
        Long id,
        boolean isContained
) {
}
