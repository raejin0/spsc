package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistGetResponse {
    @ApiModelProperty(example = "연도")
    private String optTimeYear;
    @ApiModelProperty(example = "반기")
    private String optTimeHalf;
    private ChecklistProgressOnDepartment progressOnDepartment;
    private List<ChecklistLawMainList> lawMainImplementsList = new ArrayList<>();
}
