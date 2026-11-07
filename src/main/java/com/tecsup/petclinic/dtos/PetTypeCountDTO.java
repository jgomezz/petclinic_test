package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Rows of the "pets per type" report.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PetTypeCountDTO {

    private Integer typeId;
    private String typeName;
    private long petCount;
}
