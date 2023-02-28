package net.miraeit.community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.miraeit.fx.listing.model.CommonPagingConditions;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FindPostListRequest extends CommonPagingConditions {
	private String searchCnd;
	private String searchWrd;
	private Integer bbsId;
	private String beginRegDt;
	private String endRegDt;
}
