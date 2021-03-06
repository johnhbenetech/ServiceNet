package org.benetech.servicenet.service;

import org.benetech.servicenet.service.dto.ActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Interface for managing Activity.
 */
public interface ActivityService {

    Page<ActivityDTO> getAllOrganizationActivities(Pageable pageable, UUID systemAccountId);

    Optional<ActivityDTO> getOneByOrganizationId(UUID organizationId);
}
