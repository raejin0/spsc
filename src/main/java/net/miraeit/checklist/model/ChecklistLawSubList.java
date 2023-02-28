package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChecklistLawSubList {
    private ChecklistLawSub lawSub;
    private List<ChecklistStatuteImplementsList> statuteImplementsList;
}
