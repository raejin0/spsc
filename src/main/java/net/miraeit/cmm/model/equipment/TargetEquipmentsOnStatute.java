package net.miraeit.cmm.model.equipment;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetEquipmentsOnStatute {
	private TargetStatute statute;
	private List<TargetEquipments> equipmentsList;

	public void addTargetEquipments(TargetEquipments equipments) {
		if(equipmentsList==null) equipmentsList = new ArrayList<>();
		equipmentsList.add(equipments);
	}
}
