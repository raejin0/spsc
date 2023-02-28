package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistContent {
    @ApiModelProperty(example = "마스터 법령 이행 아이디")
    private Integer lawImplId;
    @ApiModelProperty(example = "관련법령")
    private String text;
    @ApiModelProperty(example = "의무사항")
    private String itmReqr;
    @ApiModelProperty(example = "착안사항")
    private String itmAttn;
    @ApiModelProperty(example = "불이행 사유")
    private String unimplReason;
    @ApiModelProperty(example = "이행주기")
    private String implProd;
    @ApiModelProperty(example = "법령 소분류 아이디")
    private Integer lawSubId;
    private List<ChecklistMainDepartmentsList> mainDepartmentsList;
    private List<ChecklistStatuteList> statuteList;

    public void addMainDepartment(ChecklistMainDepartmentsList mainDepartment) {
        if(mainDepartmentsList==null) mainDepartmentsList = new ArrayList<>();
        mainDepartmentsList.add(mainDepartment);
    }

    public void addStatute(ChecklistStatuteList statute) {
        if(statuteList==null) statuteList = new ArrayList<>();
        statuteList.add(statute);
    }
}
