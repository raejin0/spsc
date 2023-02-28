package net.miraeit.organization.controller;

import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.organization.dao.OrganizationDAO;
import net.miraeit.organization.model.PatchOrganizationRequest;
import net.miraeit.organization.model.PatchOrganizationResponse;
import net.miraeit.organization.service.OrganizationService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organization")
@RequiredArgsConstructor
public class OrganizationController {

	private final OrganizationService organizationService;

	/**
	 * 이행부서(조직유형) 수정
	 *
	 * @param request
	 * @return
	 */
	@PatchMapping("orgType")
	public SuccessResponse<PatchOrganizationResponse> patchOrganizationOrgType(@RequestBody PatchOrganizationRequest request) {
		return new SuccessResponse(organizationService.patchOrganizationOrgType(request));
	}

	/**
	 * 주관부서 여부 수정
	 *
	 * @param request
	 * @return
	 */
	@PatchMapping("mngYn")
	public SuccessResponse<PatchOrganizationResponse> patchOrganizationMngYn(@RequestBody PatchOrganizationRequest request) {
		return new SuccessResponse(organizationService.patchOrganizationMngYn(request));
	}

}
