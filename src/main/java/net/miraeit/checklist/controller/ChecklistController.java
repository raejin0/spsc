package net.miraeit.checklist.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.checklist.model.*;
import net.miraeit.checklist.service.ChecklistService;
import net.miraeit.cmm.model.SuccessResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Api(tags = {"체크리스트(데시보드) API 를 제공하는 Controller"})
@RequestMapping("/api/checklist")
@RestController
@RequiredArgsConstructor
public class ChecklistController {
    @Resource(name = "ChecklistService")
    private ChecklistService checklistService;

    @GetMapping("dashboard")
    @ApiOperation(value = "메인화면 데이터", notes = "대시보드 메인 데이터 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
                        @ApiImplicitParam(name = "optTimeHalf" , value = "반기"),
                        @ApiImplicitParam(name = "orgCd" , value = "조직코드(사업소, 부처 등)"),
                        @ApiImplicitParam(name = "disClaCode" , value = "재해구분(serious, civil)"),
                        @ApiImplicitParam(name = "mngOrgCd" , value = "주관부서코드")})
    public SuccessResponse<ChecklistDashboardGetResponse> getDashboard(@Valid @ModelAttribute ChecklistDashboardGetRequest request){
        return new SuccessResponse<>(checklistService.getDashboard(request));
    }

    @GetMapping()
    @ApiOperation(value = "체크리스트 데이터", notes = "이행관리 입력 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "orgCd" , value = "조직코드"),
                        @ApiImplicitParam(name = "optTimeYear" , value = "연도"),
                        @ApiImplicitParam(name = "optTimeHalf" , value = "반기"),
                        @ApiImplicitParam(name = "disasterType" , value = "재해구분 필요?? 쓰이는 곳 X")})
    public SuccessResponse<ChecklistGetResponse> getChecklistData(@Valid @ModelAttribute ChecklistGetRequest request){
//        return new SuccessResponse<>(checklistService.getChecklistData(request));
        return new SuccessResponse<>(checklistService.getChecklistOnDepartment(request));
    }

    @PatchMapping("implement/state")
    @ApiOperation(value = "적부 판단", notes = "이행관리 화면의 이행상태(적부판단) 수정")
    public SuccessResponse<ChecklistImplementStateResponse> patchChecklistImplementState(@Valid @RequestBody ChecklistImplementStateRequest request){
        return new SuccessResponse<>(checklistService.patchChecklistImplementState(request));
    }

    @GetMapping("performance/mains")
    @ApiOperation(value = "대분류 조회(이행실적관리)", notes = "이행실적관리 화면의 대분류 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "optTimeYear" , value = "연도"),
                        @ApiImplicitParam(name = "optTimeHalf" , value = "반기")})
    public SuccessResponse<List<ChecklistPerformanceMainGetResponse>> getChecklistPerformanceMain(@Valid @ModelAttribute ChecklistPerformanceMainGetRequest request){
        return new SuccessResponse<>(checklistService.getChecklistPerformanceMain(request));
    }

    @GetMapping("performance/subs")
    @ApiOperation(value = "소분류 조회(이행실적관리)", notes = "이행실적관리 화면의 대분류 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "lawMainId" , value = "대분류 아이디")})
    public SuccessResponse<List<ChecklistPerformanceSubGetResponse>> getChecklistPerformanceSub(@Valid @ModelAttribute ChecklistPerformanceSubGetRequest request){
        return new SuccessResponse<>(checklistService.getChecklistPerformanceSub(request));
    }

    @GetMapping("performance")
    @ApiOperation(value = "체크리스트 데이터", notes = "이행관리 실적 입력 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "orgCd" , value = "조직코드"),
                        @ApiImplicitParam(name = "optTimeYear" , value = "연도"),
                        @ApiImplicitParam(name = "optTimeHalf" , value = "반기"),
                        @ApiImplicitParam(name = "lawMainId" , value = "대분류 아이디"),
                        @ApiImplicitParam(name = "lawSubId" , value = "소분류 아이디"),
                        @ApiImplicitParam(name = "lawRelId" , value = "관련법령 아이디")})
    public SuccessResponse<ChecklistPerformanceGetResponse> getChecklistPerformance(@Valid @ModelAttribute ChecklistPerformanceGetRequest request){
        return new SuccessResponse<>(checklistService.getChecklistPerformance(request));
    }

    @PatchMapping("performance/inspect")
    @ApiOperation(value = "점검내용", notes = "점검내용의 수정")
    public SuccessResponse patchChecklistPerformanceInspect(@Valid @RequestBody ChecklistPerformanceInspectPatchRequest request){
        return new SuccessResponse<>(checklistService.patchChecklistPerformanceInspect(request));
//	    return null;
    }

    @PatchMapping("performance/inspect/state")
    @ApiOperation(value = "점검사항", notes = "정검사항의 적부 판단")
    public SuccessResponse patchChecklistPerformanceInspectState(@Valid @RequestBody ChecklistPerformanceInspectStatePatchRequest request){
        return new SuccessResponse<>(checklistService.patchChecklistPerformanceInspectState(request));
    }

    @PatchMapping("performance/unimpl-reason")
    @ApiOperation("불이행 사유")
    public SuccessResponse<Void> patchChecklistPerformanceUnimplReason(@Valid @RequestBody ChecklistPerformanceUnimplReasonPatchRequest request) {
        checklistService.patchChecklistPerformanceUnimplReason(request);
        return new SuccessResponse(null);
    }
}
