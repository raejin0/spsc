package net.miraeit.cmm.model.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ManagementPatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "조직 코드")
    private String orgCd;
    @Pattern(regexp = Regex.USE_YN, message = "V009")
    @ApiModelProperty(example = "주관부서 여부")
    private String mngYn;
}
