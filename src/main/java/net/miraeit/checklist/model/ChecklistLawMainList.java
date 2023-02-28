package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChecklistLawMainList {
    private ChecklistLawMain lawMain;
    private List<ChecklistLawSubList> lawSubImplementsList;
}
