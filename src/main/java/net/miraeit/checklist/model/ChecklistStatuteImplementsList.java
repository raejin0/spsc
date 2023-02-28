package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistStatuteImplementsList {
    private ChecklistStatute statute;
    private List<ChecklistImplementList> implementList;
    private List<ChecklistEquipments> equipments;

    public void addChecklistImplementList(ChecklistImplementList impl) {
        if(implementList==null) implementList = new ArrayList<>();
        implementList.add(impl);
    }

    public void addChecklistEquipment(ChecklistEquipments equipment) {
        if(equipments==null) equipments = new ArrayList<>();
        equipments.add(equipment);
    }
}
