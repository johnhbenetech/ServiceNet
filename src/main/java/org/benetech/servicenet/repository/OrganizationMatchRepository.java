package org.benetech.servicenet.repository;

import org.benetech.servicenet.domain.OrganizationMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


/**
 * Spring Data  repository for the OrganizationMatch entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationMatchRepository extends JpaRepository<OrganizationMatch, UUID> {

}
