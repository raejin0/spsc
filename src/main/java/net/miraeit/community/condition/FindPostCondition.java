package net.miraeit.community.condition;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPostCondition {
	private Integer bbsId;
	private Integer nttId;
}
