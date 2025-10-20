package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.PetNotFoundException;
import com.tecsup.petclinic.mappers.PetMapper;
import com.tecsup.petclinic.services.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @author jgomezm
 *
 */
@RestController
@Slf4j
public class PetController {

	String name = null;

	//@Autowired
	private PetService petService;

	//@Autowired
	private PetMapper mapper;

	/**
	 *  Change
	 * @param petService
	 * @param mapper
	 */
	public PetController(PetService petService, PetMapper mapper){
		this.petService = petService;
		this.mapper = mapper ;
	}

	/**
	 * Get all pets
	 *
	 * @return
	 */
	/*
	@GetMapping(value = "/pets")
	public ResponseEntity<List<PetDTO>> findAllPets() {

		List<Pet> pets = petService.findAll();
		log.info("pets: " + pets);
		pets.forEach(item -> log.info("Pet >>  {} ", item));

		List<PetDTO> petsTO = this.mapper.toPetDTOList(pets);
		log.info("petsTO: " + petsTO);
		petsTO.forEach(item -> log.info("PetTO >>  {} ", item));

		return ResponseEntity.ok(petsTO);

	} */


	/**
	 * Create pet
	 *
	 * @param newPetDTO
	 * @return
	 */
	@PostMapping(value = "/pets")
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<PetDTO> create(@RequestBody PetDTO newPetDTO) {

		//Pet newPet = this.mapper.toPet(petTO);
		//PetDTO newPetTO = this.mapper.toPetTO(petService.create(newPet));

		PetDTO newPetDTOCreate = this.petService.create(newPetDTO);

		return  ResponseEntity.status(HttpStatus.CREATED).body(newPetDTOCreate);

	}


	/**
	 * Find pet by id
	 *
	 * @param id
	 * @return
	 * @throws PetNotFoundException
	 */
	@GetMapping(value = "/pets/{id}")
	ResponseEntity<PetDTO> findById(@PathVariable Integer id) {

		PetDTO petDTO = null;

		try {
			petDTO  = petService.findById(id);
			//petTO = this.mapper.toPetTO(pet);

		} catch (PetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(petDTO);
	}

	/**
	 * Update and create pet
	 *
	 * @param petDTO
	 * @param id
	 * @return
	 */
	@PutMapping(value = "/pets/{id}")
	ResponseEntity<PetDTO>  update(@RequestBody PetDTO petDTO, @PathVariable Integer id) {

		PetDTO updatePetTO = null;

		try {

			PetDTO updatePet = petService.findById(id);

			updatePet.setName(petDTO.getName());
			updatePet.setOwnerId(petDTO.getOwnerId());
			updatePet.setTypeId(petDTO.getTypeId());

			petService.update(updatePet);

			//updatePetTO = this.mapper.toPetTO(updatePet);

		} catch (PetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(updatePetTO);
	}

	/**
	 * Delete pet by id
	 *
	 * @param id
	 */
	@DeleteMapping(value = "/pets/{id}")
	ResponseEntity<String> delete(@PathVariable Integer id) {

		try {
			petService.delete(id);
			return ResponseEntity.ok(" Delete ID :" + id);
		} catch (PetNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
