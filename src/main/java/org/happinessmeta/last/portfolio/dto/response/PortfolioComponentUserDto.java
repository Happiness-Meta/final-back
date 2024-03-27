package org.happinessmeta.last.portfolio.dto.response;

import lombok.Builder;
import org.happinessmeta.last.user.domain.Role;

import java.util.List;

@Builder
public record PortfolioComponentUserDto(
        Long id,
        String email,
        String name,
        List<Role> roles,
        List<String> techStack
) {
}
