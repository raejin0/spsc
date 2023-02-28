package net.miraeit.master.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.master.model.impl.*;
import net.miraeit.master.model.main.*;
import net.miraeit.master.model.sub.*;
import net.miraeit.master.model.template.*;
import net.miraeit.master.model.text.*;
import net.miraeit.master.service.MasterService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"마스터 API 를 제공하는 Controller"})
@RequestMapping("/api/master")
@RestController
@RequiredArgsConstructor
public class MasterController {

    private final MasterService masterService;

    @PostMapping("templates")
    @ApiOperation(value = "마스터 기본정보 템플릿 등록", notes = "마스터 기본정보 템플릿을 등록 한다.")
    public SuccessResponse<MasterTemplatePostResponse> postMasterTemplate(@Valid @RequestBody MasterTemplatePostRequest request) {
        return new SuccessResponse<>(masterService.postMasterTemplate(request));
    }

    @GetMapping("templates"+ "/{optTimeYear}/{optTimeHalf}")
    @ApiOperation(value = "마스터 기본정보 템플릿 조회", notes = "마스터 기본정보 템플릿을 조회 한다. (년, 반기 선택)")
    @ApiImplicitParams({@ApiImplicitParam(name = "optTimeHalf" , value = "적용시점 - 상/하반기 구분"),
                        @ApiImplicitParam(name = "optTimeYear" , value = "적용시점 - 년도")})
    public SuccessResponse<MasterTemplateGetResponse> getMasterTemplate(@Valid @ModelAttribute final MasterTemplateGetRequest request) {
        return new SuccessResponse<>(masterService.getMasterTemplate(request));
    }

    @GetMapping("templates")
    @ApiOperation(value = "마스터 기본정보 템플릿 조회", notes = "마스터 기본정보 템플릿을 조회 한다. (최신 날짜 기준 기본 조회)")
    public SuccessResponse<MasterTemplateGetResponse> getMasterTemplates() {
        return new SuccessResponse<>(masterService.getMasterTemplates());
    }

    @PatchMapping("templates")
    @ApiOperation(value = "마스터 기본정보 템플릿 수정", notes = "마스터 기본정보 템플릿을 수정 한다.")
    public SuccessResponse patchMasterTemplate(@Valid @RequestBody MasterTemplatePatchRequest request) {
        masterService.patchMasterTemplate(request);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("templates")
    @ApiOperation(value = "마스터 기본정보 템플릿 삭제", notes = "마스터 기본정보 템플릿을 삭제 한다.")
    public SuccessResponse deleteMasterTemplate(@Valid @RequestBody MasterTemplateDeleteRequest request) {
        masterService.deleteMasterTemplate(request);
        return new SuccessResponse<>(null);
    }

    @GetMapping("mains/sub-info"+"/{lawMainId}")
    @ApiOperation(value = "마스터 대분류의 하위 정보 조회", notes = "대분류(조항)에 속해 있는 하위 정보를 조회 한다.")
    @ApiImplicitParam(name = "lawMainId" , value = "대분류 ID")
    public SuccessResponse<List<MasterMainSubInfoGetResponse>> getMainsLowList(@PathVariable final Integer lawMainId) {
        return new SuccessResponse<>(masterService.getMainsLowList(lawMainId));
    }

    @PostMapping("mains")
    @ApiOperation(value = "법령 대분류 등록", notes = "법령 대분류 정보를 등록 한다.")
    public SuccessResponse<MasterMainPostResponse> postMasterMain(@Valid @RequestBody MasterMainPostRequest request) {
        return new SuccessResponse<>(masterService.postMasterMain(request));
    }

    @PatchMapping("mains")
    @ApiOperation(value = "법령 대분류 수정", notes = "법령 대분류 정보를 수정 한다.")
    public SuccessResponse patchMasterMain(@Valid @RequestBody MasterMainPatchRequest request) {
        masterService.patchMasterMain(request);
        return new SuccessResponse<>(null);
    }

    @PostMapping("subs")
    @ApiOperation(value = "법령 소분류 등록", notes = "법령 소분류 정보를 등록 한다.")
    public SuccessResponse<MasterSubPostResponse> postMasterSub(@Valid @RequestBody MasterSubPostRequest request) {
        return new SuccessResponse<>(masterService.postMasterSub(request));
    }

    @PostMapping("subs/detail")
    @ApiOperation(value = "법령 소분류 등록", notes = "법령 소분류 정보와 법령 내용 , 이행내용이 하나씩 같이 등록이 된다.")
    public SuccessResponse<MasterSubDetailPostResponse> postMasterSubDetail(@Valid @RequestBody MasterSubPostRequest request) {
        return new SuccessResponse<>(masterService.postMasterSubDetail(request));
    }


    @PatchMapping("subs")
    @ApiOperation(value = "법령 소분류 수정", notes = "법령 소분류 정보를 수정 한다.")
    public SuccessResponse patchMasterSub(@Valid @RequestBody MasterSubPatchRequest request) {
        masterService.patchMasterSub(request);
        return new SuccessResponse<>(null);
    }

    @PostMapping("texts")
    @ApiOperation(value = "법령 내용 등록", notes = "법령 내용을 등록 한다.")
    public SuccessResponse<MasterTextPostResponse> postMasterText(@Valid @RequestBody MasterTextPostRequest request) {
        return new SuccessResponse<>(masterService.postMasterText(request));
    }

    @PostMapping("texts/detail")
    @ApiOperation(value = "법령 내용 등록", notes = "법령 법령 내용과 이행내용이 하나씩 같이 등록이 된다.")
    public SuccessResponse<MasterTextDetailPostResponse> postMasterTextDetail(@Valid @RequestBody MasterTextPostRequest request) {
        return new SuccessResponse<>(masterService.postMasterTextDetail(request));
    }

    @PatchMapping("texts")
    @ApiOperation(value = "법령 내용 수정", notes = "법령 내용을 수정 한다.")
    public SuccessResponse patchMasterText(@Valid @RequestBody MasterTextPatchRequest request) {
        masterService.patchMasterText(request);
        return new SuccessResponse<>(null);
    }

    @PostMapping("implementation")
    @ApiOperation(value = "이행 내용 등록 및 수정", notes = "lawImplId 가 null 이면 등록 값이 있으면 수정을 한다.")
    public SuccessResponse<MasterImplPostResponse> postMasterImplementation(@Valid @RequestBody MasterImplPostRequest request) {
        return new SuccessResponse<>(masterService.postMasterImplementation(request));
    }

    @GetMapping("statutes")
    @ApiOperation(value = "관련법령 조회", notes = "관련법령의 리스트를 조회 한다.")
    public SuccessResponse<List<MasterStatuteGetResponse>> getMasterStatutes(){
        return new SuccessResponse<>(masterService.getStatutes());
    }

    @GetMapping("articles"+"/{lawRelId}")
    @ApiOperation(value = "관련법령 조항 조회", notes = "관련법령 조항 조회")
    @ApiImplicitParam(name = "lawRelId" , value = "법령 ID")
    public SuccessResponse<List<MasterArticleGetResponse>> getMasterArticles(@PathVariable final Integer lawRelId){
        return new SuccessResponse<>(masterService.getArticles(lawRelId));
    }

    @DeleteMapping("implementation")
    @ApiOperation(value = "이행내용 삭제", notes = "이행내용 삭제")
    public SuccessResponse deleteMasterImplementation(@Valid @RequestBody MasterImplDeleteRequest request) {
        masterService.deleteMasterImplementation(request);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("texts")
    @ApiOperation(value = "법령내용 삭제", notes = "법령내용 삭제")
    public SuccessResponse deleteMasterText(@Valid @RequestBody MasterTextDeleteRequest request) {
        masterService.deleteMasterText(request);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("subs")
    @ApiOperation(value = "법령소분류 삭제", notes = "법령소분류 삭제")
    public SuccessResponse deleteMasterSub(@Valid @RequestBody MasterSubDeleteRequest request) {
        masterService.deleteMasterSub(request);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("mains")
    @ApiOperation(value = "법령대분류 삭제", notes = "법령대분류 삭제")
    public SuccessResponse deleteMasterMain(@Valid @RequestBody MasterMainDeleteRequest request) {
        masterService.deleteMasterMain(request);
        return new SuccessResponse<>(null);
    }

    @PostMapping("statutes")
    @ApiOperation(value = "관련법령 등록", notes = "관련법령 등록")
    public SuccessResponse postMasterStatute(@Valid @RequestBody MasterStatutePostRequest request) {
        masterService.postMasterStatute(request);
        return new SuccessResponse<>(null);
    }

    @PatchMapping("statutes")
    @ApiOperation(value = "관련법령 수정", notes = "관련법령 수정")
    public SuccessResponse patchMasterStatute(@Valid @RequestBody MasterStatutePatchRequest request) {
        masterService.patchMasterStatute(request);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("statutes")
    @ApiOperation(value = "관련법령 삭제", notes = "관련법령 삭제")
    public SuccessResponse deleteMasterStatute(@Valid @RequestBody MasterStatuteDeleteRequest request) {
        masterService.deleteMasterStatute(request);
        return new SuccessResponse<>(null);
    }

    @PostMapping("articles")
    @ApiOperation(value = "관련법령 조항 등록", notes = "관련법령 조항 등록")
    public SuccessResponse postMasterArticle(@Valid @RequestBody MasterArticlePostRequest request){
        masterService.postMasterArticle(request);
        return new SuccessResponse<>(null);
    }

    @PatchMapping("articles")
    @ApiOperation(value = "관련법령 조항 수정", notes = "관련법령 조항 수정")
    public SuccessResponse patchMasterArticle(@Valid @RequestBody MasterArticlePatchRequest request) {
        masterService.patchMasterArticle(request);
        return new SuccessResponse<>(null);
    }

    @DeleteMapping("articles")
    @ApiOperation(value = "관련법령 조항 삭제", notes = "관련법령 조항 삭제")
    public SuccessResponse deleteMasterArticle(@Valid @RequestBody MasterArticleDeleteRequest request) {
        masterService.deleteMasterArticle(request);
        return new SuccessResponse<>(null);
    }

    @GetMapping("articles-detail")
    @ApiOperation(value = "관련법령 조항 조회 상세", notes = "사용중인 관련법령 조항이 관련법령과 같이 조회")
    public SuccessResponse<List<MasterArticleGetResponse>> getMasterGetArticleDetail(){
        return new SuccessResponse<>(masterService.getMasterGetArticleDetail());
    }

}
