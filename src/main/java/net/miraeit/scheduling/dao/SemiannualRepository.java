package net.miraeit.scheduling.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.scheduling.model.semiannual.*;
import net.miraeit.scheduling.model.semiannual.map.ImplItem;
import net.miraeit.scheduling.model.semiannual.map.ImplLawDtlMap;
import net.miraeit.scheduling.model.semiannual.map.ImplOrgMap;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 반기 데이터 처리용 Repository
 */
@Repository
public class SemiannualRepository extends EgovAbstractDAO {
	/**
	 * 최근 반기 마스터정보 조회
	 *
	 * @return
	 */
	public MasterLawTemplate findLastMasterLawTemplate() {
		return (MasterLawTemplate) select("spsc.scheduling.semiannual.select.last.master.law.template");
	}

	/**
	 * 반기 마스터 정보 저장
	 *
	 * @param masterLawTemplate
	 */
	public void saveMasterLawTemplate(MasterLawTemplate masterLawTemplate) {
		insert("spsc.scheduling.semiannual.insert.master.law.template", masterLawTemplate);
	}

	/**
	 * lastMstLawId로 법령마스터-대분류 조회
	 * 새로운 mstLawId는 쿼리에서 넣는다.
	 *
	 * @param masterLawTemplate
	 * @return
	 */
	public List<MasterLawMain> findLawMainListByMstLawId(MasterLawTemplate masterLawTemplate) {
		return (List<MasterLawMain>) list("spsc.scheduling.semiannual.select.law.main.by.mst.law.id", masterLawTemplate);
	}

	/**
	 * 법령마스터 대분류 저장
	 *
	 * @param masterLawMain
	 * @return
	 */
	public Integer saveNewLawMain(MasterLawMain masterLawMain) {
		return (Integer) insert("spsc.scheduling.semiannual.insert.law.main", masterLawMain);
	}

	/**
	 * lastLawMainId로 법령마스터 소분류 조회,
	 * 새로운 lawMainId로 쿼리에서 넣는다.
	 *
	 * @param lawMain
	 * @return
	 */
	public List<MasterLawSub> findLawSubListByLawMainId(MasterLawMain lawMain) {
		return (List<MasterLawSub>) list("spsc.scheduling.semiannual.select.law.sub.by.law.main.id", lawMain);
	}

	/**
	 * 법령마스터 소분류 저장
	 *
	 * @param lawSub
	 * @return
	 */
	public Integer saveNewLawSub(MasterLawSub lawSub) {
		return (Integer) insert("spsc.scheduling.semiannual.insert.law.sub", lawSub);
	}

	/**
	 * lawSubId로 법령내용 리스트 조회
	 * 새로운 lawSubIds는 쿼리에서 넣는다.
	 *
	 * @param mastLawSub
	 * @return
	 */
	public List<MasterLawText> findLawTextListBySubId(MasterLawSub mastLawSub) {
		return (List<MasterLawText>) list("spsc.scheduling.semiannual.select.law.text.by.sub.id", mastLawSub);
	}

	/**
	 * 법령정보 저장
	 *
	 * @param masterLawText
	 * @return
	 */
	public Integer saveLawText(MasterLawText masterLawText) {
		return (Integer) insert("spsc.scheduling.semiannual.insert.law.text", masterLawText);
	}

	/**
	 * lastLawCttsId로 이행정보 조회
	 * 새로운 lawCttsId는 쿼리에서 넣는다.
	 *
	 * @param masterLawText
	 * @return
	 */
	public List<MasterLawImpl> findLawImplListByLawText(MasterLawText masterLawText) {
		return (List<MasterLawImpl>) list("spsc.scheduling.semiannual.select.law.impl.by.law.text", masterLawText);
	}

	/**
	 * 이행정보 저장
	 *
	 * @param masterLawImpl
	 * @return
	 */
	public Integer saveLawImpl(MasterLawImpl masterLawImpl) {
		return (Integer) insert("spsc.scheduling.semiannual.insert.law.impl", masterLawImpl);
	}

	/**
	 * lastMstLawId로 '조직 관련법령별 대상설비' 조회
	 * 새로운 mstLawId 쿼리에서 넣는다.
	 *
	 * @param masterLawTemplate
	 */
	public List<OrgLawRelEquip> findOrgRelEquip(MasterLawTemplate masterLawTemplate) {
		return (List<OrgLawRelEquip>) list("spsc.scheduling.semiannual.select.org.rel.equip", masterLawTemplate);
	}

	/**
	 * '조직 관련법령별 대상설비' 저장
	 *
	 * @param orgRelEquip
	 */
	public void saveOrgRelEquip(OrgLawRelEquip orgRelEquip) {
		insert("spsc.scheduling.semiannual.insert.org.rel.equip", orgRelEquip);
	}

	/**
	 * '법령마스터-이행부서 매핑' 조회
	 *
	 * @return
	 */
	public List<ImplOrgMap> findImplOrgListByMstLawId(Integer mstLawId) {
		return (List<ImplOrgMap>) list("spsc.scheduling.semiannual.select.impl.org.by.mst.law.id", mstLawId);
	}

	/**
	 * '법령마스터-주관부서 매핑' 저장
	 *
	 * @param implMap
	 */
	public void saveImplOrg(ImplOrgMap implMap) {
		insert("spsc.scheduling.semiannual.insert.impl.org", implMap);
	}

	public List<ImplOrgMap> findImplMngList(Integer mstLawId) {
		return (List<ImplOrgMap>) list("spsc.scheduling.semiannual.select.impl.mng.by.mst.law.id", mstLawId);
	}

	/**
	 * '법령마스터-주관부서 매핑' 저장
	 *
	 * @param implMap
	 */
	public void saveImplMng(ImplOrgMap implMap) {
		insert("spsc.scheduling.semiannual.insert.impl.mng", implMap);
	}

	/**
	 *'법령마스터-관련법령 각조' 조회
	 *  - lastLawImplId로 조회
	 *  - lawImplId는 쿼리에서 삽입
	 *
	 * @param masterLawImpl
	 * @return
	 */
	public List<ImplLawDtlMap> findImplLawDtlMapByLawImplId(MasterLawImpl masterLawImpl) {
		return (List<ImplLawDtlMap>) list("spsc.scheduling.semiannual.find.impl.law.dtl.map", masterLawImpl);
	}

	/**
	 * '법령마스터-관련법령 각조' 저장
	 *
	 * @param implLawDtlMap
	 */
	public void saveImplLawDtlMap(ImplLawDtlMap implLawDtlMap) {
		insert("spsc.scheduling.semiannual.insert.impl.law.dtl.map", implLawDtlMap);
	}

	/**
	 * '의무이행사항 작업' 조회
	 *
	 * @param mstLawId
	 * @return
	 */
	public List<ImplItem> findImplItemListByMstLawId(Integer mstLawId) {
		return (List<ImplItem>) list("spsc.scheduling.semiannual.select.impl.item.list.by.mst.law.id", mstLawId);
	}

	public void saveImplItem(ImplItem implItem) {
		insert ("spsc.scheduling.semiannual.insert.impl.item", implItem);
	}

	public void saveOrg(MasterLawTemplate masterLawTemplate) {
		insert ("spsc.scheduling.semiannual.insert.org", masterLawTemplate);
	}
}
