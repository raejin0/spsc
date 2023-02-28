package net.miraeit.cmm.model.equipment;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TargetEquipments {
	private TargetStatute statute;
	private TargetDepartment department;
	private List<TargetEquipment> equipments;

	public void addTargetEquipment(TargetEquipment targetEquipment) {
		if(equipments==null) equipments = new ArrayList<>();
		equipments.add(targetEquipment);
	}
}
