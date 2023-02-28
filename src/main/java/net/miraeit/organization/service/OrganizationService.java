package net.miraeit.organization.service;

import lombok.RequiredArgsConstructor;
import net.miraeit.organization.dao.OrganizationDAO;
import net.miraeit.organization.model.PatchOrganizationRequest;
import net.miraeit.organization.model.PatchOrganizationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationService {

	private final OrganizationDAO organizationDAO;

	/**
	 * 이행부서 매핑정보 삭제, 조직유형(이행부서 여부) 수정
	 *
	 * @param request
	 * @return
	 */
	public PatchOrganizationResponse patchOrganizationOrgType(PatchOrganizationRequest request) {

		// 이행조직 불가능
		if (request.getOrgType() == 1) {
			organizationDAO.deleteImplementMapping(request);        // 이행부서 매핑정보 삭제
			organizationDAO.deleteImplementItem(request);           // 이행 작업 삭제
		}

		organizationDAO.updateOrganizationOrgType(request);         // 조직    - 유형 수정
		organizationDAO.updateOrganizationCurrentOrgType(request);  // 현재조직 - 유형 수정

		return organizationDAO.findOrganizationCurrent(request);    // 현재조직 - 조회 및 반환
	}

	/**
	 * 주관부서 매핑정보 삭제, 주관부서 여부 수정
	 *
	 * @param request
	 * @return
	 */
	public PatchOrganizationResponse patchOrganizationMngYn(PatchOrganizationRequest request) {

		if (request.getMngYn().equals("N")) organizationDAO.deleteManagementMapping(request); // 주관부서 매핑정보 삭제

		organizationDAO.updateOrganizationMngYn(request);           // 조직    - 주관부서 여부 수정
		organizationDAO.updateOrganizationCurrentMngYn(request);    // 현재조직 - 주관부서 여부 수정

		return organizationDAO.findOrganizationCurrent(request);    // 현재조직 - 조회 및 반환
	}
}
