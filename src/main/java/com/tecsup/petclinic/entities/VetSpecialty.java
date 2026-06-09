package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "vet_specialties")
@Data
@IdClass(VetSpecialtyId.class)
public class VetSpecialty {

    @Id
    @Column(name = "vet_id")
    private Integer vetId;

    @Id
    @Column(name = "specialty_id")
    private Integer specialtyId;

    @ManyToOne
    @JoinColumn(name = "vet_id", insertable = false, updatable = false)
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "specialty_id", insertable = false, updatable = false)
    private Specialty specialty;

    @Column(name = "certification_date")
    private LocalDate certificationDate;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Column(name = "notes")
    private String notes;

    @Override
    public String toString() {
        return "VetSpecialty{" +
                "vetId=" + vetId +
                ", specialtyId=" + specialtyId +
                ", isPrimary=" + isPrimary +
                ", yearsExperience=" + yearsExperience +
                '}';
    }
}