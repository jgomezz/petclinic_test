package com.tecsup.petclinic.entities;

import java.io.Serializable;
import java.util.Objects;

public class VetSpecialtyId implements Serializable {

    private Integer vetId;
    private Integer specialtyId;

    public VetSpecialtyId() {}

    public VetSpecialtyId(Integer vetId, Integer specialtyId) {
        this.vetId = vetId;
        this.specialtyId = specialtyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VetSpecialtyId)) return false;
        VetSpecialtyId that = (VetSpecialtyId) o;
        return Objects.equals(vetId, that.vetId) &&
                Objects.equals(specialtyId, that.specialtyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vetId, specialtyId);
    }
}