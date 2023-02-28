package net.miraeit.scheduling.model.semiannual.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ImplLawDtlMap {
	private Integer lawImplId;      // 법령이행 id
	private Integer lawRelDtlId;    // 관련법령 순번번

	private Integer lastLawImplId;  // 지난 반기 - 법령 id
}
