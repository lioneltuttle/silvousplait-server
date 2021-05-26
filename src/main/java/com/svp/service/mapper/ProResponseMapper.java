package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProResponseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProResponse} and its DTO {@link ProResponseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProRequestMapper.class})
public interface ProResponseMapper extends EntityMapper<ProResponseDTO, ProResponse> {

    @Mapping(source = "request.id", target = "requestId")
    ProResponseDTO toDto(ProResponse proResponse);

    @Mapping(source = "requestId", target = "request")
    ProResponse toEntity(ProResponseDTO proResponseDTO);

    default ProResponse fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProResponse proResponse = new ProResponse();
        proResponse.setId(id);
        return proResponse;
    }
}
