package net.miraeit.cmm.model.file;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.miraeit.cmm.model.law.LawDetail;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LawDownloadRequest {

	@NotNull(message = "V000")
	@ApiModelProperty(example = "관련법령명")
	private String lawRelNm;

	private List<LawDetail> lawDetails;
}
