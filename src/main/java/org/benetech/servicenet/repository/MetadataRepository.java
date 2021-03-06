package org.benetech.servicenet.repository;

import org.benetech.servicenet.domain.Metadata;
import org.benetech.servicenet.domain.enumeration.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data  repository for the Metadata entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetadataRepository extends JpaRepository<Metadata, UUID> {

    @Query("select metadata from Metadata metadata where metadata.user.login = ?#{principal.username}")
    List<Metadata> findByUserIsCurrentUser();

    Optional<Metadata> findFirstByResourceIdAndFieldNameAndReplacementValueOrderByLastActionDateAsc(
                        String resourceId,
                        String fieldName,
                        String replacementValue);

    Optional<Metadata> findFirstByResourceIdAndFieldNameAndLastActionTypeOrderByLastActionDateAsc(
                        String resourceId,
                        String fieldName,
                        ActionType lastActionType);

}
