package org.benetech.servicenet.repository;

import org.benetech.servicenet.domain.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data  repository for the Metadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetadataRepository extends JpaRepository<Metadata, UUID> {

    @Query("select metadata from Metadata metadata where metadata.user.login = ?#{principal.username}")
    List<Metadata> findByUserIsCurrentUser();

}