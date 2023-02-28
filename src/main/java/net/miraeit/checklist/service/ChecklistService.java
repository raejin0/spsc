package net.miraeit.checklist.service;

import lombok.RequiredArgsConstructor;
import net.miraeit.checklist.dao.ChecklistDAO;
import net.miraeit.checklist.model.*;
import net.miraeit.checklist.model.selection.*;
import net.miraeit.cmm.condition.FileDetailConditions;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.model.MappedList;
import net.miraeit.cmm.model.file.FileDetailDTO;
import net.miraeit.cmm.model.file.FileInformation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("ChecklistService")
@RequiredArgsConstructor
public class ChecklistService {
    private final ChecklistDAO checklistDAO;
    private final FileDAO fileDAO;

    public ChecklistDashboardGetResponse getDashboard(ChecklistDashboardGetRequest request){
        ChecklistDashboardGetResponse result = new ChecklistDashboardGetResponse();
        ChecklistInside inside = new ChecklistInside();
        ChecklistProgressOnDepartment progressOnDepartment = new ChecklistProgressOnDepartment();
        ChecklistDepartment department = new ChecklistDepartment();

        result.setOptTimeYear(request.getOptTimeYear());
        result.setOptTimeHalf(request.getOptTimeHalf());
        result.setDisClaCode(request.getDisClaCode());
        result.setMngOrgCd(request.getMngOrgCd());
        result.setOrgCd(request.getOrgCd());

        inside.setOptTimeYear(request.getOptTimeYear());
        inside.setOptTimeHalf(request.getOptTimeHalf());
        inside.setDisClaCode(request.getDisClaCode());
        inside.setMngOrgCd(request.getMngOrgCd());

        if (request.getOrgCd() == null) {
            department.setOrgCd("ALL");
            department.setOrgNm("전체");
            department.setFullOrgNm("전체");
            department.setOrgType(1);
//            department.setSubOrganization(checklistDAO.getSubOrgListAll(request));
            List<ChecklistDepartment> departmentList = checklistDAO.getSubOrgListAll(request);
            List<ChecklistProgressOnDepartment> progressOnImpl = new ArrayList<>();

            progressOnDepartment.setDepartment(department);
            progressOnDepartment.setProgress(dashboardStatistics(inside));

            for (ChecklistDepartment data : departmentList) {
                ChecklistProgressOnDepartment progressOnDepartmentAllList = new ChecklistProgressOnDepartment();
                progressOnDepartmentAllList.setDepartment(data);
                inside.setOrgCd(data.getOrgCd());
                progressOnDepartmentAllList.setProgress(dashboardStatistics(inside));
                progressOnImpl.add(progressOnDepartmentAllList);
            }

            result.setProgressOnTargetOrg(progressOnDepartment);
            result.setProgressListOnImplDepartments(progressOnImpl);
            request.setOrgCd(null);
        }else {
            if (request.getOrgCd().equals("0000")){
                department.setOrgCd("0000");
                department.setOrgNm("본사");
                department.setFullOrgNm("본사");
                department.setParentOrgCd(null);
                department.setOrgType(1);
            }else {
                department = checklistDAO.getDashboardOrg(request);
            }
            List<ChecklistProgressOnDepartment> progressOnImpl = new ArrayList<>();
            List<ChecklistDepartment> departmentList = checklistDAO.getSubOrgList(request);

            progressOnDepartment.setDepartment(department);
            inside.setOrgCd(department.getOrgCd());
            progressOnDepartment.setProgress(dashboardStatistics(inside));

            for (ChecklistDepartment data : departmentList) {
                ChecklistProgressOnDepartment progressOnDepartmentAllList = new ChecklistProgressOnDepartment();
                progressOnDepartmentAllList.setDepartment(data);
                inside.setOrgCd(data.getOrgCd());
                if(data.getOrgType().equals(3)) {
                    List<ChecklistInside> statisticsData = checklistDAO.getDashboardStatistics(inside);
                    progressOnDepartmentAllList.setItemCnt(statisticsData.size());
                }
                progressOnDepartmentAllList.setProgress(dashboardStatistics(inside));
                progressOnImpl.add(progressOnDepartmentAllList);
            }

            result.setProgressOnTargetOrg(progressOnDepartment);
            result.setProgressListOnImplDepartments(progressOnImpl);
        }

        List<ChecklistDepartment> mngList = checklistDAO.getDashboardMng(request);
        List<ChecklistProgressOnDepartment> progressOnMng = new ArrayList<>();
        inside.setOrgCd(request.getOrgCd());

        for (ChecklistDepartment data : mngList){
            ChecklistProgressOnDepartment progressOnDepartmentMng = new ChecklistProgressOnDepartment();
            progressOnDepartmentMng.setDepartment(data);
            inside.setMngOrgCd(data.getOrgCd());
            progressOnDepartmentMng.setProgress(dashboardStatistics(inside));
            progressOnMng.add(progressOnDepartmentMng);
        }

        result.setProgressListOnMainDepartments(progressOnMng);
        /* KOMIPO 기술연구원 제외하기 위함 */
        List<ChecklistProgressOnDepartment> impls = result.getProgressListOnImplDepartments();
        for(int i=0 ; i<impls.size() ; i++) {
            ChecklistProgressOnDepartment item = impls.get(i);
            if("8940".equals(item.getDepartment().getOrgCd())) {
                impls.remove(item);
                break;
            }
        }

        return result;
    }

    /**
     * 부서의 체크리스트 항목  전체목록 구성(구분 없음)
     * @param items
     * @param statutes
     * @param managerOrgs
     * @param progressOnDepartment
     * @return
     */
    private List<ChecklistImplementList> buildChecklistImplements(
            List<ChecklistImplementItem> items,
            List<ChecklistImplementStatute> statutes,
            List<ChecklistImplementManagerOrg> managerOrgs,
            ChecklistProgressOnDepartment progressOnDepartment
    ) {
        // 첨부파일 전체 조회
        FileDetailConditions fileConditions = new FileDetailConditions();
        items.forEach(item -> {
            fileConditions.addAtchFileId(item.getImplFileId());
            fileConditions.addAtchFileId(item.getPointOutFileId());
        });
        MappedList<Integer, FileDetailDTO> fileMappedList = new MappedList<>(
            fileDAO.findFilesOfMultiplId(fileConditions), FileDetailDTO::getAtchFileId
        );
        MappedList<Integer, ChecklistImplementStatute> statuteMappedList = new MappedList<>(statutes, ChecklistImplementStatute::getLawImplId);
        MappedList<Integer, ChecklistImplementManagerOrg> managerOrgMappedList = new MappedList<>(managerOrgs, ChecklistImplementManagerOrg::getLawImplId);

        // 전체 체크리스트 생성
        List<ChecklistImplementList> implementList = new ArrayList<>();
        items.forEach(item -> {
            ChecklistImplementList implement = new ChecklistImplementList();
            ChecklistContent content = item.getContent();
            ChecklistResultList result = item.getResult();

            // 관련 법령 목록 설정
            statuteMappedList.getList(item.getLawImplId()).forEach(x -> content.addStatute(x.getChecklistStatuteList()));
            // 주관부서 설정
            managerOrgMappedList.getList(item.getLawImplId()).forEach(x->content.addMainDepartment(x.getChecklistMainDepartmentsList()));
            // 첨부파일 설정
            fileMappedList.getList(item.getImplFileId()).forEach(result::addAttachment);
            fileMappedList.getList(item.getPointOutFileId()).forEach(result::addAttachment);
            result.setDepartment(progressOnDepartment.getDepartment());

            implement.setContent(content);
            implement.addChecklistResult(result);

            implementList.add(implement);
        });
        return implementList;
    }

    /**
     * 체크리스트를 소분류로 구분하여 그룹화 처리(소분류 내에 관련법령으로 추가 분류처리 함)
     * @param implementsList
     * @param masters
     * @param equipments
     * @return
     */
    private List<ChecklistLawSubList> buildLawSubImplementsList(
            List<ChecklistImplementList> implementsList,
            List<ChecklistImplementLawMainSub> masters,
            List<ChecklistImplementEquipment> equipments
    ) {
        List<Integer> filterList = new ArrayList<>(); // 중복처리를 막기위해 재사용할 단순 integer 리스트
        MappedList<Integer, ChecklistImplementList> implementMappedList = new MappedList<>(
                implementsList, x -> x.getContent().getLawSubId()
        );
        MappedList<Integer, ChecklistImplementEquipment> equipmentMappedList = new MappedList<>(
                equipments, ChecklistImplementEquipment::getLawRelId
        );

        List<ChecklistLawSubList> lawSubImplementsList = new ArrayList<>();
        masters.forEach(mainSub -> {
            List<ChecklistImplementList> impls = implementMappedList.getList(mainSub.getLawSubId());
            if(CollectionUtils.isEmpty(impls)) return;

            ChecklistLawSubList lawSubImplements = new ChecklistLawSubList();
            lawSubImplements.setLawSub(mainSub.getChecklistLawSub());

            // 관련법령 그룹화 모델 생성
            MappedList<Integer, ChecklistStatuteImplementsList> statuteImplementMappedList =
                    new MappedList<>(x -> x.getStatute().getLawRelId());
            impls.forEach(impl -> {
                List<ChecklistStatuteList> statuteList = impl.getContent().getStatuteList();
                if(CollectionUtils.isEmpty(statuteList)) { // 관련법령이 없는경우
                    ChecklistStatuteImplementsList statuteImplementsList = statuteImplementMappedList.get(-1);
                    if(statuteImplementsList==null) {
                        statuteImplementsList = new ChecklistStatuteImplementsList();
                        statuteImplementsList.setStatute(new ChecklistStatute());   // 의미 없는 빈 값. 필요하던가?
                        statuteImplementMappedList.add(-1, statuteImplementsList);
                    }
                    statuteImplementsList.addChecklistImplementList(impl);
                    return;
                }

                // 관련법령별로 분배처리(동일한 항목이 반복되는 문제가 있으나 일단은 감안 해야 한다)
                filterList.clear();
                statuteList.forEach(statute -> {
                    if(filterList.contains(statute.getLawRelId())) return;

                    ChecklistStatuteImplementsList statuteImplementsList = statuteImplementMappedList.get(statute.getLawRelId());
                    if(statuteImplementsList==null) {
                        statuteImplementsList = new ChecklistStatuteImplementsList();
                        statuteImplementsList.setStatute(statute.getChecklistStatute());
                        statuteImplementMappedList.add(statuteImplementsList);
                    }
                    statuteImplementsList.addChecklistImplementList(impl);
                    // 대상설비 설정
                    statuteImplementsList.setEquipments( // stream을 사용하면 더 복잡해 보이는것 같은데...
                            equipmentMappedList.getList(statute.getLawRelId())
                                    .stream().map(ChecklistImplementEquipment::getChecklistEquipment)
                                    .collect(Collectors.toList())
                    );
                    filterList.add(statute.getLawRelId());
                });
            });

            lawSubImplements.setStatuteImplementsList(statuteImplementMappedList.getCollection());
            lawSubImplementsList.add(lawSubImplements);
        });

        return lawSubImplementsList;
    }

    /**
     * 소분류 - 관련법령 별 체크리스트 목록을 대분류로 그룹화 처리
     * @param lawSubImplementsList
     * @param masters
     * @return
     */
    private List<ChecklistLawMainList> buildLawMainImplementsList (
            List<ChecklistLawSubList> lawSubImplementsList,
            List<ChecklistImplementLawMainSub> masters
    ) {
        List<Integer> filterList = new ArrayList<>();
        MappedList<Integer, ChecklistLawSubList> lawSubMappedList =
                new MappedList<>(lawSubImplementsList, x -> x.getLawSub().getLawMainId());
        List<ChecklistLawMainList> lawMainList = new ArrayList<>();
        masters.forEach(master -> {
            if(filterList.contains(master.getLawMainId())) return;
            List<ChecklistLawSubList> lawSubList = lawSubMappedList.getList(master.getLawMainId());
            if(CollectionUtils.isEmpty(lawSubList)) return;
            ChecklistLawMainList lawMain = new ChecklistLawMainList();
            lawMain.setLawMain(master.getChecklistLawMain());
            lawMain.setLawSubImplementsList(lawSubList);
            lawMainList.add(lawMain);
            filterList.add(master.getLawMainId());
        });
        return lawMainList;
    }

    /**
     * 부서 체크리스트 조회
     * @param request
     * @return
     */
    public ChecklistGetResponse getChecklistOnDepartment(ChecklistGetRequest request) {
        List<ChecklistImplementLawMainSub> masters = checklistDAO.selectChecklistImplementLawMainSub(request);
        List<ChecklistImplementStatute> statutes = checklistDAO.selectChecklistImplementStatutes(request);
        List<ChecklistImplementEquipment> equipments = checklistDAO.selectChecklistImplementEquipments(request);
        List<ChecklistImplementItem> items = checklistDAO.selectChecklistImplementItems(request);
        List<ChecklistImplementManagerOrg> managerOrgs = checklistDAO.selectChecklistImplementManagerOrgs(request);

        // 대상 부서 진행률 정보 설정
        ChecklistInside inside = new ChecklistInside();
        inside.setOptTimeHalf(request.getOptTimeHalf());
        inside.setOptTimeYear(request.getOptTimeYear());
        inside.setOrgCd(request.getOrgCd());
        inside.setLawCttsId(null);
        Double sts = dashboardStatistics(inside);
        ChecklistProgressOnDepartment progressOnDepartment = new ChecklistProgressOnDepartment();
        progressOnDepartment.setProgress(sts);
        progressOnDepartment.setDepartment(checklistDAO.getChecklistData(inside));

        // 전체 체크리스트
        List<ChecklistImplementList> implementsList = buildChecklistImplements(items, statutes, managerOrgs, progressOnDepartment);
        // 소분류(각호) 아이디로 그룹화
        List<ChecklistLawSubList> lawSubImplementsList = buildLawSubImplementsList(implementsList, masters, equipments);
        // 대분류 아이디로 그룹화
        List<ChecklistLawMainList> lawMainList = buildLawMainImplementsList(lawSubImplementsList, masters);


        ChecklistGetResponse retVal = new ChecklistGetResponse();
        retVal.setOptTimeYear(request.getOptTimeYear());
        retVal.setOptTimeHalf(request.getOptTimeHalf());
        retVal.setLawMainImplementsList(lawMainList);
        retVal.setProgressOnDepartment(progressOnDepartment);

        return retVal;
    }

    public ChecklistGetResponse getChecklistData(ChecklistGetRequest request){
        ChecklistGetResponse result = new ChecklistGetResponse();
        ChecklistInside inside = new ChecklistInside();

        result.setOptTimeHalf(request.getOptTimeHalf());
        result.setOptTimeYear(request.getOptTimeYear());

        inside.setOptTimeHalf(request.getOptTimeHalf());
        inside.setOptTimeYear(request.getOptTimeYear());
        inside.setOrgCd(request.getOrgCd());
        inside.setLawCttsId(null);

        Double sts = dashboardStatistics(inside);

        ChecklistProgressOnDepartment progressOnDepartment = new ChecklistProgressOnDepartment();
        progressOnDepartment.setProgress(sts);
        progressOnDepartment.setDepartment(checklistDAO.getChecklistData(inside));

        result.setProgressOnDepartment(progressOnDepartment);

        List<ChecklistLawMain> lawMain = checklistDAO.getChecklistDataMain(request);
        List<ChecklistLawMainList> lawMainList = new ArrayList<>();
        List<FileInformation> fileList = checklistDAO.getChecklistDataResultFile();
        List<ChecklistDepartment> resultDepartmentList = checklistDAO.getChecklistDataResultDepartment();
        List<ChecklistMainDepartmentsList> checklistMainDepartmentsList = checklistDAO.getChecklistDataContentMng();
        List<ChecklistStatuteList> checklistStatuteList = checklistDAO.getChecklistDataContentStatute();
        List<ChecklistResultList> resultLists = checklistDAO.getChecklistDataResult();

        if (lawMain == null || lawMain.size() == 0) return result;
        else {
            for (ChecklistLawMain data : lawMain){
                ChecklistLawMainList mainListData = new ChecklistLawMainList();
                mainListData.setLawMain(data);

                inside.setLawMainId(data.getLawMainId());
                inside.setLawSubId(null);
                inside.setLawRelId(null);
                List<ChecklistLawSub> lawSub = checklistDAO.getChecklistDataSub(inside);
                List<ChecklistLawSubList> lawSubList = new ArrayList<>();

                if (lawSub != null){
                    for (ChecklistLawSub subData : lawSub){
                        ChecklistLawSubList subListData = new ChecklistLawSubList();
                        subListData.setLawSub(subData);

                        inside.setLawSubId(subData.getLawSubId());
                        List<ChecklistStatute> statute = checklistDAO.getChecklistDataStatute(inside);
                        List<ChecklistStatuteImplementsList> statuteImplementsList = new ArrayList<>();
                        if (statute != null){
                            for (ChecklistStatute statuteData : statute){
                                ChecklistStatuteImplementsList statuteImplementsListData = new ChecklistStatuteImplementsList();

                                inside.setLawRelId(statuteData.getLawRelId());
                                List<ChecklistEquipments> equipments = checklistDAO.getChecklistDataEquipment(inside);
                                statuteImplementsListData.setEquipments(equipments);

                                statuteImplementsListData.setStatute(statuteData);
                                statuteImplementsList.add(statuteImplementsListData);
                                subListData.setStatuteImplementsList(statuteImplementsList);

                                inside.setLawRelDtlId(null);
                                List<ChecklistContent> content = checklistDAO.getChecklistDataContent(inside);
                                if (content != null){
                                    for (ChecklistContent contentData : content){
                                        ChecklistImplementList implementListData = new ChecklistImplementList();
                                        List<ChecklistImplementList> implementList = new ArrayList<>();
                                        implementListData.setContent(contentData);
                                        implementList.add(implementListData);
                                        statuteImplementsListData.setImplementList(implementList);

                                        inside.setLawImplId(contentData.getLawImplId());
                                        List<ChecklistMainDepartmentsList> checklistMainDepartmentsListData = new ArrayList<>();
                                        List<ChecklistStatuteList> checklistStatuteListData = new ArrayList<>();

                                        if (checklistMainDepartmentsList != null) {
                                            for (ChecklistMainDepartmentsList mainDepartmentsList : checklistMainDepartmentsList) if (mainDepartmentsList.getLawImplId().equals(contentData.getLawImplId())) checklistMainDepartmentsListData.add(mainDepartmentsList);
                                        }

                                        if (checklistStatuteList != null) {
                                            for (ChecklistStatuteList statuteList : checklistStatuteList) if (statuteList.getLawImplId().equals(contentData.getLawImplId())) checklistStatuteListData.add(statuteList);
                                        }

                                        contentData.setMainDepartmentsList(checklistMainDepartmentsListData);
                                        contentData.setStatuteList(checklistStatuteListData);
                                        List<ChecklistResultList> resultList = new ArrayList<>();
                                        for (ChecklistResultList checklistResultList : resultLists){
                                            if (checklistResultList.getLawImplId().equals(contentData.getLawImplId()) &&  checklistResultList.getOrgCd().equals(request.getOrgCd())) resultList.add(checklistResultList);
                                        }

                                        for(ChecklistResultList resultListData : resultList){
                                            inside.setItemId(resultListData.getItemId());
                                            ChecklistDepartment resultDepartment = null;
                                            if (resultDepartmentList != null)
                                                for (ChecklistDepartment ChecklistDepartmentData : resultDepartmentList) if (ChecklistDepartmentData.getItemId().equals(inside.getItemId())) resultDepartment = new ChecklistDepartment(ChecklistDepartmentData);
                                            resultListData.setDepartment(resultDepartment);

                                            ChecklistAttachments attachments = new ChecklistAttachments();
//                                                List<FileInformation> fileImplList = checklistDAO.getChecklistDataResultFile(resultListData.getImplFileId());
                                            List<FileInformation> fileImplList = new ArrayList<>();
                                            List<FileInformation> filePointedList = new ArrayList<>();

                                            if (fileList != null){
                                                for (FileInformation fileListData : fileList){
                                                    if (fileListData.getAtchFileId().equals(resultListData.getImplFileId())) fileImplList.add(fileListData);
                                                    else if (fileListData.getAtchFileId().equals(resultListData.getPointOutFileId())) filePointedList.add(fileListData);
                                                }
                                            }

                                            for (FileInformation fileImplListData :fileImplList){
                                                if (fileImplListData.getAtchTy().equals("F")) attachments.getImplFiles().add(fileImplListData);
                                                else if (fileImplListData.getAtchTy().equals("L")) attachments.getImplLinks().add(fileImplListData);
                                            }

//                                                List<FileInformation> filePointedList = checklistDAO.getChecklistDataResultFile(resultListData.getPointOutFileId());
                                            for (FileInformation filePointedListData :filePointedList){
                                                if (filePointedListData.getAtchTy().equals("F")) attachments.getPointedFiles().add(filePointedListData);
                                                else if (filePointedListData.getAtchTy().equals("L")) attachments.getPointedLinks().add(filePointedListData);
                                            }

                                            resultListData.setAttachments(attachments);
                                        }

                                        implementListData.setResultList(resultList);
                                    }
                                }
                            }
                        }

                        lawSubList.add(subListData);
                    }
                }
                mainListData.setLawSubImplementsList(lawSubList);
                lawMainList.add(mainListData);
            }
        }
        result.setLawMainImplementsList(lawMainList);


        return result;
    }

    public List<ChecklistPerformanceMainGetResponse> getChecklistPerformanceMain(ChecklistPerformanceMainGetRequest request) {
        return checklistDAO.getChecklistPerformanceMain(request);
    }

    public List<ChecklistPerformanceSubGetResponse> getChecklistPerformanceSub(ChecklistPerformanceSubGetRequest request) {
        return checklistDAO.getChecklistPerformanceSub(request);
    }

    public ChecklistPerformanceGetResponse getChecklistPerformance(ChecklistPerformanceGetRequest request) {

        ChecklistPerformanceGetResponse result = new ChecklistPerformanceGetResponse(); // lawSubImplementsList , progressOnDepartment

        ChecklistInside inside = new ChecklistInside();
        inside.setOptTimeHalf(request.getOptTimeHalf());
        inside.setOptTimeYear(request.getOptTimeYear());
        inside.setOrgCd(request.getOrgCd());
        inside.setLawMainId(request.getLawMainId());
        inside.setLawSubId(request.getLawSubId());
        inside.setLawRelId(request.getLawRelId());

        // progressOnDepartment =============================================================================================================
        Double sts = dashboardStatistics(inside);   // dashboard 진행률 계산
        ChecklistProgressOnDepartment progressOnDepartment = new ChecklistProgressOnDepartment(); // progressOnDepartment
        progressOnDepartment.setProgress(sts); // progressOnDepartment안에 진행률 (progress)
        progressOnDepartment.setDepartment(checklistDAO.getChecklistData(inside)); // progressOnDepartment안에 부서(최상위 부서)

        result.setProgressOnDepartment(progressOnDepartment); // result에 progressOnDepartment 세팅
        // progressOnDepartment =============================================================================================================


        // lawSubImplementsList =============================================================================================================
        List<ChecklistLawSub> lawSub = checklistDAO.getChecklistDataPerformanceSub(inside); // lawSub

        // 빈 lawSubImplementsList (lawSub, statuteImplementsList) 리스트 생성
        List<ChecklistLawSubList> lawSubList = new ArrayList<>();
        // 전체 fileList
        List<FileInformation> fileList = checklistDAO.getChecklistDataResultFile();
        // 전체 item - 이행부서
        List<ChecklistDepartment> resultDepartmentList = checklistDAO.getChecklistDataResultDepartment();
        // 전체 lawImplId - 주관부서
        List<ChecklistMainDepartmentsList> checklistMainDepartmentsList = checklistDAO.getChecklistDataContentMng();
        // 전체 lawImplId - 관련법령
        List<ChecklistStatuteList> checklistStatuteList = checklistDAO.getChecklistDataContentStatute();
        // 법령마스터 이행부서 매핑 테이블과 item 테이블 조인 => 넘겨주는 org_cd 하위 부서에 속하는 이행부서 item 전부 검색
        List<ChecklistResultList> resultLists = checklistDAO.getChecklistDataPerformanceResult(inside);

        // List<ChecklistResultList> resultLists = inspectService.getChecklistDataPerformanceResult(inside); // todo

        if (lawSub != null) {
            for (ChecklistLawSub subData : lawSub) {
                // 빈 lawSubImplementsList (lawSub, statuteImplementsList) 생성
                ChecklistLawSubList subListData = new ChecklistLawSubList();
                subListData.setLawSub(subData); // lawSub 세팅

                inside.setLawSubId(subData.getLawSubId());
                inside.setLawRelId(request.getLawRelId());

                List<ChecklistStatute> statute = checklistDAO.getChecklistDataPerformanceStatute(inside);

                // 빈 statuteImplementsList 리스트 생성 ( statute, implementList, equipments )
                List<ChecklistStatuteImplementsList> statuteImplementsList = new ArrayList<>();

                if (statute != null){
                    for (ChecklistStatute statuteData : statute){
                        // 빈 statuteImplementsList 생성 ( statute, implementList, equipments )
                        ChecklistStatuteImplementsList statuteImplementsListData = new ChecklistStatuteImplementsList();

                        inside.setLawRelId(statuteData.getLawRelId());
//                        List<ChecklistEquipments> equipments = checklistDAO.getChecklistDataEquipment(inside);
//                        statuteImplementsListData.setEquipments(equipments);

                        statuteImplementsListData.setStatute(statuteData);      // statute 세팅
                        statuteImplementsList.add(statuteImplementsListData);   // statuteImplementsList 리스트에 관련법령 (statute) 추가

                        subListData.setStatuteImplementsList(statuteImplementsList);

                        inside.setLawRelDtlId(null);

                        List<ChecklistContent> content = checklistDAO.getChecklistDataPerformanceContent(inside);

                        if (content != null){
                            List<ChecklistImplementList> implementList = new ArrayList<>(); // implementList (content, resultList) 리스트 생성
                            for (ChecklistContent contentData : content){
                                ChecklistImplementList implementListData = new ChecklistImplementList(); // implementList 생성
                                implementListData.setContent(contentData);
                                implementList.add(implementListData);
                                statuteImplementsListData.setImplementList(implementList);

                                inside.setLawImplId(contentData.getLawImplId());
                                List<ChecklistMainDepartmentsList> checklistMainDepartmentsListData = new ArrayList<>();
                                List<ChecklistStatuteList> checklistStatuteListData = new ArrayList<>();

                                // 주관부서 (mainDepartmentList)
                                if (checklistMainDepartmentsList != null) {
                                    for (ChecklistMainDepartmentsList mainDepartmentsList : checklistMainDepartmentsList)
                                        if (mainDepartmentsList.getLawImplId().equals(contentData.getLawImplId()))
                                            checklistMainDepartmentsListData.add(mainDepartmentsList);
                                }
                                contentData.setMainDepartmentsList(checklistMainDepartmentsListData); // content안에 주관부서 (mainDepartmentList)

                                // 관련법령 (statuteList)
                                if (checklistStatuteList != null) {
                                    for (ChecklistStatuteList statuteList : checklistStatuteList)
                                        if (statuteList.getLawImplId().equals(contentData.getLawImplId()))
                                            checklistStatuteListData.add(statuteList); // statuteList
                                }
                                contentData.setStatuteList(checklistStatuteListData); // content안에 관련법령 (statuteList)


                                // resultList
                                List<ChecklistResultList> resultList = new ArrayList<>();   // resultList
                                for (ChecklistResultList checklistResultList : resultLists){
                                    if (checklistResultList.getLawImplId().equals(contentData.getLawImplId()))
                                        resultList.add(checklistResultList); // resultList
                                }

                                for(ChecklistResultList resultListData : resultList){
                                    inside.setItemId(resultListData.getItemId());
                                    // 이행부서
                                    ChecklistDepartment resultDepartment = null;
                                    if (resultDepartmentList != null)
                                        for (ChecklistDepartment ChecklistDepartmentData : resultDepartmentList)
                                            if (ChecklistDepartmentData.getItemId().equals(inside.getItemId()))
                                                resultDepartment = new ChecklistDepartment(ChecklistDepartmentData); // 이행부서
                                    resultListData.setDepartment(resultDepartment); // 이행부서

                                    // attachments
                                    ChecklistAttachments attachments = new ChecklistAttachments();
                                    //List<FileInformation> fileImplList = checklistDAO.getChecklistDataResultFile(resultListData.getImplFileId());
                                    List<FileInformation> fileImplList = new ArrayList<>();
                                    List<FileInformation> filePointedList = new ArrayList<>();
                                    if (fileList != null){
                                        for (FileInformation fileListData : fileList){
                                            if (fileListData.getAtchFileId().equals(resultListData.getImplFileId())) fileImplList.add(fileListData);
                                            else if (fileListData.getAtchFileId().equals(resultListData.getPointOutFileId())) filePointedList.add(fileListData);
                                        }
                                    }

                                    for (FileInformation fileImplListData :fileImplList){
                                        if (fileImplListData.getAtchTy().equals("F")) attachments.getImplFiles().add(fileImplListData);
                                        else if (fileImplListData.getAtchTy().equals("L")) attachments.getImplLinks().add(fileImplListData);
                                    }

                                    //List<FileInformation> filePointedList = checklistDAO.getChecklistDataResultFile(resultListData.getPointOutFileId());
                                    for (FileInformation filePointedListData :filePointedList){
                                        if (filePointedListData.getAtchTy().equals("F")) attachments.getPointedFiles().add(filePointedListData);
                                        else if (filePointedListData.getAtchTy().equals("L")) attachments.getPointedLinks().add(filePointedListData);
                                    }

                                    resultListData.setAttachments(attachments);
                                }

                                implementListData.setResultList(resultList);
                            }
                        }
                    }
                }

                lawSubList.add(subListData);
            }
        }
        result.setLawSubImplementsList(lawSubList);

        return result;
    }

    public ChecklistImplementStateResponse patchChecklistImplementState(ChecklistImplementStateRequest request){
        ChecklistImplementStateResponse result = new ChecklistImplementStateResponse();
        ChecklistInside inside = new ChecklistInside();

        inside.setOptTimeHalf(request.getOptTimeHalf());
        inside.setOptTimeYear(request.getOptTimeYear());
        inside.setOrgCd(request.getOrgCd());

        ChecklistProgressOnDepartment progressOnDepartment = new ChecklistProgressOnDepartment();
        progressOnDepartment.setDepartment(checklistDAO.getChecklistData(inside));

        checklistDAO.patchChecklistImplementState(request);
        progressOnDepartment.setProgress(dashboardStatistics(inside));
        result.setProgressOnDepartment(progressOnDepartment);
        return result;
    }

    public String patchChecklistPerformanceInspect(ChecklistPerformanceInspectPatchRequest request){
        checklistDAO.patchChecklistPerformanceInspect(request);
        return null;
    }

    public String patchChecklistPerformanceInspectState(ChecklistPerformanceInspectStatePatchRequest request){
        checklistDAO.patchChecklistPerformanceInspectState(request);
        return null;
    }

    public void patchChecklistPerformanceUnimplReason(ChecklistPerformanceUnimplReasonPatchRequest request) {
        checklistDAO.patchChecklistPerformanceUnimplReason(request);
    }

    private Double dashboardStatistics(ChecklistInside inside) {
        double statistics = 0;
        List<ChecklistInside> statisticsData = checklistDAO.getDashboardStatistics(inside);
        if (statisticsData == null || statisticsData.size() == 0) return statistics;
        double xCnt = 0;
        double cnt = 0;

        for (ChecklistInside data : statisticsData){
            if (data.getImplState().equals("X")){
                xCnt += data.getStateCount();
                cnt += data.getStateCount();
            } else cnt += data.getStateCount();
        }

        if (cnt == 0) return 0.0;
        statistics = (cnt - xCnt)/cnt * 100;
        statistics = Math.round(statistics * 100) / 100.0;
        return statistics;
    }
}
