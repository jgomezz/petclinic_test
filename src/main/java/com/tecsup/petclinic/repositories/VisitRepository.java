package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author jgomezm
 *
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

	List<Visit> findByPetId(int petId);

	List<Visit> findByVetId(int vetId);

	@Override
	List<Visit> findAll();
}
