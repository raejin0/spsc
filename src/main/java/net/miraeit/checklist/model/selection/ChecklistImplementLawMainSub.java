package net.miraeit.checklist.model.selection;

import lombok.Getter;
import lombok.Setter;
import net.miraeit.checklist.model.ChecklistLawMain;
import net.miraeit.checklist.model.ChecklistLawSub;

@Getter @Setter
public class ChecklistImplementLawMainSub {
	private int lawMainId;
	private int lawMainNo;
	private String lawMainNm;
	private int lawSubId;
	private int lawSubNo;
	private String lawSubNm;

	public ChecklistLawMain getChecklistLawMain() {
		ChecklistLawMain retVal = new ChecklistLawMain();
		retVal.setLawMainId(lawMainId);
		retVal.setLawMainNo(lawMainNo);
		retVal.setLawMainNm(lawMainNm);
		return retVal;
	}

	public ChecklistLawSub getChecklistLawSub() {
		ChecklistLawSub retVal = new ChecklistLawSub();
		retVal.setLawSubId(lawSubId);
		retVal.setLawSubNo(lawSubNo);
		retVal.setLawSubNm(lawSubNm);
		retVal.setLawMainId(lawMainId);
		return retVal;
	}
}
