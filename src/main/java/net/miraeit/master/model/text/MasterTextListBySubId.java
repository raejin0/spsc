package net.miraeit.master.model.text;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.miraeit.master.model.impl.MasterImplListByTextId;

import java.util.List;

@Getter
@Setter
public class MasterTextListBySubId {
    @ApiModelProperty(example = "법령 내용 ID")
    private int lawCttsId;
    @ApiModelProperty(example = "법령 내용")
    private String text;
    @ApiModelProperty(example = "법령 내용 정렬")
    private Integer lawCttsSort;
    private List<MasterImplListByTextId> implList;
}
