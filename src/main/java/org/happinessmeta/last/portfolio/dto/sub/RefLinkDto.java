package org.happinessmeta.last.portfolio.dto.sub;

import org.happinessmeta.last.portfolio.domain.entity.RefLink;

import java.io.Serializable;

/**
 * DTO for {@link RefLink}
 */
public record RefLinkDto(String description, String address) implements Serializable {
    public RefLink toEntity() {
        return RefLink.builder()
                .description(description)
                .address(address)
                .build();
    }
}