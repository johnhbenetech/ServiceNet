package org.benetech.servicenet.adapter.healthleads;

import org.benetech.servicenet.adapter.healthleads.model.HealthleadsBaseData;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsEligibility;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsLanguage;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsLocation;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsOrganization;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsPhone;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsPhysicalAddress;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsRequiredDocument;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsService;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsServiceAtLocation;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsServiceTaxonomy;
import org.benetech.servicenet.adapter.healthleads.model.HealthleadsTaxonomy;

enum DataType {
    ELIGIBILITY(HealthleadsEligibility.class),
    LANGUAGES(HealthleadsLanguage.class),
    LOCATIONS(HealthleadsLocation.class),
    ORGANIZATIONS(HealthleadsOrganization.class),
    PHONES(HealthleadsPhone.class),
    PHYSICAL_ADDRESSES(HealthleadsPhysicalAddress.class),
    REQUIRED_DOCUMENTS(HealthleadsRequiredDocument.class),
    SERVICES(HealthleadsService.class),
    SERVICES_AT_LOCATION(HealthleadsServiceAtLocation.class),
    SERVICES_TAXONOMY(HealthleadsServiceTaxonomy.class),
    TAXONOMY(HealthleadsTaxonomy.class);

    private Class<? extends HealthleadsBaseData> clazz;

    DataType(Class<? extends HealthleadsBaseData> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends HealthleadsBaseData> getClazz() {
        return clazz;
    }
}
