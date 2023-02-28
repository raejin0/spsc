package net.miraeit.master.service;

import lombok.RequiredArgsConstructor;
import net.miraeit.master.dao.MasterDAO;
import net.miraeit.master.exception.*;
import net.miraeit.master.model.*;
import net.miraeit.master.model.impl.*;
import net.miraeit.master.model.main.*;
import net.miraeit.master.model.sub.*;
import net.miraeit.master.model.template.*;
import net.miraeit.master.model.text.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MasterService {

    private final MasterDAO masterDAO;

    public MasterTemplatePostResponse postMasterTemplate(MasterTemplatePostRequest request) {
        if (masterDAO.getSelectMstLawId(request) != null) throw new MstLawTplTimeOverlapException();
        Integer mstLawId = masterDAO.postMasterTemplate(request);   // 신규 등록 템플릿 아이디

        if (request.isCopy()){
            if (masterDAO.getAppliedPeriod() == null || masterDAO.getAppliedPeriod().size() == 0) throw new AppliedPeriodNullException();
            Integer copyMstLawId = masterDAO.getMasterLatestTemplateId(); // 복사할 템플릿 아이디

            List<AppliedPeriod> appliedPeriod = masterDAO.getAppliedPeriod();

            // copy response
            MasterTemplatePostResponse result = new MasterTemplatePostResponse();
            result.setOptTimeHalf(appliedPeriod.get(0).getOptTimeHalf());
            result.setOptTimeYear(appliedPeriod.get(0).getOptTimeYear());

            // copy main
            List<MasterInside> copyMain = new ArrayList<>();
            List<Integer> mainIdList = masterDAO.getMainIdByLawId(copyMstLawId);
            if (mainIdList == null || mainIdList.size()==0) return result;
            for (Integer data : mainIdList){
                MasterInside mainInside = new MasterInside();
                mainInside.setCopyLawMainId(data);
                mainInside.setMstLawId(mstLawId);
                mainInside.setLawMainId(masterDAO.copyMasterMain(mainInside));
                copyMain.add(mainInside);
            }

            // copy sub
            List<MasterInside> copySub = new ArrayList<>();
            for (MasterInside data : copyMain){
                List<Integer> subIdList = masterDAO.getMasterSubId(data.getCopyLawMainId());
                if(subIdList == null || subIdList.size()==0) continue;
                for(Integer subId : subIdList){
                    MasterInside subInside = new MasterInside();
                    subInside.setCopyLawSubId(subId);
                    subInside.setLawMainId(data.getLawMainId());
                    subInside.setLawSubId(masterDAO.copyMasterSub(subInside));
                    copySub.add(subInside);
                }
            }

            // copy text
            List<MasterInside> copyText = new ArrayList<>();
            for (MasterInside data : copySub){
                List<MasterTextListBySubId> textList = masterDAO.getTextListBySubId(data.getCopyLawSubId());
                if(textList == null || textList.size()==0) continue;
                for(MasterTextListBySubId textData : textList){
                    MasterInside textInside = new MasterInside();
                    textInside.setCopyLawCttsId(textData.getLawCttsId());
                    textInside.setLawSubId(data.getLawSubId());
                    textInside.setLawCttsId(masterDAO.copyMasterText(textInside));
                    copyText.add(textInside);
                }
            }

            // copy implementation
            List<MasterInside> copyImplementation = new ArrayList<>();
            for (MasterInside data : copyText){
                List<MasterImplListByTextId> implementationList= masterDAO.getImplListByTextId(data.getCopyLawCttsId());
                if(implementationList == null || implementationList.size()==0) continue;
                for(MasterImplListByTextId implementationData : implementationList){
                    MasterInside implementationInside = new MasterInside();
                    implementationInside.setCopyLawImplId(implementationData.getLawImplId());
                    implementationInside.setLawCttsId(data.getLawCttsId());
                    implementationInside.setLawCttsId(masterDAO.copyMasterImplementation(implementationInside));
                    copyImplementation.add(implementationInside);
                }
            }

            // copy organization
            for (MasterInside data : copyImplementation){
                masterDAO.copyMasterOrganization(data);
                masterDAO.copyMasterManagement(data);
            }

            return result;
        } else return null;
    }

    public MasterTemplateGetResponse getMasterTemplate(MasterTemplateGetRequest request) {
        MasterInside masterInside = new MasterInside();
        masterInside.setOptTimeHalf(request.getOptTimeHalf());
        masterInside.setOptTimeYear(request.getOptTimeYear());
        MasterTemplateGetResponse result = masterDAO.getMasterTemplate(masterInside);
        if (result == null) throw new MasterTemplateGetNullException();
        masterInside.setMstLawId(result.getMstLawId());
        result.setAppliedPeriodList(masterDAO.getAppliedPeriod());
        result.setMasterMainListByLawId(masterDAO.getMasterMainList(masterInside));
        return result;
    }

    public MasterTemplateGetResponse getMasterTemplates() {
        if (masterDAO.getAppliedPeriod() == null || masterDAO.getAppliedPeriod().size() == 0) throw new AppliedPeriodNullException();
        List<AppliedPeriod> appliedPeriod = masterDAO.getAppliedPeriod();

        MasterInside masterInside = new MasterInside();
        masterInside.setOptTimeHalf(appliedPeriod.get(0).getOptTimeHalf());
        masterInside.setOptTimeYear(appliedPeriod.get(0).getOptTimeYear());

        MasterTemplateGetResponse result = masterDAO.getMasterTemplate(masterInside);
        if (result == null) throw new MasterTemplateGetNullException();
        result.setAppliedPeriodList(appliedPeriod);

        masterInside.setMstLawId(result.getMstLawId());
        result.setMasterMainListByLawId(masterDAO.getMasterMainList(masterInside));
        return result;
    }

    public void patchMasterTemplate(MasterTemplatePatchRequest request) {
        masterDAO.patchMasterTemplate(request);
    }

    public void deleteMasterTemplate(MasterTemplateDeleteRequest request) {
        deleteTemplate(request.getMstLawId(),request.isForced());
    }

    public List<MasterMainSubInfoGetResponse> getMainsLowList(Integer request) {
        List<MasterMainSubInfoGetResponse> result = masterDAO.getMainsLowList(request);
        if (result == null) return null;
        for (MasterMainSubInfoGetResponse data : result) {
            data.setTextList(masterDAO.getTextListBySubId(data.getLawSubId()));
            if (masterDAO.getTextListBySubId(data.getLawSubId()) != null)
                for (MasterTextListBySubId textList : data.getTextList()) {
                    textList.setImplList(masterDAO.getImplListByTextId(textList.getLawCttsId()));
                    if (masterDAO.getImplListByTextId(textList.getLawCttsId()) != null) {
                        for (MasterImplListByTextId implList : textList.getImplList()) {
                            implList.setMainDepartmentLists(masterDAO.getMainDepartmentList(implList.getLawImplId()));
                            implList.setExecutionDepartmentLists(masterDAO.getExecutionDepartmentList(implList.getLawImplId()));
							implList.setStatuteLists(masterDAO.getTempleStatuteList(implList.getLawImplId()));
                        }
                    }
                }
        }
        return result;
    }

    public MasterMainPostResponse postMasterMain(MasterMainPostRequest request) {
        if (masterDAO.checkPostMainNo(request) != null) throw new MasterMainOverlapException();
        MasterInside templeCheck = new MasterInside();
        templeCheck.setMstLawId(request.getMstLawId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();
        return new MasterMainPostResponse(masterDAO.postMasterMain(request),request);
    }

    public void patchMasterMain(MasterMainPatchRequest request) {
        if (masterDAO.checkPatchMainNo(request) != null) throw new MasterMainOverlapException();
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawMainId(request.getLawMainId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();
        masterDAO.patchMasterMain(request);
    }

    public MasterSubPostResponse postMasterSub(MasterSubPostRequest request) {
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawMainId(request.getLawMainId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();
        return new MasterSubPostResponse(subSort(request),request);
    }

    public MasterSubDetailPostResponse postMasterSubDetail(MasterSubPostRequest request) {
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawMainId(request.getLawMainId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        /* 바로 직전 lawSubId 를 받아와서 sort 처리 */
        Integer preSortOrder = masterDAO.findLawSubSort(request.getNextOf());
        request.setLawSubSort((preSortOrder==null) ? 1 : preSortOrder + 1); // 최초 추가시에는 null일 것이므로 1번으로

        Integer subId = subSort(request);

        MasterTextPostRequest textPostRequest = new MasterTextPostRequest();
        textPostRequest.setText("-");
        textPostRequest.setLawSubId(subId);
        textPostRequest.setLawCttsSort(1);
        Integer textId = masterDAO.postMasterText(textPostRequest);

        MasterImplPostRequest implPostRequest = new MasterImplPostRequest();
        implPostRequest.setItmReqr("-");
        implPostRequest.setItmAttn("-");
        implPostRequest.setItmChk("-");
        implPostRequest.setImplProd("-");
        implPostRequest.setLawCttsId(textId);
        implPostRequest.setLawImplSort(1);
        Integer implId = masterDAO.postMasterImplementation(implPostRequest);

        return new MasterSubDetailPostResponse(subId,textId,implId,request);
    }

    public void patchMasterSub(MasterSubPatchRequest request) {
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawSubId(request.getLawSubId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        if (masterDAO.checkPatchSubNo(request) != null) throw new MasterSubOverlapException();
        if (request.getLawSubSort() == null || request.getLawSubSort() == 0) request.setLawSubSort(99999999);
        List<MasterInside> masterInsideList = masterDAO.getMasterSubSort(request.getLawMainId());
        MasterInside masterInside = new MasterInside();
        for (int i =0; i < masterInsideList.size(); i++){
            if (request.getLawSubId().equals(masterInsideList.get(i).getLawSubId())) {
                masterInsideList.remove(i);
                --i;
            }
        }
        masterInsideList = masterInsideList.stream().sorted(Comparator.comparing(MasterInside::getLawSubSort)).collect(Collectors.toList());
        masterInside.setLawSubId(request.getLawSubId());
        masterInside.setLawSubSort(request.getLawSubSort());
        if (request.getLawSubSort() > masterInsideList.size()) {
            masterInsideList.add(masterInsideList.size(),masterInside);
            request.setLawSubSort(masterInsideList.size());
        }else {
            masterInsideList.add(masterInside.getLawSubSort()-1,masterInside);
            request.setLawSubSort(masterInside.getLawSubSort());
        }
        for (int i = 0; i < masterInsideList.size(); i++){
            masterInsideList.get(i).setLawSubSort(i+1);
//            masterDAO.patchMasterSubSort(masterInsideList.get(i));
        }
        masterDAO.patchMasterSub(request);
    }

    public MasterTextPostResponse postMasterText(MasterTextPostRequest request) {
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawSubId(request.getLawSubId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        if (request.getLawCttsSort() == null || request.getLawCttsSort() == 0) request.setLawCttsSort(99999999);
        List<MasterInside> masterInsideList = masterDAO.getMasterTextSort(request.getLawSubId());
        MasterInside masterInside = new MasterInside();
        masterInsideList = masterInsideList.stream().sorted(Comparator.comparing(MasterInside::getLawCttsSort)).collect(Collectors.toList());
        Integer insertId = masterDAO.postMasterText(request);
        masterInside.setLawCttsId(insertId);
        masterInside.setLawCttsSort(request.getLawCttsSort());

        if (request.getLawCttsSort() > masterInsideList.size()) {
            masterInsideList.add(masterInsideList.size(),masterInside);
            request.setLawCttsSort(masterInsideList.size());
        }else {
            masterInsideList.add(masterInside.getLawCttsSort()-1,masterInside);
            request.setLawCttsSort(masterInside.getLawCttsSort());
        }
        for (int i = 0; i < masterInsideList.size(); i++){
            masterInsideList.get(i).setLawCttsSort(i+1);
            masterDAO.patchMasterTextSort(masterInsideList.get(i));
        }

        return new MasterTextPostResponse(insertId,request);
    }

    public MasterTextDetailPostResponse postMasterTextDetail(MasterTextPostRequest request) {
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawSubId(request.getLawSubId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        /* 바로 직전 lawCttsId 를 받아와서 sort 처리 */
        Integer preSortOrder = masterDAO.findLawCttsSort(request.getNextOf());
        request.setLawCttsSort((preSortOrder==null) ? 1 : preSortOrder + 1); // 최초 추가시에는 null일 것이므로 1번으로

        if (request.getLawCttsSort() == null || request.getLawCttsSort() == 0) request.setLawCttsSort(99999999);
        List<MasterInside> masterInsideList = masterDAO.getMasterTextSort(request.getLawSubId());
        MasterInside masterInside = new MasterInside();
        masterInsideList = masterInsideList.stream().sorted(Comparator.comparing(MasterInside::getLawCttsSort)).collect(Collectors.toList());
        Integer insertId = masterDAO.postMasterText(request);
        masterInside.setLawCttsId(insertId);
        masterInside.setLawCttsSort(request.getLawCttsSort());

        if (request.getLawCttsSort() > masterInsideList.size()) {
            masterInsideList.add(masterInsideList.size(),masterInside);
            request.setLawCttsSort(masterInsideList.size());
        }else {
            masterInsideList.add(masterInside.getLawCttsSort()-1,masterInside);
            request.setLawCttsSort(masterInside.getLawCttsSort());
        }
        for (int i = 0; i < masterInsideList.size(); i++){
            masterInsideList.get(i).setLawCttsSort(i+1);
            masterDAO.patchMasterTextSort(masterInsideList.get(i));
        }

        MasterImplPostRequest implPostRequest = new MasterImplPostRequest();
        implPostRequest.setItmReqr("-");
        implPostRequest.setItmAttn("-");
        implPostRequest.setItmChk("-");
        implPostRequest.setImplProd("-");
        implPostRequest.setLawCttsId(insertId);
        implPostRequest.setLawImplSort(1);
        Integer implId = masterDAO.postMasterImplementation(implPostRequest);

        return new MasterTextDetailPostResponse(insertId,implId,request);
    }

    public void patchMasterText(MasterTextPatchRequest request) {
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawCttsId(request.getLawCttsId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        if (request.getLawCttsSort() == null || request.getLawCttsSort() == 0) request.setLawCttsSort(99999999);
        List<MasterInside> masterInsideList = masterDAO.getMasterTextSort(request.getLawSubId());
        MasterInside masterInside = new MasterInside();

        for (int i =0; i < masterInsideList.size(); i++){
            if (request.getLawCttsId().equals(masterInsideList.get(i).getLawCttsId())) {
                masterInsideList.remove(i);
                --i;
            }
        }

        masterInsideList = masterInsideList.stream().sorted(Comparator.comparing(MasterInside::getLawCttsSort)).collect(Collectors.toList());

        masterInside.setLawCttsId(request.getLawCttsId());
        masterInside.setLawCttsSort(request.getLawCttsSort());

        if (request.getLawCttsSort() > masterInsideList.size()) {
            masterInsideList.add(masterInsideList.size(),masterInside);
            request.setLawCttsSort(masterInsideList.size());
        }else {
            masterInsideList.add(masterInside.getLawCttsSort()-1,masterInside);
            request.setLawCttsSort(masterInside.getLawCttsSort());
        }
        for (int i = 0; i < masterInsideList.size(); i++){
            masterInsideList.get(i).setLawCttsSort(i+1);
//            masterDAO.patchMasterTextSort(masterInsideList.get(i));
        }

        masterDAO.patchMasterText(request);
    }

    public MasterImplPostResponse postMasterImplementation(MasterImplPostRequest request) {
    	boolean isPost = false; // post, patch 구분

        MasterInside masterInside = new MasterInside();

        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawCttsId(request.getLawCttsId());
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        /* 바로 직전 lawImplId 를 받아와서 sort 처리 */
        Integer preSortOrder = masterDAO.findLawImplSort(request.getNextOf());
        request.setLawImplSort((preSortOrder==null) ? 1 : preSortOrder + 1); // 최초 추가시에는 null일 것이므로 1번으로

        if (request.getLawImplId() == null) {
        	request.setLawImplId(masterDAO.postMasterImplementation(request));
        	isPost = true;
        }
        else masterDAO.patchMasterImplementation(request);
        masterInside.setLawImplId(request.getLawImplId());

        // 정렬
        if (request.getLawImplSort() == null || request.getLawImplSort() == 0) request.setLawImplSort(99999999);
        List<MasterInside> masterInsideList = masterDAO.getMasterImplementationSort(request.getLawCttsId());

        for (int i =0; i < masterInsideList.size(); i++){
            if (request.getLawImplId().equals(masterInsideList.get(i).getLawImplId())) {
                masterInsideList.remove(i);
                --i;
            }
        }

        masterInsideList = masterInsideList.stream().sorted(Comparator.comparing(MasterInside::getLawImplSort)).collect(Collectors.toList());
        masterInside.setLawImplId(request.getLawImplId());
        masterInside.setLawImplSort(request.getLawImplSort());

        if (request.getLawImplSort() > masterInsideList.size()) {
            masterInsideList.add(masterInsideList.size(),masterInside);
            request.setLawImplSort(masterInsideList.size());
        }else {
            masterInsideList.add(masterInside.getLawImplSort()-1,masterInside);
            request.setLawImplSort(masterInside.getLawImplSort());
        }
        if (isPost) { // post일 경우에만 순번 재설정
	        for (int i = 0; i < masterInsideList.size(); i++){
	            masterInsideList.get(i).setLawImplSort(i+1);
	            masterDAO.patchMasterImplementationSort(masterInsideList.get(i));
	        }
        }

        // 관련 법령
        masterDAO.deleteMasterImplementationMap(request.getLawImplId());
        if (request.getLawRelDtlIdList() != null && request.getLawRelDtlIdList().size() != 0) {
            for (Integer data : request.getLawRelDtlIdList()) {
                masterInside.setLawRelDtlId(data);
                masterDAO.postRelatedMap(masterInside);
            }
        }

        // 주관 부서
        masterDAO.deleteMasterImplementationMngMap(request.getLawImplId());
        if (request.getMngOrgCdList() != null && request.getMngOrgCdList().size() != 0) {
            for (String data : request.getMngOrgCdList()){
                masterInside.setMngOrgCd(data);
                masterDAO.postMngMap(masterInside);
            }
        }

        // 이행 부서
        masterDAO.deleteMasterImplementationOrgMap(request.getLawImplId()); // 일단 TB_MST_IMPL_ORG (법령마스터-이행부서 매핑) 테이블 데이터 전부 DELETE
        if (request.getOrgCdList() != null && request.getOrgCdList().size() != 0) { // 넘어온 이행부서 목록 있을 때
            for (String data : request.getOrgCdList()){
                masterInside.setOrgCd(data);
                masterDAO.postOrgMap(masterInside);
            }
            masterDAO.deleteImplItemByOrgCd(request.getLawImplId());
        } else {    // 넘어온 이행부서 목록 비어있을 때
            masterDAO.deleteImplItem(request.getLawImplId());
        }
        return new MasterImplPostResponse(request);
    }

    public List<MasterStatuteGetResponse> getStatutes(){
        return masterDAO.getMasterStatute();
    }

    public List<MasterArticleGetResponse> getArticles(Integer request){
        return masterDAO.getMasterArticle(request);
    }

    public void deleteMasterImplementation(MasterImplDeleteRequest request){
        deleteImplementation(request.getLawImplId());
    }

    public void deleteMasterText(MasterTextDeleteRequest request){
        deleteText(request.getLawCttsId(),request.isForced());
    }

    public void deleteMasterSub(MasterSubDeleteRequest request){
        deleteSub(request.getLawSubId(),request.isForced());
    }

    public void deleteMasterMain(MasterMainDeleteRequest request){
        deleteMain(request.getLawMainId(),request.isForced());
    }

    public void postMasterStatute(MasterStatutePostRequest request){
	    Integer lawRelId = masterDAO.postMasterStatute(request);

	    MasterArticlePostRequest masterArticlePostRequest = MasterArticlePostRequest.builder()
			    .lawRelId(lawRelId)
			    .build();

	    masterDAO.postMasterArticle(masterArticlePostRequest);
    }

    public void patchMasterStatute(MasterStatutePatchRequest request){
        masterDAO.patchMasterStatute(request);
    }

    public void deleteMasterStatute(MasterStatuteDeleteRequest request){
        List<MasterArticleGetResponse> list = masterDAO.getMasterArticle(request.getLawRelId());
        if (list == null || list.size() == 0) masterDAO.deleteMasterStatute(request);
        else{
            if(request.isForced()){
                for (MasterArticleGetResponse data : list) deleteArticle(data.getLawRelDtlId(),true);
                masterDAO.deleteMasterStatute(request);
            } else throw new MasterSubDataExistException();
        }
    }

    public void postMasterArticle(MasterArticlePostRequest request){
        masterDAO.postMasterArticle(request);
    }

    public void patchMasterArticle(MasterArticlePatchRequest request){
        masterDAO.patchMasterArticle(request);
    }

    public void deleteMasterArticle(MasterArticleDeleteRequest request){
        deleteArticle(request.getLawRelDtlId(),request.isForced());
    }

    public List<MasterArticleGetResponse> getMasterGetArticleDetail(){
        return masterDAO.getMasterGetArticleDetail();
    }



    private void deleteImplementation(Integer lawImplId){
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawImplId(lawImplId);
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        masterDAO.deleteMasterImplementationMap(lawImplId);
        masterDAO.deleteMasterImplementationMngMap(lawImplId);
        masterDAO.deleteMasterImplementationOrgMap(lawImplId);
        masterDAO.deleteMasterImplementation(lawImplId);
    }

    private void deleteText(Integer lawCttsId , boolean forced){
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawCttsId(lawCttsId);
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        List<MasterImplListByTextId> list = masterDAO.getImplListByTextId(lawCttsId);
        if (list == null || list.size() == 0) masterDAO.deleteMasterText(lawCttsId);
        else{
            if(forced){
                for (MasterImplListByTextId data : list) deleteImplementation(data.getLawImplId());
                masterDAO.deleteMasterText(lawCttsId);
            } else throw new MasterSubDataExistException();
        }
    }

    private void deleteSub(Integer lawSubId, boolean forced){
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawSubId(lawSubId);
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        List<MasterTextListBySubId> list = masterDAO.getTextListBySubId(lawSubId);
        if (list == null || list.size() == 0) masterDAO.deleteMasterSub(lawSubId);
        else {
            if(forced){
                for (MasterTextListBySubId data : list) deleteText(data.getLawCttsId(), true);
                masterDAO.deleteMasterSub(lawSubId);
            } else throw new MasterSubDataExistException();
        }
    }

    private void deleteMain(Integer lawMainId, boolean forced){
        MasterInside templeCheck = new MasterInside();
        templeCheck.setLawMainId(lawMainId);
        if (masterDAO.getMasterLatestTemplateCheck(templeCheck) == null) throw new MasterNoLastTemplateException();

        List<Integer> list = masterDAO.getMasterSubId(lawMainId);
        if (list == null || list.size() == 0) masterDAO.deleteMasterMain(lawMainId);
        else {
            if (forced){
                for (Integer data : list) deleteSub(data,true);
                masterDAO.deleteMasterMain(lawMainId);
            } else throw new MasterSubDataExistException();
        }
    }

    private void deleteTemplate(Integer mstLawId, boolean forced){
        List<Integer> list = masterDAO.getMainIdByLawId(mstLawId);
        if (list == null || list.size() == 0) masterDAO.deleteMasterTemplate(mstLawId);
        else {
            if (forced) {
                for (Integer data : list) deleteMain(data, true);
                masterDAO.deleteMasterTemplate(mstLawId);
            } else throw new MasterSubDataExistException();
        }
    }

    private void deleteArticle(Integer lawRelDtlId, boolean forced){
        List<Integer> list = masterDAO.getMasterArticleMap(lawRelDtlId);
        if (list == null || list.size() == 0) masterDAO.deleteMasterArticle(lawRelDtlId);
        else {
            if (forced) {
                masterDAO.deleteMasterArticleMap(lawRelDtlId);
                masterDAO.deleteMasterArticle(lawRelDtlId);
            } else throw new MasterImplementationExistException();
        }
    }

    private Integer subSort(MasterSubPostRequest request){
        if (masterDAO.checkPostSubNo(request) != null) throw new MasterSubOverlapException();
        if (request.getLawSubSort() == null || request.getLawSubSort() == 0) request.setLawSubSort(99999999);
        List<MasterInside> masterInsideList = masterDAO.getMasterSubSort(request.getLawMainId());
        MasterInside masterInside = new MasterInside();
        masterInsideList = masterInsideList.stream().sorted(Comparator.comparing(MasterInside::getLawSubSort)).collect(Collectors.toList());
        Integer insertId = masterDAO.postMasterSub(request);
        masterInside.setLawSubId(insertId);
        masterInside.setLawSubSort(request.getLawSubSort());
        if (request.getLawSubSort() > masterInsideList.size()) {
            masterInsideList.add(masterInsideList.size(),masterInside);
            request.setLawSubSort(masterInsideList.size());
        }else {
            masterInsideList.add(masterInside.getLawSubSort()-1,masterInside);
            request.setLawSubSort(masterInside.getLawSubSort());
        }
        for (int i = 0; i < masterInsideList.size(); i++){
            masterInsideList.get(i).setLawSubSort(i+1);
            masterDAO.patchMasterSubSort(masterInsideList.get(i));
        }
        return insertId;
    }
}

