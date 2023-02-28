package net.miraeit.cmm.model.organization;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationGetResponse {
    @ApiModelProperty(example = "조직 아이디")
    private String orgCd;
    @ApiModelProperty(example = "조직 명")
    private String orgNm;
    @ApiModelProperty(example = "전체 조직 명")
    private String fullOrgNm;
    @ApiModelProperty(example = "조직 타입 - 1:불가 3:가능")
    private Integer orgType;
    @ApiModelProperty(example = "주관부서 Y/N")
    private String mngYn;
    @ApiModelProperty(example = "반기 템플릿 아이디")
    private Integer mstLawId;
    @ApiModelProperty(example = "상위조직 코드")
    private String parentOrgCd;
    @ApiModelProperty(example = "조직 순서 코드")
    private String orgOrderCd;
    @ApiModelProperty(example = "조직 순서 코드(직제서열 코드)")
    private String orgRank;
    @ApiModelProperty(example = "하위 조직")
    private List<Object> subOrganization = new ArrayList<>();
    @ApiModelProperty(example = "조직 리스트")
    private List<OrganizationGetResponse> orgList = new ArrayList<>();
}
