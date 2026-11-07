package com.tecsup.petclinic.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Specialty entity mapped to the specialties table.
 */
@Entity(name = "specialties")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String office;

    @Column(name = "h_open")
    private Integer openHour;

    @Column(name = "h_close")
    private Integer closeHour;
}
