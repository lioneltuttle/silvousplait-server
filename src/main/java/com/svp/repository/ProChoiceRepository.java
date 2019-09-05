package com.svp.repository;

import com.svp.domain.ProChoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ProChoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProChoiceRepository extends JpaRepository<ProChoice, Long> {

}
