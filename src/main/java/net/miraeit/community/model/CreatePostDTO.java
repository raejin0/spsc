package net.miraeit.community.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.miraeit.cmm.model.BaseModel;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDTO extends BaseModel {
	private Integer nttId;
	private Integer bbsId;
	private String nttSj;
	private String nttCn;
	private Integer atchFileId;
}
