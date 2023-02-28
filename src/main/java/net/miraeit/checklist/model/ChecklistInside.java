package net.miraeit.checklist.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChecklistInside {
    private List<String> orgList = new ArrayList<>();
    private String implState;
    private Integer stateCount;
    private String optTimeYear;
    private String optTimeHalf;
    private String disClaCode;
    private String mngOrgCd;
    private String orgCd;
    private Integer lawMainId;
    private Integer lawSubId;
    private Integer lawRelDtlId;
    private Integer lawImplId;
    private Integer lawRelId;
    private Integer lawCttsId;
    private Integer itemId;
    private Integer atchFileId;
}
