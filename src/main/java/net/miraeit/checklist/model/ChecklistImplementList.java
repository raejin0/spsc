package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistImplementList {
    private ChecklistContent content;
    private List<ChecklistResultList> resultList;

    public void addChecklistResult(ChecklistResultList result) {
        if(resultList==null) resultList = new ArrayList<>();
        resultList.add(result);
    }
}
