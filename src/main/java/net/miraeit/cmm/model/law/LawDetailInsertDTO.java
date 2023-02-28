package net.miraeit.cmm.model.law;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LawDetailInsertDTO extends LawDetailPostRequest {

	private Integer lawRelDtlId;
}
