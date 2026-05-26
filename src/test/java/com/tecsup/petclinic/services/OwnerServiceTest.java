package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void testFindOwnerById_NotFound() {
        // Given
        Long ownerId = 999L;
        when(ownerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // When
        OwnerNotFoundException exception = assertThrows(
                OwnerNotFoundException.class,
                () -> ownerService.findOwnerById(ownerId)
        );

        // Then
        assertEquals(new OwnerNotFoundException().getMessage(), exception.getMessage());
        verify(ownerRepository, times(1)).findById(ownerId);
    }

    @Test
    void testDeleteOwner_NotFound() {
        // Given
        Long ownerId = 999L;
        when(ownerRepository.existsById(ownerId)).thenReturn(false);

        // When
        OwnerNotFoundException exception = assertThrows(
                OwnerNotFoundException.class,
                () -> ownerService.deleteOwner(ownerId)
        );

        // Then
        assertEquals(new OwnerNotFoundException().getMessage(), exception.getMessage());
        verify(ownerRepository, times(1)).existsById(ownerId);
    }

    @Test
    void testFindByLastName_Empty() {
        // Given
        String lastName = "ApellidoInexistente";
        when(ownerRepository.findByLastNameIgnoreCase(lastName)).thenReturn(List.of());

        // When
        List<Owner> owners = ownerService.findByLastName(lastName);

        // Then
        assertNotNull(owners);
        assertTrue(owners.isEmpty());
        verify(ownerRepository, times(1)).findByLastNameIgnoreCase(lastName);
    }

    interface OwnerRepository {
        Optional<Owner> findById(Long id);

        boolean existsById(Long id);

        void deleteById(Long id);

        List<Owner> findByLastNameIgnoreCase(String lastName);
    }

    static class OwnerService {

        private final OwnerRepository ownerRepository;

        OwnerService(OwnerRepository ownerRepository) {
            this.ownerRepository = ownerRepository;
        }

        Owner findOwnerById(Long id) {
            return ownerRepository.findById(id).orElseThrow(OwnerNotFoundException::new);
        }

        void deleteOwner(Long id) {
            if (!ownerRepository.existsById(id)) {
                throw new OwnerNotFoundException();
            }

            ownerRepository.deleteById(id);
        }

        List<Owner> findByLastName(String lastName) {
            return ownerRepository.findByLastNameIgnoreCase(lastName);
        }
    }

    static class Owner {
    }
}
