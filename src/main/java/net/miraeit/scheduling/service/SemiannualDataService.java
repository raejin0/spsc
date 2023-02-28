package net.miraeit.scheduling.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.scheduling.model.semiannual.*;
import net.miraeit.scheduling.model.semiannual.map.ImplItem;
import net.miraeit.scheduling.model.semiannual.map.ImplLawDtlMap;
import net.miraeit.scheduling.model.semiannual.map.ImplOrgMap;
import net.miraeit.scheduling.dao.SchedulingDAO;
import net.miraeit.scheduling.dao.SemiannualRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 반기 데이터 처리용 Service
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SemiannualDataService {

	private final SemiannualRepository semiannualRepository;
	private final SchedulingDAO schedulingDAO;
	private final FileDAO       fileDAO;

	/**
	 * 반기데이터 - 마스터 정보 생성
	 */
	public void update(){ // method name to be redefined
		log.info("[START] SemiannualDataService.proceedBasicMasterInfo: 반기데이터");

		// 마스터정보
		MasterLawTemplate masterLawTemplate = semiannualRepository.findLastMasterLawTemplate();
		masterLawTemplate = saveNewMasterLawTemplate(masterLawTemplate);

		// 법령마스터 대분류
		List<MasterLawMain> lawMainList = semiannualRepository.findLawMainListByMstLawId(masterLawTemplate);
		lawMainList = saveNewLawMainList(lawMainList);

		// 법령마스터 소분류
		List<MasterLawSub> lawSubList = findLawSubListByMainId(lawMainList);
		lawSubList = saveNewLawSubList(lawSubList);

		// 법령내용
		List<MasterLawText> lawTextList = findLawTextList(lawSubList);
		lawTextList = saveLawTextList(lawTextList);

		// 이행정보
		List<MasterLawImpl> lawImplList = findLawImplList(lawTextList);
		lawImplList = saveLawImplList(lawImplList);

		// 조직
		semiannualRepository.saveOrg(masterLawTemplate);
		schedulingDAO.deleteLinkOrganizationCurrent();
		schedulingDAO.postLinkOrganizationCurrent();

		// 조직-관련법령별 대상설비 ( TB_ORG_LAW_REL_EQUIP )
		List<OrgLawRelEquip> orgRelEquipList = semiannualRepository.findOrgRelEquip(masterLawTemplate);
		saveOrgRelEquipList(orgRelEquipList);

		// 변수 선언
		Integer lastMstLawId = masterLawTemplate.getLastMstLawId();
		Integer mstLawId = masterLawTemplate.getMstLawId();

		// 법령마스터-이행부서 ( TB_MST_IMPL_ORG )
		List<ImplOrgMap> implOrgList = semiannualRepository.findImplOrgListByMstLawId(lastMstLawId);
		saveImplOrgMap(lawImplList, implOrgList, mstLawId, "implOrg");

		// 법령마스터-주관부서 ( TB_MST_LAW_IMPL_MNG )
		List<ImplOrgMap> implMngList = semiannualRepository.findImplMngList(lastMstLawId);
		saveImplOrgMap(lawImplList, implMngList, mstLawId,"implMng");

		// 법령마스터-관련법령 각조 ( TB_MST_LAW_REL_DTL )
		List<ImplLawDtlMap> implLawDtlMapList = findImplLawDtlMapList(lawImplList);
		saveImplLawDtlMapList(implLawDtlMapList);

		// 의무이행사항 작업 ( TB_IMPL_ITEM )
		List<ImplItem> implItemList = semiannualRepository.findImplItemListByMstLawId(lastMstLawId);
		saveImplItemList(implItemList, lawImplList, mstLawId);

		log.info("[END] SemiannualDataService.proceedBasicMasterInfo: 반기데이터");
	}

	/**
	 * 새로운 반기 마스터정보 빌드 및 저장
	 *
	 * @param masterLawTemplate
	 * @return
	 */
	private MasterLawTemplate saveNewMasterLawTemplate(MasterLawTemplate masterLawTemplate) {
		MasterLawTemplate newMasterLawTemplate = buildNewMasterLawTemplate(masterLawTemplate);
		semiannualRepository.saveMasterLawTemplate(newMasterLawTemplate);
		return newMasterLawTemplate;
	}

	/**
	 * 새로운 반기 마스터정보 객체 빌드
	 *
	 * @param masterLawTemplate
	 */
	private MasterLawTemplate buildNewMasterLawTemplate(MasterLawTemplate masterLawTemplate) {
		Integer lastMstLawId = masterLawTemplate.getLastMstLawId();
		MasterLawTemplate result = MasterLawTemplate.builder()
				.mstLawId(lastMstLawId + 1)
				.lastMstLawId(lastMstLawId)
				.build();

		if ( masterLawTemplate.getOptTimeHalf().equals("S")) {
			result.setOptTimeHalf("F");
			result.setOptTimeYear(String.valueOf(Integer.parseInt(masterLawTemplate.getOptTimeYear()) + 1));
		}else {
			result.setOptTimeHalf("S");
			result.setOptTimeYear(masterLawTemplate.getOptTimeYear());
		}

		return result;
	}

	/**
	 * 새로운 반기 법령마스터 대분류 객체 빌드 및 저장
	 *
	 * @param lawMainList
	 * @return
	 */
	private List<MasterLawMain> saveNewLawMainList(List<MasterLawMain> lawMainList) {
		for (MasterLawMain lawMain : lawMainList) {
			lawMain.setLawMainId(semiannualRepository.saveNewLawMain(lawMain));
		}
		return lawMainList;
	}

	/**
	 * 법령마스터 소분류 조회 - 반복문 실행 및 lawMainId로 구분된 리스트 반환
	 *
	 * @param masterLawMainList
	 * @return
	 */
	private List<MasterLawSub> findLawSubListByMainId(List<MasterLawMain> masterLawMainList) {
		List<MasterLawSub> result = new ArrayList();
		for (MasterLawMain lawMain : masterLawMainList) {
			List<MasterLawSub> lawSubList = semiannualRepository.findLawSubListByLawMainId(lawMain);
			for (MasterLawSub lawSub : lawSubList) {
				result.add(lawSub);
			}
		}
		return result;
	}

	/**
	 * 법령마스터 소분류 저장- lawSubId를 반환받아 설정 후 리턴한다.
	 *
	 * @param lawSubList
	 * @return
	 */
	private List<MasterLawSub> saveNewLawSubList(List<MasterLawSub> lawSubList) {
		for (MasterLawSub lawSub : lawSubList) {
			lawSub.setLawSubId(semiannualRepository.saveNewLawSub(lawSub));
		}
		return lawSubList;
	}

	/**
	 * 법령내용 조회
	 *  1. MasterLawSub 리스트 객체에서 lasLawSubId 기준으로 조회
	 *  2. 조회된 MasterLawText 리스트 결과 리스트 객체에 담아 반환
	 *
	 * @param lawSubList
	 * @return
	 */
	private List<MasterLawText> findLawTextList(List<MasterLawSub> lawSubList) {
		List<MasterLawText> result = new ArrayList<>();
		for (MasterLawSub lawSub : lawSubList) {
			List<MasterLawText> lawTextList = semiannualRepository.findLawTextListBySubId(lawSub);
			for (MasterLawText lawText : lawTextList) {
				result.add(lawText);
			}
		}
		return result;
	}

	/**
	 * 법령내용 저장 - lawCttsId를 반환받아 설정 후 리턴한다.
	 *
	 * @param lawTextList
	 * @return
	 */
	private List<MasterLawText> saveLawTextList(List<MasterLawText> lawTextList) {
		for (MasterLawText masterLawText : lawTextList) {
			masterLawText.setLawCttsId(semiannualRepository.saveLawText(masterLawText));
		}
		return lawTextList;
	}

	/**
	 * 이행정보 조회
	 *  1. MasterLawText 리스트 객체에서 lastLawCttsId 기준으로 조회
	 *  2. 조회된 MasterLawImpl 리스트 결과 리스트 객체에 담아 반환
	 *
	 * @param newLawTextList
	 * @return
	 */
	private List<MasterLawImpl> findLawImplList(List<MasterLawText> newLawTextList) {
		List<MasterLawImpl> result = new ArrayList();
		for (MasterLawText masterLawText : newLawTextList) {
			List<MasterLawImpl> lawImplListByLawText = semiannualRepository.findLawImplListByLawText(masterLawText);
			for (MasterLawImpl lawImpl : lawImplListByLawText) {
				result.add(lawImpl);
			}
		}
		return result;
	}

	/**
	 * 이행정보 저장 - lawImplId 반환받아 설정 후 리턴한다.
	 *
	 * @param lawImplList
	 * @return
	 */
	private List<MasterLawImpl> saveLawImplList(List<MasterLawImpl> lawImplList) {
		for (MasterLawImpl lawImpl : lawImplList) {
			Integer lawImplId = semiannualRepository.saveLawImpl(lawImpl);
			lawImpl.setLawImplId(lawImplId);
		}
		return lawImplList;
	}

	/**
	 * '조직-관련법령별 대상설비' 목록 저장
	 *
	 * @param orgRelEquipList
	 */
	private void saveOrgRelEquipList(List<OrgLawRelEquip> orgRelEquipList) {
		for (OrgLawRelEquip orgLawRelEquip : orgRelEquipList) {
			semiannualRepository.saveOrgRelEquip(orgLawRelEquip);
		}
	}

	/**
	 * 법령마스터-조직 매핑 정보 생성 (이행부서, 주관부서)
	 *  - lastLawImplId가 같을 경우 새로운 lawImplId 로 설정 후 insert
	 *  - 작업 끝난 객체 제거 ( 속도 )
	 *
	 * @param lawImplList
	 * @param implMapList
	 * @param mstLawId
	 * @param sort
	 */
	private void saveImplOrgMap(List<MasterLawImpl> lawImplList, List<ImplOrgMap> implMapList, Integer mstLawId, String sort) {
		int size = implMapList.size();

		for (MasterLawImpl lawImpl : lawImplList) {
			int idx = 0;
			while(idx < size) {
				ImplOrgMap implMap = implMapList.get(idx);
				Integer implLastId = lawImpl.getLastLawImplId();
				Integer mapLastId = implMap.getLastLawImplId();

				if ( implLastId.equals(mapLastId)) {
					implMap.setMstLawId(mstLawId);
					implMap.setLawImplId(lawImpl.getLawImplId());

					// 이행, 주관 부서 분기
					if (sort.equals("implOrg")) semiannualRepository.saveImplOrg(implMap);
					if (sort.equals("implMng")) semiannualRepository.saveImplMng(implMap);

					implMapList.remove(implMap);
					size -= 1;
				}else if ( implLastId > mapLastId) {
					idx++;
				}else  { //( implLastId < mapLastId)
					break;
				}
			}
		}
	}

	/**
	 * '법령마스터-관련법령 각조' 조회
	 *  - lastLawImplId로 조회
	 *  - lawImplId는 쿼리에서 삽입
	 *
	 * @param lawImplList
	 * @return
	 */
	private List<ImplLawDtlMap> findImplLawDtlMapList(List<MasterLawImpl> lawImplList) {
		List<ImplLawDtlMap> result = new ArrayList();
		for (MasterLawImpl lawImpl : lawImplList) {
			List<ImplLawDtlMap> mapList= semiannualRepository.findImplLawDtlMapByLawImplId(lawImpl);
			for (ImplLawDtlMap implLawDtlMap : mapList) {
				result.add(implLawDtlMap);
			}
		}
		return result;
	}

	/**
	 * '법령마스터-관련법령 각조' 리스트 저장
	 *
	 * @param implLawDtlMapList
	 */
	private void saveImplLawDtlMapList(List<ImplLawDtlMap> implLawDtlMapList) {
		for (ImplLawDtlMap implLawDtlMap : implLawDtlMapList) {
			semiannualRepository.saveImplLawDtlMap(implLawDtlMap);
		}
	}

	/**
	 * '의무이행사항 작업'저장
	 *  - lawImplList 정렬
	 *  - 파일id생성 후 필드에 담아 저장
	 *  - 작업 끝난 객체 제거 ( 속도 )
	 *
	 * @paramimplItemList
	 */
	private void saveImplItemList(List<ImplItem> itemList, List<MasterLawImpl> implList, Integer mstLawId) {
		Collections.sort(implList);

		int itemListSize = itemList.size();
		for (MasterLawImpl impl : implList) {
			Integer implLastLawImplId = impl.getLastLawImplId();

			int idx = 0;
			while (idx < itemListSize) {
				ImplItem item = itemList.get(0);
				Integer itemLastLawImplId = item.getLastLawImplId();

				if ( itemLastLawImplId.equals(implLastLawImplId) ) {
					item.setMstLawId(mstLawId);
					item.setImplFileId(fileDAO.createFileId());
					item.setPointOutFileId(fileDAO.createFileId());
					item.setLawImplId(impl.getLawImplId());

					semiannualRepository.saveImplItem(item);

					itemList.remove(item);
					itemListSize -= 1;
				}else if ( implLastLawImplId > itemLastLawImplId ) {
					idx++;
				}else {  // ( implLastId < itemLastId )
					break;
				}
			}
		}
	}
}
