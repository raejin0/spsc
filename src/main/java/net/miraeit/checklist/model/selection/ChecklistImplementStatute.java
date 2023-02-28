package net.miraeit.checklist.model.selection;

import lombok.Getter;
import lombok.Setter;
import net.miraeit.checklist.model.ChecklistStatute;
import net.miraeit.checklist.model.ChecklistStatuteList;

@Getter
@Setter
public class ChecklistImplementStatute {
	private int lawImplId;
	private int lawRelId;
	private String lawRelNm;
	private int lawRelDtlId;
	private Integer lawRelAtc;
	private Integer lawRelAtcSub;

	public ChecklistStatuteList getChecklistStatuteList() {
		ChecklistStatuteList retVal = new ChecklistStatuteList();
		retVal.setLawImplId(lawImplId);
		retVal.setLawRelId(lawRelId);
		retVal.setLawRelNm(lawRelNm);
		retVal.setLawRelDtlId(lawRelDtlId);
		retVal.setLawRelAtc(lawRelAtc);
		retVal.setLawRelAtcSub(lawRelAtcSub);
		return retVal;
	}

	public ChecklistStatute getChecklistStatute() {
		ChecklistStatute statute = new ChecklistStatute();
		statute.setLawRelId(lawRelId);
		statute.setLawRelNm(lawRelNm);
		return statute;
	}
}
