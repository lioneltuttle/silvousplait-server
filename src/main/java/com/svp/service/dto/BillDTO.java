package com.svp.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;
import com.svp.domain.enumeration.BillStatus;
import lombok.*;
@ToString
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BillDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Double amountDue;

    private BillStatus status;

    private Long companyId;

}
