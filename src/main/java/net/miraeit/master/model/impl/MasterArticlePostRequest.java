package net.miraeit.master.model.impl;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MasterArticlePostRequest {
	@NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 조항")
    private Integer lawRelAtc;

    @ApiModelProperty(example = "관련법령 조항 하위구분")
    private Integer lawRelAtcSub;

//    @NotEmpty(message = "V010")
	@NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 내용")
    private String lawRelText;

    @NotNull(message = "V000")
    @ApiModelProperty(example = "관련법령 ID")
    private Integer lawRelId;
}
