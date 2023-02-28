package net.miraeit.equipment.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.cmm.dao.BaseDAO;
import net.miraeit.equipment.model.*;
import net.miraeit.fx.listing.model.PagedList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EquipmentDAO extends BaseDAO {
	public PagedList<FindEquipmentListResponse> findEquipmentList(FindEquipmentListRequest findEquipmentListRequest) {
		return selectPagedList("spsc.equipment.select.equipment.list.count",
								"spsc.equipment.select.equipment.list",
										findEquipmentListRequest);
	}

	public Integer saveEquipment(PostEquipmentRequest postEquipmentRequest) {
		return (Integer) insert("spsc.equipment.insert.equipment", postEquipmentRequest);
	}

	public void updateEquipment(PatchEquipmentRequest patchEquipmentRequest) {
		update("spsc.equipment.update.equipment", patchEquipmentRequest);
	}

	public void deleteEquipment(DeleteEquipmentRequest deleteEquipmentRequest) {
		delete("spsc.equipment.delete.equipment", deleteEquipmentRequest);
	}

	public int selectEquipmentCountByEquipNm(String equipNm) {
		return selectOne("spsc.equipment.select.equipment.count.by.equip.nm", equipNm);
	}
}
