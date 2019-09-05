package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.CompanyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyTypeMapper.class, SubscriptionTypeMapper.class})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {

    @Mapping(source = "companyType.id", target = "companyTypeId")
    @Mapping(source = "subscriptionType.id", target = "subscriptionTypeId")
    CompanyDTO toDto(Company company);

    @Mapping(target = "locations", ignore = true)
    @Mapping(source = "companyTypeId", target = "companyType")
    @Mapping(source = "subscriptionTypeId", target = "subscriptionType")
    Company toEntity(CompanyDTO companyDTO);

    default Company fromId(Long id) {
        if (id == null) {
            return null;
        }
        Company company = new Company();
        company.setId(id);
        return company;
    }
}
