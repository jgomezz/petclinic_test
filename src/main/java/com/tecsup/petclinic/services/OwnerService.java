package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

/**
 *
 * @author Calderon
 *
 */
public interface OwnerService {

	Owner findById(Integer id) throws OwnerNotFoundException;

	List<Owner> findByLastName(String lastName);

	List<Owner> findByCity(String city);

	Owner save(Owner owner);

	void deleteById(Integer id) throws OwnerNotFoundException;
}
