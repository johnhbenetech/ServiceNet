package org.benetech.servicenet.service;

import org.benetech.servicenet.service.dto.PhysicalAddressDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing PhysicalAddress.
 */
public interface PhysicalAddressService {

    /**
     * Save a physicalAddress.
     *
     * @param physicalAddressDTO the entity to save
     * @return the persisted entity
     */
    PhysicalAddressDTO save(PhysicalAddressDTO physicalAddressDTO);

    /**
     * Get all the physicalAddresses.
     *
     * @return the list of entities
     */
    List<PhysicalAddressDTO> findAll();


    /**
     * Get the "id" physicalAddress.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PhysicalAddressDTO> findOne(UUID id);

    /**
     * Delete the "id" physicalAddress.
     *
     * @param id the id of the entity
     */
    void delete(UUID id);
}
