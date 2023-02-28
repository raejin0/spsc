package net.miraeit.cmm.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.cmm.condition.OrganizationConditions;
import net.miraeit.cmm.model.CMMCodesGetResponse;
import net.miraeit.cmm.model.CMMInside;
import net.miraeit.cmm.model.OperationPeriod;
import net.miraeit.cmm.model.SimpleCount;
import net.miraeit.cmm.model.equipment.TargetEquipment;
import net.miraeit.cmm.model.equipment.TargetEquipmentListGetRequest;
import net.miraeit.cmm.model.equipment.TargetEquipmentOnLawGetRequest;
import net.miraeit.cmm.model.equipment.TargetEquipmentPostRequest;
import net.miraeit.cmm.model.law.LawDetail;
import net.miraeit.cmm.model.law.LawDetailDeleteRequest;
import net.miraeit.cmm.model.law.LawDetailPatchRequest;
import net.miraeit.cmm.model.organization.*;
import net.miraeit.cmm.model.law.LawDetailInsertDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CMMDAO extends EgovAbstractDAO {

    public String checkCode(CMMInside request) {
        return (String) select("spsc.cmm.check.code",request);
    }

    public String checkCodeId(String request) {
        return (String) select("spsc.cmm.check.codeId",request);
    }

    public List<SimpleCount> checkCodeIds(List<String> codeIds) {
        return (List<SimpleCount>) list("spsc.cmm.check.codeIds", codeIds);
    }

    @SuppressWarnings("unchecked")
    public List<CMMCodesGetResponse> getCodes(String request) {
        return (List<CMMCodesGetResponse>) list("spsc.cmm.get.codes",request);
    }

    public List<CMMCodesGetResponse> getCodes(List<String> codeIds) {
        return (List<CMMCodesGetResponse>) list("spsc.cmm.get.codesList", codeIds);
    }

    public OrganizationGetResponse getOrganizationKomipo() {
        return (OrganizationGetResponse) select("spsc.cmm.get.organization.komipo");
    }

    public void patchOrganizationManagement(ManagementPatchRequest request) {
        update("spsc.cmm.patch.management", request);
        update("spsc.cmm.patch.management.current", request);
    }

    public void deleteOrganizationManagementMap(ManagementPatchRequest request) {
        delete("spsc.cmm.delete.management.map",request);
    }

    public void patchOrganizationType(OrganizationTypePatchRequest request) {
        update("spsc.cmm.patch.organization.type", request);
        update("spsc.cmm.patch.organization.type.current", request);
    }

    public void deleteOrganizationMap(OrganizationTypePatchRequest request) {
        delete("spsc.cmm.delete.organization.map",request);
    }

    @SuppressWarnings("unchecked")
    public List<OrganizationGetResponse> getOrganizationList(OrganizationGetRequest request) {
        return (List<OrganizationGetResponse>) list("spsc.cmm.get.organization.list",request);
    }

    @SuppressWarnings("unchecked")
    public List<ManagementGetResponse> getManagement(OrganizationGetRequest request) {
        return (List<ManagementGetResponse>) list("spsc.cmm.get.management",request);
    }

    public Integer saveTargetEquipment(TargetEquipmentPostRequest targetEquipment) {
        return (Integer)insert("spsc.cmm.insert.targetEquipment", targetEquipment);
    }

    public int deleteTargetEquipment(TargetEquipmentPostRequest targetEquipment) {
        return delete("spsc.cmm.delete.targetEquipment", targetEquipment);
    }

    @SuppressWarnings("unchecked")
    public List<TargetEquipment> selectTargetEquipmentList(TargetEquipmentListGetRequest targetEquipment) {
        return (List<TargetEquipment>)list("spsc.cmm.select.targetEquipment", targetEquipment);
    }

    public List<TargetEquipment> selectTargetEquipmentsOnLaws(TargetEquipmentOnLawGetRequest request) {
        return (List<TargetEquipment>)list("spsc.cmm.select.target.equipments", request);
    }

    @SuppressWarnings("unchecked")
    public List<TargetEquipment> selectEquipmentAll() {
        return (List<TargetEquipment>)list("spsc.cmm.select.equipmentAll");
    }

    @SuppressWarnings("unchecked")
    public List<String> selectUsableYears() {
        return (List<String>)list("spsc.cmm.select.usableYears");
    }

    @SuppressWarnings("unchecked")
    public List<OperationPeriod> selectUsablePeriods() {
        return (List<OperationPeriod>)list("spsc.cmm.select.usablePeriods");
    }

	public void saveRawDetail(LawDetailInsertDTO request) {
		insert("spsc.cmm.insert.law.detail", request);
	}

	/*public List<LawMapDTO> findLawImplId(LawDetailPatchRequest request) {
		return (List<LawMapDTO>) list("spsc.cmm.select.law.map", request);
	}

	public void updateUsedRawDetail(LawDetailPatchRequest request) {
		update("spsc.cmm.update.used.law.detail", request);
	}*/

	public void updateRawDetail(LawDetailPatchRequest request) {
		update("spsc.cmm.update.law.detail", request);
	}

	public void deleteLawDetail(LawDetailDeleteRequest request) {
		delete("spsc.cmm.delete.law.detail", request);
	}

	public List<LawDetail> findLawList() {
		return (List<LawDetail>) list("spsc.cmm.select.law.detail.list");
	}

	public String findOrgCdByMstLawIdAndFullOrgNm(Integer mstLawId, String orgNm) {
		OrganizationConditions conditions = OrganizationConditions.builder()
				.mstLawId(mstLawId)
				.fullOrgNm(orgNm)
				.build();

		return (String) select("spsc.cmm.selet.org.cd.by.full.org.nm", conditions);
	}
}
