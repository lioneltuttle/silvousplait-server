package com.svp.service.mapper;

import com.svp.domain.*;
import com.svp.service.dto.ProChoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProChoice} and its DTO {@link ProChoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProfessionalMapper.class, ProRequestMapper.class, RatingMapper.class, CustomerMapper.class})
public interface ProChoiceMapper extends EntityMapper<ProChoiceDTO, ProChoice> {

    @Mapping(source = "choice.id", target = "choiceId")
    @Mapping(source = "request.id", target = "requestId")
    @Mapping(source = "rating.id", target = "ratingId")
    @Mapping(source = "customer.id", target = "customerId")
    ProChoiceDTO toDto(ProChoice proChoice);

    @Mapping(source = "choiceId", target = "choice")
    @Mapping(source = "requestId", target = "request")
    @Mapping(source = "ratingId", target = "rating")
    @Mapping(source = "customerId", target = "customer")
    ProChoice toEntity(ProChoiceDTO proChoiceDTO);

    default ProChoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProChoice proChoice = new ProChoice();
        proChoice.setId(id);
        return proChoice;
    }
}
