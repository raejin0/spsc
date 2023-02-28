package net.miraeit.pointout.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PointOutSTS {
	private int mstLawId;
	private String mngOrgCd;
	private String orgCd;
	private String orgNm;
	private String fullOrgNm;
	private int cntPointOut;
	private int cntPointClear;
}
