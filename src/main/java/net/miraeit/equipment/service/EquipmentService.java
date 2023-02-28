package net.miraeit.equipment.service;

import lombok.RequiredArgsConstructor;
import net.miraeit.equipment.dao.EquipmentDAO;
import net.miraeit.equipment.model.PostEquipmentRequest;
import net.miraeit.equipment.model.PostEquipmentResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {

	private final EquipmentDAO equipmentDAO;

	public PostEquipmentResponse saveEquipment(PostEquipmentRequest postEquipmentRequest) {
		Integer equipId = equipmentDAO.saveEquipment(postEquipmentRequest);

		return PostEquipmentResponse.builder()
				.equipId(equipId)
				.build();
	}

	public int getEquipmentCountByEquipNm(String equipNm) {
		return equipmentDAO.selectEquipmentCountByEquipNm(equipNm);
	}
}
