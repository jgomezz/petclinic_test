package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.repositories.OwnerRepository;

/**
 *
 * @author Calderon
 *
 */
@Service
public class OwnerServiceImpl implements OwnerService {

	OwnerRepository ownerRepository;

	public OwnerServiceImpl(OwnerRepository ownerRepository) {
		this.ownerRepository = ownerRepository;
	}

	@Override
	public Owner findById(Integer id) throws OwnerNotFoundException {

		Optional<Owner> owner = ownerRepository.findById(id);

		if (!owner.isPresent())
			throw new OwnerNotFoundException("Record not found...!");

		return owner.get();
	}

	@Override
	public List<Owner> findByLastName(String lastName) {

		List<Owner> owners = ownerRepository.findByLastName(lastName);

		return owners;
	}

	@Override
	public List<Owner> findByCity(String city) {

		List<Owner> owners = ownerRepository.findByCity(city);

		return owners;
	}

	@Override
	public Owner save(Owner owner) {
		return ownerRepository.save(owner);
	}

	@Override
	public void deleteById(Integer id) throws OwnerNotFoundException {

		findById(id);

		ownerRepository.deleteById(id);
	}
}
