package org.benetech.servicenet.listener;

import org.benetech.servicenet.domain.enumeration.ActionType;
import org.benetech.servicenet.service.MetadataService;
import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class HibernatePostDeleteListener extends AbstractHibernateListener implements PostDeleteEventListener {

    @Autowired
    private MetadataService metadataService;

    @Override
    public boolean requiresPostCommitHanding(EntityPersister persister) {
        return false;
    }

    @Override
    public void onPostDelete(PostDeleteEvent event) {
        if (shouldTrackMetadata(event.getEntity())) {
            persistMetaData(event);
        }
    }

    private void persistMetaData(PostDeleteEvent event) {
        metadataService.saveForCurrentOrSystemUser(
            Collections.singletonList(
                prepareMetadataForAllFields(
                    event.getId().toString(), ActionType.DELETE, event.getEntity().getClass().getSimpleName())));
    }
}
