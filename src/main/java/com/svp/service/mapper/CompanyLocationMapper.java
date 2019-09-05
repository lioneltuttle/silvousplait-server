package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.CompanyLocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyLocation} and its DTO {@link CompanyLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface CompanyLocationMapper extends EntityMapper<CompanyLocationDTO, CompanyLocation> {

    @Mapping(source = "professional.id", target = "professionalId")
    CompanyLocationDTO toDto(CompanyLocation companyLocation);

    @Mapping(target = "professionals", ignore = true)
    @Mapping(source = "professionalId", target = "professional")
    CompanyLocation toEntity(CompanyLocationDTO companyLocationDTO);

    default CompanyLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyLocation companyLocation = new CompanyLocation();
        companyLocation.setId(id);
        return companyLocation;
    }
}
