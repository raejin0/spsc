package net.miraeit.checklist.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import net.miraeit.cmm.model.file.FileDetailDTO;

import java.util.Objects;

@Getter
@Setter
public class ChecklistResultList {
    @ApiModelProperty(example = "의무 이행사항 아이디")
    private Integer itemId;
    @ApiModelProperty(example = "이행상태 N:선택하지 않음, X:불이행, V:이행")
    private String implState;
    @ApiModelProperty(example = "불이행 사유")
    private String unimplReason;
    @ApiModelProperty(example = "주관부서/점검사항")
    private String inspect;
    @ApiModelProperty(example = "주관부서/점검사항 상태 I:개선, G:양호")
    private String inspectState;
    @ApiModelProperty(example = "이행증빙 파일 아이디")
    private Integer implFileId;
    @ApiModelProperty(example = "지적사항 증빙 파일 아이디")
    private Integer pointOutFileId;
    @ApiModelProperty(example = "법령 이행 ID")
    private Integer lawImplId;
    @ApiModelProperty(example = "조직 코드")
    private String orgCd;
    private ChecklistDepartment department;
    private ChecklistAttachments attachments = new ChecklistAttachments();

    public void addAttachment(FileDetailDTO fileDetailDTO) {
        if(attachments==null) attachments = new ChecklistAttachments();
        if(Objects.equals(fileDetailDTO.getAtchFileId(), implFileId))
            attachments.addImplAttachment(fileDetailDTO);
        if(Objects.equals(fileDetailDTO.getAtchFileId(), pointOutFileId))
            attachments.addPointedAttachment(fileDetailDTO);
    }
}
