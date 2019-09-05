package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.HitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Hit} and its DTO {@link HitDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionalMapper.class, CustomerMapper.class})
public interface HitMapper extends EntityMapper<HitDTO, Hit> {

    @Mapping(source = "professional.id", target = "professionalId")
    @Mapping(source = "customer.id", target = "customerId")
    HitDTO toDto(Hit hit);

    @Mapping(source = "professionalId", target = "professional")
    @Mapping(source = "customerId", target = "customer")
    Hit toEntity(HitDTO hitDTO);

    default Hit fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hit hit = new Hit();
        hit.setId(id);
        return hit;
    }
}
