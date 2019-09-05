package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.BillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bill} and its DTO {@link BillDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompanyMapper.class})
public interface BillMapper extends EntityMapper<BillDTO, Bill> {

    @Mapping(source = "company.id", target = "companyId")
    BillDTO toDto(Bill bill);

    @Mapping(source = "companyId", target = "company")
    Bill toEntity(BillDTO billDTO);

    default Bill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
