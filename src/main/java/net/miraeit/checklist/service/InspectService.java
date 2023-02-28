package net.miraeit.checklist.service;

import lombok.RequiredArgsConstructor;
import net.miraeit.checklist.dao.InspectDAO;
import net.miraeit.checklist.model.inspect.PostInspectContentCondition;
import net.miraeit.checklist.model.inspect.PostInspectContentRequest;
import net.miraeit.checklist.model.inspect.PostInspectContentResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InspectService {

	private final InspectDAO inspectDAO;

	/**
	 * 저장용 condition 객체 생성 후, 점검 내용 추가
	 *
	 * @param request
	 * @return
	 */
	public PostInspectContentResponse postInspectContent(PostInspectContentRequest request) {
		Integer inspectId = inspectDAO.saveInspectContent(new PostInspectContentCondition(request));
		return new PostInspectContentResponse(inspectId);
	}
}
