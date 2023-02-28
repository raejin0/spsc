package net.miraeit.organization.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.organization.model.PatchOrganizationRequest;
import net.miraeit.organization.model.PatchOrganizationResponse;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizationDAO extends EgovAbstractDAO {

/*	public void updateOrganization(PatchOrganizationRequest patchOrganizationRequest) {
		update("spsc.organization.update.organization", patchOrganizationRequest);
	}

	public void updateOrganizationCurrent(PatchOrganizationRequest patchOrganizationRequest) {
		update("spsc.organization.update.organization.current", patchOrganizationRequest);
	}*/

	// TB_ORG_CURRENT 조회
	public PatchOrganizationResponse findOrganizationCurrent(PatchOrganizationRequest patchOrganizationRequest) {
		return (PatchOrganizationResponse) select("spsc.organization.select.organization.current", patchOrganizationRequest);
	}

	/* ----------- 이행부서 수정 ------------------ */
	// 이행부서 매핑정보 삭제
	public void deleteImplementMapping(PatchOrganizationRequest request) {
		delete("spsc.organization.delete.implement.mapping", request);
	}

	// 이행 작업 삭제
	public void deleteImplementItem(PatchOrganizationRequest request) {
		delete("spsc.organization.delete.implement.item", request);
	}

	// TB_ORG 조직유형 수정
	public void updateOrganizationOrgType(PatchOrganizationRequest patchOrganizationRequest) {
		update("spsc.organization.update.orgType", patchOrganizationRequest);
	}

	// TB_ORG_CURRENT 조직유형 수정
	public void updateOrganizationCurrentOrgType(PatchOrganizationRequest patchOrganizationRequest) {
		update("spsc.organization.update.orgType.current", patchOrganizationRequest);
	}
	/* /////////// 이행부서 수정 ////////////////// */


	/* ----------- 주관부서 수정 ------------------ */
	// 주관부서 매핑정보 삭제
	public void deleteManagementMapping(PatchOrganizationRequest request) {
		delete("spsc.organization.delete.management.mapping", request);
	}

	// TB_ORG 주관부서 여부 수정
	public void updateOrganizationMngYn(PatchOrganizationRequest patchOrganizationRequest) {
		update("spsc.organization.update.mngYn", patchOrganizationRequest);
	}

	// TB_ORG_CURRENT 주관부서 여부 수정
	public void updateOrganizationCurrentMngYn(PatchOrganizationRequest patchOrganizationRequest) {
		update("spsc.organization.update.mngYn.current", patchOrganizationRequest);
	}
	/* /////////// 주관부서 수정 ////////////////// */
}
