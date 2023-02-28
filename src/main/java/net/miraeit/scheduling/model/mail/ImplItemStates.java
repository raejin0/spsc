package net.miraeit.scheduling.model.mail;

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
public class ImplItemStates {
	private int v;  // 이행
	private int x;  // 미이행
	private int n;  // 해당없음
}
