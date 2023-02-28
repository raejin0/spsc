package net.miraeit.checklist.model.selection;

import lombok.Getter;
import lombok.Setter;
import net.miraeit.checklist.model.ChecklistEquipments;

@Getter @Setter
public class ChecklistImplementEquipment {
    private int equipId;
    private String equipNm;
    private int lawRelId;
    private String orgCd;
    private int lawMstId;

    public ChecklistEquipments getChecklistEquipment() {
        ChecklistEquipments retVal = new ChecklistEquipments();
        retVal.setEquipId(equipId);
        retVal.setEquipNm(equipNm);
        return retVal;
    }
}
