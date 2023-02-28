package net.miraeit.checklist.model.selection;

import lombok.Getter;
import lombok.Setter;
import net.miraeit.checklist.model.ChecklistContent;
import net.miraeit.checklist.model.ChecklistResultList;

@Getter
@Setter
public class ChecklistImplementItem {
	private int itemId;
	private String orgCd;
	private int lawImplId;
	private int lawCttsId;
	private String implProd;
	private String text;
	private String itmReqr;
	private String itmAttn;
	private String implState;
	private String inspect;
	private String inspectState;
	private int implFileId;
	private int pointOutFileId;
	private int lawSubId;
	private String unimplReason;

	public ChecklistContent getContent() {
		ChecklistContent content = new ChecklistContent();
		content.setLawImplId(lawImplId);
		content.setText(text);
		content.setItmReqr(itmReqr);
		content.setItmAttn(itmAttn);
		content.setImplProd(implProd);
		content.setLawSubId(lawSubId);
		content.setUnimplReason(unimplReason);
		return content;
	}

	public ChecklistResultList getResult() {
		ChecklistResultList result = new ChecklistResultList();
		result.setItemId(itemId);
		result.setImplState(implState);
		result.setInspect(inspect);
		result.setInspectState(inspectState);
		result.setImplFileId(implFileId);
		result.setPointOutFileId(pointOutFileId);
		result.setOrgCd(orgCd);
		result.setLawImplId(lawImplId);
		result.setUnimplReason(unimplReason);
		return result;
	}
}
