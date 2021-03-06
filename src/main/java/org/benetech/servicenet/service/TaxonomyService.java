package org.benetech.servicenet.service;

import org.benetech.servicenet.domain.Taxonomy;
import org.benetech.servicenet.service.dto.TaxonomyDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing Taxonomy.
 */
public interface TaxonomyService {

    /**
     * Save a taxonomy.
     *
     * @param taxonomyDTO the entity to save
     * @return the persisted entity
     */
    TaxonomyDTO save(TaxonomyDTO taxonomyDTO);

    /**
     * Get all the taxonomies.
     *
     * @return the list of entities
     */
    List<TaxonomyDTO> findAll();


    /**
     * Get the "id" taxonomy.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TaxonomyDTO> findOne(UUID id);

    Optional<Taxonomy> findForExternalDb(String externalDbId, String providerName);

    /**
     * Delete the "id" taxonomy.
     *
     * @param id the id of the entity
     */
    void delete(UUID id);
}
