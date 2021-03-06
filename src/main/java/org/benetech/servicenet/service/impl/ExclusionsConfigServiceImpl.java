package org.benetech.servicenet.service.impl;

import org.benetech.servicenet.domain.ExclusionsConfig;
import org.benetech.servicenet.repository.ExclusionsConfigRepository;
import org.benetech.servicenet.service.ExclusionsConfigService;
import org.benetech.servicenet.service.dto.ExclusionsConfigDTO;
import org.benetech.servicenet.service.mapper.ExclusionsConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ExclusionsConfig.
 */
@Service
@Transactional
public class ExclusionsConfigServiceImpl implements ExclusionsConfigService {

    private final Logger log = LoggerFactory.getLogger(ExclusionsConfigServiceImpl.class);

    private final ExclusionsConfigRepository exclusionsConfigRepository;

    private final ExclusionsConfigMapper exclusionsConfigMapper;

    public ExclusionsConfigServiceImpl(ExclusionsConfigRepository exclusionsConfigRepository,
                                       ExclusionsConfigMapper exclusionsConfigMapper) {
        this.exclusionsConfigRepository = exclusionsConfigRepository;
        this.exclusionsConfigMapper = exclusionsConfigMapper;
    }

    @Override
    public ExclusionsConfigDTO save(ExclusionsConfigDTO exclusionsConfigDTO) {
        log.debug("Request to save ExclusionsConfig : {}", exclusionsConfigDTO);

        ExclusionsConfig exclusionsConfig = exclusionsConfigMapper.toEntity(exclusionsConfigDTO);
        exclusionsConfig = exclusionsConfigRepository.save(exclusionsConfig);
        return exclusionsConfigMapper.toDto(exclusionsConfig);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExclusionsConfigDTO> findAll() {
        log.debug("Request to get all ExclusionsConfigs");
        return exclusionsConfigRepository.findAll().stream()
            .map(exclusionsConfigMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<ExclusionsConfigDTO> findAllBySystemAccountNameIn(Set<String> accountNames) {
        return exclusionsConfigRepository.findAllBySystemAccountNameIn(accountNames)
            .stream()
            .map(exclusionsConfigMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExclusionsConfigDTO> findOne(UUID id) {
        log.debug("Request to get ExclusionsConfig : {}", id);
        return exclusionsConfigRepository.findById(id)
            .map(exclusionsConfigMapper::toDto);
    }

    @Override
    public Optional<ExclusionsConfigDTO> findOneBySystemAccountName(String accountName) {
        return exclusionsConfigRepository.findOneBySystemAccountName(accountName)
            .map(exclusionsConfigMapper::toDto);
    }

    @Override
    public void delete(UUID id) {
        log.debug("Request to delete ExclusionsConfig : {}", id);
        exclusionsConfigRepository.deleteById(id);
    }
}
