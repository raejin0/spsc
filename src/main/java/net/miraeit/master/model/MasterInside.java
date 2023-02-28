package net.miraeit.master.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MasterInside {
    private String optTimeHalf;
    private String optTimeYear;
    private Integer mstLawId;
    private Integer lawSubId;
    private Integer lawMainNo;
    private Integer lawRelDtlId;
    private Integer lawImplId;
    private String mngOrgCd;
    private String orgCd;
    private Integer lawMainId;
    private Integer copyLawMainId;
    private Integer copyLawSubId;
    private Integer lawCttsId;
    private Integer copyLawCttsId;
    private Integer copyLawImplId;
    private Integer lawSubSort;
    private Integer lawCttsSort;
    private Integer lawImplSort;
    private String disClaCode;
    private Integer atchFileId;
    private Integer implFileId;
    private Integer pointOutFileId;
    private Integer itemId;
}
