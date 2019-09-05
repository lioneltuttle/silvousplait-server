package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProfessionalDetailsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfessionalDetails} and its DTO {@link ProfessionalDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfessionalDetailsMapper extends EntityMapper<ProfessionalDetailsDTO, ProfessionalDetails> {



    default ProfessionalDetails fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfessionalDetails professionalDetails = new ProfessionalDetails();
        professionalDetails.setId(id);
        return professionalDetails;
    }
}
