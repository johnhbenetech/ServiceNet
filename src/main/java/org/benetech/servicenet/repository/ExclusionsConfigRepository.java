package org.benetech.servicenet.repository;

import org.benetech.servicenet.domain.ExclusionsConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


/**
 * Spring Data  repository for the ExclusionsConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExclusionsConfigRepository extends JpaRepository<ExclusionsConfig, UUID> {

    @Query("SELECT config FROM ExclusionsConfig config where config.account.name = :accountName")
    Optional<ExclusionsConfig> findOneBySystemAccountName(@Param("accountName") String accountName);

    @Query("SELECT config FROM ExclusionsConfig config where config.account.name in :accountNames")
    List<ExclusionsConfig> findAllBySystemAccountNameIn(@Param("accountNames") Set<String> accountNames);
}
