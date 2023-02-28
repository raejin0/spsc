package net.miraeit.pointout.model.excel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelBaseModel {
	private List<Attachment> attachments;   // 첨부파일 리스트
	private Integer rowNumber;              // 행번호 for image
	private Integer colNumber;              // 열번호 for image

	/* MasterLawTemplate */
	private Integer	mstLawId;	    // 마스터법령ID
	private String	optTimeYear;	// 적용시점-년도
	private String	optTimeHalf;	// 적용시점-상/하반기 구분

	private Integer lastMstLawId;    // 지난 반기 - 마스터법령ID
}
