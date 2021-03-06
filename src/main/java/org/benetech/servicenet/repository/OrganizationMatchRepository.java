package org.benetech.servicenet.repository;

import org.benetech.servicenet.domain.OrganizationMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data  repository for the OrganizationMatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationMatchRepository extends JpaRepository<OrganizationMatch, UUID> {

    @Query("SELECT orgMatch FROM OrganizationMatch orgMatch WHERE orgMatch.organizationRecord.id = :organizationRecordId")
    List<OrganizationMatch> findAllByOrganizationRecordId(@Param("organizationRecordId") UUID uuid);
}
