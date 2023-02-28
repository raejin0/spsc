package net.miraeit.master.model.main;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.master.model.text.MasterTextListBySubId;

import java.util.List;

@Getter
@Setter
public class MasterMainSubInfoGetResponse {
    @ApiModelProperty(example = "법령 소분류 ID")
    private Integer lawSubId;
    @ApiModelProperty(example = "법령 소분류 번호")
    private Integer lawSubNo;
    @ApiModelProperty(example = "법령 소분류 이름")
    private String lawSubNm;
    @ApiModelProperty(example = "법령 소분류 비고")
    private String lawSubEtc;
    @ApiModelProperty(example = "법령 소분류 정렬")
    private Integer lawSubSort;
    private List<MasterTextListBySubId> textList;
}
