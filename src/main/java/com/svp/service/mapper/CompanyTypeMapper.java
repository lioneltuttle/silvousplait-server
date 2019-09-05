package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.CompanyTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyType} and its DTO {@link CompanyTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyTypeMapper extends EntityMapper<CompanyTypeDTO, CompanyType> {



    default CompanyType fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyType companyType = new CompanyType();
        companyType.setId(id);
        return companyType;
    }
}
