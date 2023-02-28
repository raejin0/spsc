package net.miraeit.cmm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.CMMCodesGetResponse;
import net.miraeit.cmm.model.OperationPeriod;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.cmm.model.equipment.*;
import net.miraeit.cmm.model.organization.*;
import net.miraeit.cmm.service.CMMService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = {"공통 API 를 제공하는 Controller"})
@RequestMapping("/api/cmm")
@RestController
@RequiredArgsConstructor
public class CMMController {

    private final CMMService cmmService;

    @GetMapping("codes"+"/{codeId}")
    @ApiOperation(value = "코드 조회", notes = "요청한 코드 ID 의 코드 리스트를 조회한다.")
    @ApiImplicitParam(name = "codeId" , value = "코드 ID")
    public SuccessResponse<List<CMMCodesGetResponse>> getCMMCodes(@PathVariable final String codeId){
        return new SuccessResponse<>(cmmService.getCodes(codeId));
    }

	@GetMapping("codes")
	@ApiOperation(value = "코드 다중 조회", notes="요청된 여러 코드 ID에 대한 코드 데이터를 조회한다.")
	public SuccessResponse<Map<String, List<CMMCodesGetResponse>>> getCMMCodesMap(@RequestParam List<String> codeIds) {
		return new SuccessResponse<>(cmmService.getCodesMap(codeIds));
	}

    @GetMapping("organizations")
    @ApiOperation(value = "조직 조회", notes = "사업소, 기관, 부서를 조회한다.")
	@ApiImplicitParams({@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
						@ApiImplicitParam(name = "optTimeHalf" , value = "반기"),})
    public SuccessResponse<OrganizationGetResponse> getOrganization(@Valid @ModelAttribute OrganizationGetRequest request){
        return new SuccessResponse<>(cmmService.getOrganization(request));
    }

    @GetMapping("organizations/managements")
    @ApiOperation(value = "주관 부서 조회", notes = "주관 부서 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
                        @ApiImplicitParam(name = "optTimeHalf" , value = "반기"),})
    public SuccessResponse<List<ManagementGetResponse>> getOrganizationManagement(@Valid @ModelAttribute OrganizationGetRequest request){
        return new SuccessResponse<>(cmmService.getOrganizationManagement(request));
    }

	@PatchMapping("organizations/managements")
	@ApiOperation(value = "주관 부서 변경", notes = "주관 부서 변경")
	public SuccessResponse patchOrganizationManagement(@Valid @RequestBody ManagementPatchRequest request){
		return new SuccessResponse<>(cmmService.patchOrganizationManagement(request));
	}

	@PatchMapping("organizations/type")
	@ApiOperation(value = "조직타입 변경", notes = "조직타입 변경(이행부서 여부)")
	public SuccessResponse patchOrganizationType(@Valid @RequestBody OrganizationTypePatchRequest request){
		return new SuccessResponse<>(cmmService.patchOrganizationType(request));
	}

	@PostMapping("/equipment")
	@ApiOperation(value="대상설비 입력")
	public SuccessResponse<TargetEquipmentPostRequest> addTargetEquipment(@Valid @RequestBody TargetEquipmentPostRequest targetEquipment) {
		return new SuccessResponse<>(cmmService.regTargetEquipment(targetEquipment));
	}

	@DeleteMapping("/equipment")
	@ApiOperation(value="대상설비 삭제")
	public SuccessResponse<Void> deleteTargetEquipment(@Valid @RequestBody TargetEquipmentPostRequest targetEquipment) {
		cmmService.deleteTargetEquipment(targetEquipment);
		return new SuccessResponse<>(null);
	}

	@GetMapping("/equipment-list/{optTimeYear}/{optTimeHalf}/{lawRelId}")
	@ApiOperation(value="관련법령-조직별 대상설비 목록")
	@ApiImplicitParams({@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
                        @ApiImplicitParam(name = "optTimeHalf" , value = "반기"),
						@ApiImplicitParam(name = "lawRelId" , value = "관련법령 ID")})
	public SuccessResponse<List<TargetEquipments>> getTargetEquipmentsList(@Valid @ModelAttribute TargetEquipmentListGetRequest targetEquipment) {
		return new SuccessResponse<>(cmmService.getTargetEquipmentsList(targetEquipment));
	}
	@GetMapping("/equipment-list-on-statute")
	@ApiOperation("그룹화된 관련법령-조직별 대상설비 목록")
	public SuccessResponse<List<TargetEquipmentsOnStatute>> getTargetEquipmentsOnStatuteList(@Valid @ModelAttribute TargetEquipmentOnLawGetRequest request) {
		return new SuccessResponse<>(cmmService.getTargetEquipmentsOnStatuteList(request));
	}

	@GetMapping("/equipment-all")
	@ApiOperation(value="대상설비 전체")
	public SuccessResponse<List<TargetEquipment>> getEquipmentAll() {
		return new SuccessResponse<>(cmmService.getEquipmentAll());
	}

	@GetMapping("/usable-years")
	@ApiOperation(value="유효한 연도 가져오기")
	public SuccessResponse<List<String>> getUsableYears() {
		return new SuccessResponse<>(cmmService.getUsableYears());
	}

	@GetMapping("/usable-periods")
	@ApiOperation(value = "유효한 반기 목록 가져오기")
	public SuccessResponse<List<OperationPeriod>> getUsablePeriods() {
		return new SuccessResponse<>(cmmService.getUsablePeriods());
	}

	// ------------ 관련법령 -------------------------------------------------------
	// master 패키지에 동일한 로직 존재하므로 사용하지 않는다.
	/*@PostMapping("/law-detail")
	@ApiOperation(value = "관련법령 조항 등록")
	public SuccessResponse postLawDetail(@RequestBody LawDetailPostRequest request) {
		cmmService.saveRawDetail(request);
		return new SuccessResponse(null);
	}

	@PatchMapping("/law-detail")
	@ApiOperation(value = "관련법령 조항 수정")
	public SuccessResponse patchLawDetail(@RequestBody LawDetailPatchRequest request) {
		cmmService.updateRawDetail(request);
		return new SuccessResponse(null);
	}

	@DeleteMapping("/law-detail")
	@ApiOperation(value = "관련법령 조항 삭제")
	public SuccessResponse deleteLawDetail(@RequestBody LawDetailDeleteRequest request) {
		cmmService.deleteLawDetail(request);
		return new SuccessResponse(null);
	}*/
	//  ////////// 관련법령 ////////////////////////////////////////////////////////
}
