package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.CustomerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {


    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "removeRequests", ignore = true)
    @Mapping(target = "choices", ignore = true)
    @Mapping(target = "removeChoices", ignore = true)
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
