package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProfessionalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Professional} and its DTO {@link ProfessionalDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface ProfessionalMapper extends EntityMapper<ProfessionalDTO, Professional> {

    @Mapping(source = "company.id", target = "companyId")
    ProfessionalDTO toDto(Professional professional);

    @Mapping(source = "companyId", target = "company")
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
