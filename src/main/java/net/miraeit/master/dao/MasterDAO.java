package net.miraeit.master.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.master.model.*;
import net.miraeit.master.model.impl.*;
import net.miraeit.master.model.main.MasterMainListByLawId;
import net.miraeit.master.model.main.MasterMainPatchRequest;
import net.miraeit.master.model.main.MasterMainPostRequest;
import net.miraeit.master.model.main.MasterMainSubInfoGetResponse;
import net.miraeit.master.model.sub.MasterSubPatchRequest;
import net.miraeit.master.model.sub.MasterSubPostRequest;
import net.miraeit.master.model.template.*;
import net.miraeit.master.model.text.MasterTextListBySubId;
import net.miraeit.master.model.text.MasterTextPatchRequest;
import net.miraeit.master.model.text.MasterTextPostRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MasterDAO extends EgovAbstractDAO {

    public Integer postMasterTemplate(MasterTemplatePostRequest request) {
        return (Integer) insert("spsc.master.post.template",request);
    }

    public MasterInside getSelectMstLawId(MasterTemplatePostRequest request) {
        return (MasterInside) select("spsc.master.get.mstlawid",request);
    }

    public MasterTemplateGetResponse getMasterTemplate(MasterInside request) {
        return (MasterTemplateGetResponse) select("spsc.master.get.template",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterMainListByLawId> getMasterMainList(MasterInside request) {
        return (List<MasterMainListByLawId>) list("spsc.master.get.mains",request);
    }

    @SuppressWarnings("unchecked")
    public List<AppliedPeriod> getAppliedPeriod() {
        return (List<AppliedPeriod>) list("spsc.master.get.applied.period");
    }

    public void patchMasterTemplate(MasterTemplatePatchRequest request) {
        update("spsc.master.patch.template",request);
    }

    public void deleteMasterTemplate(Integer request) {
        delete("spsc.master.delete.template",request);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMainIdByLawId(Integer request) {
        return (List<Integer>) list("spsc.master.get.mainIdByLawId",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterMainSubInfoGetResponse> getMainsLowList(Integer request) {
        return (List<MasterMainSubInfoGetResponse>) list("spsc.master.get.mainSubInfo",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterTextListBySubId> getTextListBySubId(Integer request) {
        return (List<MasterTextListBySubId>) list("spsc.master.get.textListBySubId",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterImplListByTextId> getImplListByTextId(Integer request) {
        return (List<MasterImplListByTextId>) list("spsc.master.get.implListByTextId",request);
    }

    @SuppressWarnings("unchecked")
    public List<MainDepartmentList> getMainDepartmentList(Integer request) {
        return (List<MainDepartmentList>) list("spsc.master.get.mainDepartmentList",request);
    }

    @SuppressWarnings("unchecked")
    public List<ExecutionDepartmentList> getExecutionDepartmentList(Integer request) {
        return (List<ExecutionDepartmentList>) list("spsc.master.get.executionDepartmentList",request);
    }

    @SuppressWarnings("unchecked")
    public List<StatuteList> getTempleStatuteList(Integer request) {
        return (List<StatuteList>) list("spsc.master.get.temple.statuteList",request);
    }

    public Integer postMasterMain(MasterMainPostRequest request) {
        return (Integer) insert("spsc.master.post.main",request);
    }

    public Integer checkPostMainNo(MasterMainPostRequest request) {
        return (Integer) select("spsc.master.check.post.mainNo",request);
    }

    public Integer checkPatchMainNo(MasterMainPatchRequest request) {
        return (Integer) select("spsc.master.check.patch.mainNo",request);
    }

    public void patchMasterMain(MasterMainPatchRequest request) {
        update("spsc.master.patch.main",request);
    }

    public Integer postMasterSub(MasterSubPostRequest request) {
        return (Integer) insert("spsc.master.post.sub",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterInside> getMasterSubSort(Integer request) {
        return (List<MasterInside>) list("spsc.master.get.subSort",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterInside> getMasterTextSort(Integer request) {
        return (List<MasterInside>) list("spsc.master.get.textSort",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterInside> getMasterImplementationSort(Integer request) {
        return (List<MasterInside>) list("spsc.master.get.implementationSort",request);
    }

    public void patchMasterSubSort(MasterInside request) {
        update("spsc.master.patch.subSort",request);
    }

    public void patchMasterTextSort(MasterInside request) {
        update("spsc.master.patch.textSort",request);
    }

    public void patchMasterImplementationSort(MasterInside request) {
        update("spsc.master.patch.implementationSort",request);
    }

    public Integer checkPostSubNo(MasterSubPostRequest request) {
        return (Integer) select("spsc.master.check.post.subNo",request);
    }

    public void patchMasterSub(MasterSubPatchRequest request) {
        update("spsc.master.patch.sub",request);
    }

    public Integer checkPatchSubNo(MasterSubPatchRequest request) {
        return (Integer) select("spsc.master.check.patch.subNo",request);
    }

    public Integer postMasterText(MasterTextPostRequest request) {
        return (Integer) insert("spsc.master.post.text",request);
    }

    public void patchMasterText(MasterTextPatchRequest request) {
        update("spsc.master.patch.text",request);
    }

    public Integer postMasterImplementation(MasterImplPostRequest request) {
        return (Integer) insert("spsc.master.post.implementation",request);
    }

    public void deleteMasterImplementationMap(Integer request) {
        delete("spsc.master.delete.implementation.map",request);
    }

    public void postRelatedMap(MasterInside request) {
        update("spsc.master.post.related.map",request);
    }

    public void patchMasterImplementation(MasterImplPostRequest request) {
        update("spsc.master.patch.implementation",request);
    }

    public void deleteMasterImplementationMngMap(Integer request) {
        delete("spsc.master.delete.implementation.mng.map",request);
    }

    public void postMngMap(MasterInside request) {
        update("spsc.master.post.mng.map",request);
    }

    public void deleteMasterImplementationOrgMap(Integer request) {
        delete("spsc.master.delete.implementation.org.map",request);
    }

    public void deleteImplItem(Integer request) {
        delete("spsc.master.delete.impl.item", request);
    }

    public void deleteImplItemByOrgCd(Integer request) {
        delete("spsc.master.delete.impl.item.by.orgCd", request);
    }

    public void postOrgMap(MasterInside request) {
        update("spsc.master.post.org.map",request);  // TB_MST_IMPL_ORG (법령마스터-이행부서 매핑) 테이블에 INSERT
        Integer implFileId = (Integer) insert("spsc.master.post.file",request);
        Integer pointOutFileId = (Integer) insert("spsc.master.post.file",request);
        Integer itemId = (Integer) select("spsc.master.post.impl.item.id");
        request.setImplFileId(implFileId);
        request.setPointOutFileId(pointOutFileId);
        request.setItemId(itemId);
        update("spsc.master.post.impl.item",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterStatuteGetResponse> getMasterStatute() {
        return (List<MasterStatuteGetResponse>) list("spsc.master.get.statute");
    }

    @SuppressWarnings("unchecked")
    public List<MasterArticleGetResponse> getMasterArticle(Integer request) {
        return (List<MasterArticleGetResponse>) list("spsc.master.get.article",request);
    }

    public void deleteMasterImplementation(Integer request) {
        delete("spsc.master.delete.implementation",request);
    }

    public void deleteMasterText(Integer request) {
        delete("spsc.master.delete.text",request);
    }

    public void deleteMasterSub(Integer request) {
        delete("spsc.master.delete.sub",request);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMasterSubId(Integer request) {
        return (List<Integer>) list("spsc.master.get.sub.id",request);
    }

    public void deleteMasterMain(Integer request) {
        delete("spsc.master.delete.main",request);
    }

    public Integer postMasterStatute(MasterStatutePostRequest request) {
        return (Integer) insert("spsc.master.post.statute", request);
    }

    public void patchMasterStatute(MasterStatutePatchRequest request) {
        update("spsc.master.patch.statute",request);
    }

    public void deleteMasterStatute(MasterStatuteDeleteRequest request) {
        delete("spsc.master.delete.statute",request);
    }

    public void postMasterArticle(MasterArticlePostRequest request) {
        insert("spsc.master.post.article",request);
    }

    public void patchMasterArticle(MasterArticlePatchRequest request) {
        update("spsc.master.patch.article",request);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMasterArticleMap(Integer request) {
        return (List<Integer>) list("spsc.master.get.article.map",request);
    }

    public void deleteMasterArticleMap(Integer request) {
        delete("spsc.master.delete.article.map",request);
    }

    public void deleteMasterArticle(Integer request) {
        delete("spsc.master.delete.article",request);
    }

    public Integer copyMasterMain(MasterInside request) {
        return (Integer) insert("spsc.master.copy.main",request);
    }

    public Integer copyMasterSub(MasterInside request) {
        return (Integer) insert("spsc.master.copy.sub",request);
    }

    public Integer getMasterLatestTemplateId() {
        return (Integer) select("spsc.master.get.latest.template.id");
    }

    public Integer copyMasterText(MasterInside request) {
        return (Integer) insert("spsc.master.copy.text",request);
    }

    public Integer copyMasterImplementation(MasterInside request) {
        return (Integer) insert("spsc.master.copy.implementation",request);
    }

    public void copyMasterOrganization(MasterInside request) {
        insert("spsc.master.copy.organization",request);
    }

    public void copyMasterManagement(MasterInside request) {
        insert("spsc.master.copy.management",request);
    }

    @SuppressWarnings("unchecked")
    public List<MasterArticleGetResponse> getMasterGetArticleDetail() {
        return (List<MasterArticleGetResponse>) list("spsc.master.get.article.detail");
    }

    public Integer getMasterLatestTemplateCheck(MasterInside request) {
        return (Integer) select("spsc.master.get.last.temple.check",request);
    }

    public Integer findLawSubSort(Integer lawSubId) {
        return (Integer) select("spsc.master.find.law.sub.sort", lawSubId);
    }

    public Integer findLawCttsSort(Integer lawCttsId) {
        return (Integer) select("spsc.master.find.law.ctts.sort", lawCttsId);
    }

    public Integer findLawImplSort(Integer lawImplId) {
        return (Integer) select("spsc.master.find.law.impl.sort", lawImplId);
    }
}
