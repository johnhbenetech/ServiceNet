package org.benetech.servicenet.adapter.icarol;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.http.Header;
import org.benetech.servicenet.adapter.SingleDataAdapter;
import org.benetech.servicenet.adapter.icarol.model.ICarolAgency;
import org.benetech.servicenet.adapter.icarol.model.ICarolComplexResponseElement;
import org.benetech.servicenet.adapter.icarol.model.ICarolDataToPersist;
import org.benetech.servicenet.adapter.icarol.model.ICarolProgram;
import org.benetech.servicenet.adapter.icarol.model.ICarolServiceSite;
import org.benetech.servicenet.adapter.icarol.model.ICarolSimpleResponseElement;
import org.benetech.servicenet.adapter.icarol.model.ICarolSite;
import org.benetech.servicenet.adapter.shared.model.SingleImportData;
import org.benetech.servicenet.domain.DataImportReport;
import org.benetech.servicenet.manager.ImportManager;
import org.benetech.servicenet.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.lang.reflect.Type;
import java.util.Collection;

public abstract class AbstractICarolDataAdapter extends SingleDataAdapter {

    private static final String AGENCY = "Agency";
    private static final String PROGRAM = "Program";
    private static final String SITE = "Site";
    private static final String SERVICE_SITE = "ServiceSite";
    private static final String TYPE = "type";

    @Autowired
    private EntityManager em;

    @Autowired
    private ImportManager importManager;

    @Override
    public abstract DataImportReport importData(SingleImportData importData);

    protected DataImportReport importData(SingleImportData importData, String uri) {
        ICarolDataToPersist dataToPersist = gatherMoreDetails(importData, uri);
        RelationManager manager = new RelationManager(importManager);
        return manager.persist(dataToPersist, importData);
    }

    protected abstract String getApiKey();

    private ICarolDataToPersist gatherMoreDetails(SingleImportData importData, String uri) {
        Header[] headers = HttpUtils.getStandardAuthHeaders(getApiKey());
        return getDataToPersist(importData, headers, uri);
    }

    private ICarolDataToPersist getDataToPersist(SingleImportData importData, Header[] headers, String uri) {
        if (importData.isFileUpload()) {
            return collectDataDetailsFromTheFile(importData.getSingleObjectData());
        }
        Type collectionType = new TypeToken<Collection<ICarolSimpleResponseElement>>() {
        }.getType();
        Collection<ICarolSimpleResponseElement> responseElements = new Gson()
            .fromJson(importData.getSingleObjectData(), collectionType);
        ICarolComplexResponseElement data = new ICarolComplexResponseElement(responseElements);
        return collectDataDetailsFromTheApi(data, headers, uri);
    }

    private ICarolDataToPersist collectDataDetailsFromTheFile(String file) {
        ICarolDataToPersist dataToPersist = new ICarolDataToPersist();
        JsonArray elements = new Gson().fromJson(file, JsonArray.class);

        for (JsonElement element : elements) {
            JsonObject jsonObject = element.getAsJsonObject();
            String type = jsonObject.get(TYPE).getAsString();
            updateData(dataToPersist, jsonObject, type);
        }

        return dataToPersist;
    }

    private void updateData(ICarolDataToPersist dataToPersist, JsonObject jsonObject, String type) {
        if (type.equals(AGENCY)) {
            dataToPersist.addAgency(new Gson().fromJson(jsonObject, ICarolAgency.class));
        }
        if (type.equals(PROGRAM)) {
            dataToPersist.addProgram(new Gson().fromJson(jsonObject, ICarolProgram.class));
        }
        if (type.equals(SERVICE_SITE)) {
            dataToPersist.addServiceSite(new Gson().fromJson(jsonObject, ICarolServiceSite.class));
        }
        if (type.equals(SITE)) {
            dataToPersist.addSite(new Gson().fromJson(jsonObject, ICarolSite.class));
        }
    }

    private ICarolDataToPersist collectDataDetailsFromTheApi(ICarolComplexResponseElement data,
                                                             Header[] headers,
                                                             String uri) {
        ICarolDataToPersist dataToPersist = new ICarolDataToPersist();

        dataToPersist.setPrograms(
            ICarolDataCollector.collectData(data.getProgramBatches(), headers, ICarolProgram.class, uri));
        dataToPersist.setSites(
            ICarolDataCollector.collectData(data.getSiteBatches(), headers, ICarolSite.class, uri));
        dataToPersist.setAgencies(
            ICarolDataCollector.collectData(data.getAgencyBatches(), headers, ICarolAgency.class, uri));
        dataToPersist.setServiceSites(
            ICarolDataCollector.collectData(data.getServiceSiteBatches(), headers, ICarolServiceSite.class, uri));

        return dataToPersist;
    }
}
