package net.miraeit.checklist.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.checklist.model.*;
import net.miraeit.checklist.model.selection.*;
import net.miraeit.cmm.dao.BaseDAO;
import net.miraeit.cmm.model.file.FileInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChecklistDAO extends BaseDAO {

    @SuppressWarnings("unchecked")
    public List<ChecklistDepartment> getSubOrgListAll(ChecklistDashboardGetRequest request){
        List<ChecklistDepartment> result = (List<ChecklistDepartment>) list("spsc.checklist.dashboard.subOrgListAll",request);
        for (int i = 0; i < result.size(); i++){
            request.setOrgCd(result.get(i).getOrgCd());
            List<ChecklistDepartment> check = (List<ChecklistDepartment>) list("spsc.checklist.dashboard.implementation.check",request);
            if (check.size() <= 0){
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistDepartment> getSubOrgList(ChecklistDashboardGetRequest request){
        List<ChecklistDepartment> result = (List<ChecklistDepartment>) list("spsc.checklist.dashboard.subOrgList",request);
        for (int i = 0; i < result.size(); i++){
            request.setOrgCd(result.get(i).getOrgCd());
            List<ChecklistDepartment> check = (List<ChecklistDepartment>) list("spsc.checklist.dashboard.implementation.check",request);
            if (check.size() <= 0){
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistInside> getDashboardStatistics(ChecklistInside request){
        return (List<ChecklistInside>) list("spsc.checklist.dashboard.statistics",request);
    }

    public ChecklistDepartment getDashboardOrg(ChecklistDashboardGetRequest request){
        return (ChecklistDepartment) select("spsc.checklist.dashboard.get.org",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistDepartment> getDashboardMng(ChecklistDashboardGetRequest request){
        return (List<ChecklistDepartment>) list("spsc.checklist.dashboard.get.mng",request);
    }

    public ChecklistDepartment getChecklistData(ChecklistInside request){
        return (ChecklistDepartment) select("spsc.checklist.get.data",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistLawMain> getChecklistDataMain(ChecklistGetRequest request){
        return (List<ChecklistLawMain>) list("spsc.checklist.get.data.main",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistLawSub> getChecklistDataSub(ChecklistInside request){
        return (List<ChecklistLawSub>) list("spsc.checklist.get.data.sub",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistLawSub> getChecklistDataPerformanceSub(ChecklistInside request){
        return (List<ChecklistLawSub>) list("spsc.checklist.get.data.performance.sub",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistStatute> getChecklistDataStatute(ChecklistInside request){
        return (List<ChecklistStatute>) list("spsc.checklist.get.data.statute",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistStatute> getChecklistDataPerformanceStatute(ChecklistInside request){
        return (List<ChecklistStatute>) list("spsc.checklist.get.data.performance.statute",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistContent> getChecklistDataContent(ChecklistInside request){
        return (List<ChecklistContent>) list("spsc.checklist.get.data.content",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistContent> getChecklistDataPerformanceContent(ChecklistInside request){
        return (List<ChecklistContent>) list("spsc.checklist.get.data.performance.content",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistMainDepartmentsList> getChecklistDataContentMng(){
        return (List<ChecklistMainDepartmentsList>) list("spsc.checklist.get.data.content.mng");
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistStatuteList> getChecklistDataContentStatute(){
        return (List<ChecklistStatuteList>) list("spsc.checklist.get.data.content.statute");
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistResultList> getChecklistDataResult() {
        return (List<ChecklistResultList>) list("spsc.checklist.get.data.result"); // todo
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistResultList> getChecklistDataPerformanceResult(ChecklistInside request) {
        return (List<ChecklistResultList>) list("spsc.checklist.get.data.performance.result",request); // todo
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistEquipments> getChecklistDataEquipment(ChecklistInside request) {
        return (List<ChecklistEquipments>) list("spsc.checklist.get.data.equipment",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistPerformanceMainGetResponse> getChecklistPerformanceMain(ChecklistPerformanceMainGetRequest request) {
        return (List<ChecklistPerformanceMainGetResponse>) list("spsc.checklist.get.performance.main",request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistPerformanceSubGetResponse> getChecklistPerformanceSub(ChecklistPerformanceSubGetRequest request) {
        return (List<ChecklistPerformanceSubGetResponse>) list("spsc.checklist.get.performance.sub",request);
    }

    public void patchChecklistImplementState(ChecklistImplementStateRequest request) {
        update("spsc.checklist.patch.implement.state",request);
    }

    public void patchChecklistPerformanceInspect(ChecklistPerformanceInspectPatchRequest request) {
        update("spsc.checklist.patch.performance.inspect",request);
    }

    public void patchChecklistPerformanceInspectState(ChecklistPerformanceInspectStatePatchRequest request) {
        update("spsc.checklist.patch.performance.inspect.state",request);
    }

    public void patchChecklistPerformanceUnimplReason(ChecklistPerformanceUnimplReasonPatchRequest request) {
        update("spsc.checklist.patch.performance.unimpl.reason", request);
    }

    @SuppressWarnings("unchecked")
    public List<ChecklistDepartment> getChecklistDataResultDepartment(){
        return (List<ChecklistDepartment>) list("spsc.checklist.get.data.result.department");
    }

    @SuppressWarnings("unchecked")
    public List<FileInformation> getChecklistDataResultFile(){
        return (List<FileInformation>) list("spsc.checklist.get.data.result.file");
    }




    public List<ChecklistImplementItem> selectChecklistImplementItems(ChecklistGetRequest request) {
        return selectList("spsc.checklist.get.checklist.implement.item", request);
    }

    public List<ChecklistImplementEquipment> selectChecklistImplementEquipments(ChecklistGetRequest request) {
        return selectList("spsc.checklist.get.checklist.implement.equipment", request);
    }

    public List<ChecklistImplementManagerOrg> selectChecklistImplementManagerOrgs(ChecklistGetRequest request) {
        return selectList("spsc.checklist.get.checklist.implement.manager.org", request);
    }

    public List<ChecklistImplementStatute> selectChecklistImplementStatutes(ChecklistGetRequest request) {
        return selectList("spsc.checklist.get.checklist.implement.statute", request);
    }

    public List<ChecklistImplementLawMainSub> selectChecklistImplementLawMainSub(ChecklistGetRequest request) {
        return selectList("spsc.checklist.get.checklist.implement.law.main.sub", request);
    }


}
