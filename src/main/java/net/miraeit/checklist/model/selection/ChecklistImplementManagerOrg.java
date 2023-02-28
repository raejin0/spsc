package net.miraeit.checklist.model.selection;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.checklist.model.ChecklistMainDepartmentsList;

@Getter @Setter
public class ChecklistImplementManagerOrg {
	private int lawImplId;
	private int mstLawId;
	private String parentOrgCd;
	private String mngOrgCd;
	private String mngOrgNm;
	private String fullOrgNm;

	public ChecklistMainDepartmentsList getChecklistMainDepartmentsList() {
		ChecklistMainDepartmentsList retVal = new ChecklistMainDepartmentsList();
		retVal.setOrgCd(mngOrgCd);
		retVal.setOrgNm(mngOrgNm);
		retVal.setFullOrgNm(fullOrgNm);
		retVal.setParentOrgCd(parentOrgCd);
		retVal.setLawImplId(lawImplId);
		return retVal;
	}
}
