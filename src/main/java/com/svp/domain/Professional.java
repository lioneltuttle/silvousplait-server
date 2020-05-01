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
 * A Professional.
 */
@Entity
@Table(name = "professional")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EqualsAndHashCode
@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Professional implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "up")
    private Boolean up;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(unique = true)
    private ProfessionalDetails details;

    @ManyToOne
    @JsonIgnoreProperties("professionals")
    private CompanyLocation location;
}
