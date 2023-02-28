package net.miraeit.equipment.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.equipment.model.*;
import net.miraeit.equipment.dao.EquipmentDAO;
import net.miraeit.equipment.service.EquipmentService;
import net.miraeit.fx.listing.model.PagedList;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/equipment")
@RequiredArgsConstructor
public class EquipmentController {

	private final EquipmentService equipmentService;
	private final EquipmentDAO equipmentDAO;

	// 검색 조회 post 방식
	/*@PostMapping("/list")
	@ApiOperation(value="설비 목록 조회", notes = "설비 목록 조회")
	public SuccessResponse<PagedList<FindEquipmentListResponse>> getEquipmentList(@RequestBody FindEquipmentListRequest findEquipmentListRequest) {
		return new SuccessResponse(equipmentDAO.findEquipmentList(findEquipmentListRequest));
	}*/

	// 검색 조회 get 방식
	@GetMapping("/list")
	public SuccessResponse<PagedList<FindEquipmentListResponse>> getEquipmentList(@ModelAttribute FindEquipmentListRequest findEquipmentListRequest) {
		return new SuccessResponse(equipmentDAO.findEquipmentList(findEquipmentListRequest));
	}

	@GetMapping("/equipment-nm-check/{equipNm}")
	@ApiOperation(value="설비명 중복 확인", notes = "설비명 중복 여부 확인")
	@ApiImplicitParams({@ApiImplicitParam(name = "equipNm", value="설비명")})
	public SuccessResponse<Integer> getEquipmentCountByEquipNm(@PathVariable final String equipNm) {
		return new SuccessResponse<>(equipmentService.getEquipmentCountByEquipNm(equipNm));
	}

	@PostMapping
	@ApiOperation(value="설비 등록", notes = "설비 등록")
	public SuccessResponse<PostEquipmentResponse> postEquipment(@RequestBody PostEquipmentRequest postEquipmentRequest) {
		return new SuccessResponse(equipmentService.saveEquipment(postEquipmentRequest));
	}

	@PatchMapping
	@ApiOperation(value="설비명 수정", notes = "설비명 수정")
	public SuccessResponse patchEquipment(@RequestBody PatchEquipmentRequest patchEquipmentRequest) {
		equipmentDAO.updateEquipment(patchEquipmentRequest);
		return new SuccessResponse(null);
	}

	@DeleteMapping
	@ApiOperation(value="설비 삭제", notes = "설비 삭제")
	public SuccessResponse deleteEquipment(@RequestBody DeleteEquipmentRequest deleteEquipmentRequest) {
		equipmentDAO.deleteEquipment(deleteEquipmentRequest);
		return new SuccessResponse(null);
	}
}
