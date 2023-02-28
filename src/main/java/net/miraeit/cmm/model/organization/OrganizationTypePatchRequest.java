package net.miraeit.cmm.model.organization;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.property.Regex;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class OrganizationTypePatchRequest {
    @NotNull(message = "V000")
    @ApiModelProperty(example = "조직 코드")
    private String orgCd;
    @NotNull(message = "V000")
    @Pattern(regexp = Regex.ORG_TYPE, message = "V008")
    @ApiModelProperty(example = "조직 타입 - 1:불가 3:가능")
    private Integer orgType;
}
