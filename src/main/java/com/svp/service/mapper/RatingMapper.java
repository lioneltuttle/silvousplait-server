package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.RatingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rating} and its DTO {@link RatingDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionalMapper.class})
public interface RatingMapper extends EntityMapper<RatingDTO, Rating> {

    @Mapping(source = "company.id", target = "companyId")
    RatingDTO toDto(Rating rating);

    @Mapping(source = "companyId", target = "company")
    Rating toEntity(RatingDTO ratingDTO);

    default Rating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Rating rating = new Rating();
        rating.setId(id);
        return rating;
    }
}
