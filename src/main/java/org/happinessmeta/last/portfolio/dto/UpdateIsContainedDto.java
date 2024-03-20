package org.happinessmeta.last.portfolio.dto;

import org.happinessmeta.last.portfolio.dto.sub.UpdateSelectedPortfolioIsContainedDto;

import java.util.List;

public record UpdateIsContainedDto(
        List<UpdateSelectedPortfolioIsContainedDto> selectedComponent
) {
}
