package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProfessionalProfileImageDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProfessionalProfileImage} and its DTO {@link ProfessionalProfileImageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProfessionalProfileImageMapper extends EntityMapper<ProfessionalProfileImageDTO, ProfessionalProfileImage> {



    default ProfessionalProfileImage fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfessionalProfileImage professionalProfileImage = new ProfessionalProfileImage();
        professionalProfileImage.setId(id);
        return professionalProfileImage;
    }
}
