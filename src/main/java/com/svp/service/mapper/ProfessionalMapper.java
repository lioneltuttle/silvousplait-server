package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProfessionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professional} and its DTO {@link ProfessionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionalDetailsMapper.class, CompanyLocationMapper.class})
public interface ProfessionalMapper extends EntityMapper<ProfessionalDTO, Professional> {

    @Mapping(source = "details.id", target = "detailsId")
    @Mapping(source = "location.id", target = "locationId")
    ProfessionalDTO toDto(Professional professional);

    @Mapping(source = "detailsId", target = "details")
    @Mapping(source = "locationId", target = "location")
    Professional toEntity(ProfessionalDTO professionalDTO);

    default Professional fromId(Long id) {
        if (id == null) {
            return null;
        }
        Professional professional = new Professional();
        professional.setId(id);
        return professional;
    }
}
