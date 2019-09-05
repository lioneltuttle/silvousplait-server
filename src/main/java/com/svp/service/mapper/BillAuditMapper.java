package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.BillAuditDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link BillAudit} and its DTO {@link BillAuditDTO}.
 */
@Mapper(componentModel = "spring", uses = {BillMapper.class})
public interface BillAuditMapper extends EntityMapper<BillAuditDTO, BillAudit> {

    @Mapping(source = "bill.id", target = "billId")
    BillAuditDTO toDto(BillAudit billAudit);

    @Mapping(source = "billId", target = "bill")
    BillAudit toEntity(BillAuditDTO billAuditDTO);

    default BillAudit fromId(Long id) {
        if (id == null) {
            return null;
        }
        BillAudit billAudit = new BillAudit();
        billAudit.setId(id);
        return billAudit;
    }
}
