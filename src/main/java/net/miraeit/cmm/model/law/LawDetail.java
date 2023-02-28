package net.miraeit.cmm.model.law;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LawDetail {

	private Integer lawRelId;
	private String lawRelNm;
	private Integer lawRelDtlId;
	private String lawRelAtc;
	private String lawRelText;

}
