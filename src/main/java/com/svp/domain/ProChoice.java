package com.svp.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ProChoice.
 */
@Entity
@Table(name = "pro_choice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProChoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "device_registration_id")
    private String deviceRegistrationId;

    @Column(name = "jhi_date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(unique = true)
    private Professional choice;

    @OneToOne
    @JoinColumn(unique = true)
    private ProRequest request;

    @OneToOne
    @JoinColumn(unique = true)
    private Rating rating;

    @ManyToOne
    @JsonIgnoreProperties("choices")
    private Customer customer;
}
