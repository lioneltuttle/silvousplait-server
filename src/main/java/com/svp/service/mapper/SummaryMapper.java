package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.SummaryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Summary} and its DTO {@link SummaryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SummaryMapper extends EntityMapper<SummaryDTO, Summary> {



    default Summary fromId(Long id) {
        if (id == null) {
            return null;
        }
        Summary summary = new Summary();
        summary.setId(id);
        return summary;
    }
}
