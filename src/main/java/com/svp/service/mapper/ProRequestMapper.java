package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProRequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProRequest} and its DTO {@link ProRequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyTypeMapper.class, CustomerMapper.class})
public interface ProRequestMapper extends EntityMapper<ProRequestDTO, ProRequest> {

    @Mapping(source = "companyType.id", target = "companyTypeId")
    @Mapping(source = "customer.id", target = "customerId")
    ProRequestDTO toDto(ProRequest proRequest);

    @Mapping(source = "companyTypeId", target = "companyType")
    @Mapping(source = "customerId", target = "customer")
    ProRequest toEntity(ProRequestDTO proRequestDTO);

    default ProRequest fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProRequest proRequest = new ProRequest();
        proRequest.setId(id);
        return proRequest;
    }
}
