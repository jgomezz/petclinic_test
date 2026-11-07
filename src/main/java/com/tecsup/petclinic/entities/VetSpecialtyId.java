package com.tecsup.petclinic.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for the vet_specialties table.
 */
@Embeddable
@NoArgsConstructor
@Data
@AllArgsConstructor
public class VetSpecialtyId implements Serializable {

    @Column(name = "vet_id")
    private Integer vetId;

    @Column(name = "specialty_id")
    private Integer specialtyId;
}
