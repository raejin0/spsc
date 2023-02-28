package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistPerformanceGetResponse {
    private ChecklistProgressOnDepartment progressOnDepartment;
    private List<ChecklistLawSubList> lawSubImplementsList = new ArrayList<>();
}
