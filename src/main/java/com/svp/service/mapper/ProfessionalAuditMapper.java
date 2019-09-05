package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProfessionalAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfessionalAudit} and its DTO {@link ProfessionalAuditDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionalMapper.class})
public interface ProfessionalAuditMapper extends EntityMapper<ProfessionalAuditDTO, ProfessionalAudit> {

    @Mapping(source = "professional.id", target = "professionalId")
    ProfessionalAuditDTO toDto(ProfessionalAudit professionalAudit);

    @Mapping(source = "professionalId", target = "professional")
    ProfessionalAudit toEntity(ProfessionalAuditDTO professionalAuditDTO);

    default ProfessionalAudit fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfessionalAudit professionalAudit = new ProfessionalAudit();
        professionalAudit.setId(id);
        return professionalAudit;
    }
}
