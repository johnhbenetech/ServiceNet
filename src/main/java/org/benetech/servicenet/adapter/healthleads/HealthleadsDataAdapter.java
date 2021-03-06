package org.benetech.servicenet.adapter.healthleads;

import org.benetech.servicenet.adapter.MultipleDataAdapter;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsBaseData;
import org.benetech.servicenet.adapter.healthleads.persistence.PersistanceManager;
import org.benetech.servicenet.adapter.shared.model.MultipleImportData;
import org.benetech.servicenet.domain.DataImportReport;
import org.benetech.servicenet.manager.ImportManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("healthleadsDataAdapter")
public class HealthleadsDataAdapter extends MultipleDataAdapter {

    private static final int NUMBER_OF_FILES = 11;

    @Autowired
    private ImportManager importManager;

    @Override
    @Transactional
    public DataImportReport importData(MultipleImportData data) {
        verifyData(data);
        DataResolver dataResolver = new DataResolver();
        PersistanceManager persistanceManager = new PersistanceManager(importManager);
        for (int i = 0; i < data.getDocumentUploads().size(); i++) {
            String objectJson = data.getMultipleObjectsData().get(i);
            String filename = data.getDocumentUploads().get(i).getFilename();

            List<HealthleadsBaseData> baseDataList = dataResolver.getDataFromJson(objectJson, filename);

            for (HealthleadsBaseData baseData : baseDataList) {
                persistanceManager.addData(baseData);
            }
        }

        return persistanceManager.persistData(data);
    }

    @Override
    protected int getNumberOfFilesToProcess() {
        return NUMBER_OF_FILES;
    }
}
