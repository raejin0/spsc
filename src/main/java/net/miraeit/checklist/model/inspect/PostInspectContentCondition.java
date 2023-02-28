package net.miraeit.checklist.model.inspect;

import lombok.*;


@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PostInspectContentCondition {

    private Integer inspectId;  // 점검사항 ID
    private String content;     // 점검내용
    private Integer implItemId; // 이행체크항목 아이디

	public PostInspectContentCondition(PostInspectContentRequest request) {
		this.content = request.getContent();
		this.implItemId = request.getImplItemId();
	}
}
