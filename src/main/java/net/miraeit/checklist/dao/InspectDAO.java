package net.miraeit.checklist.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.checklist.model.inspect.DeleteInspectContentRequest;
import net.miraeit.checklist.model.inspect.PostInspectContentCondition;
import org.springframework.stereotype.Repository;

@Repository
public class InspectDAO extends EgovAbstractDAO {

	/**
	 * 점검내용 저장
	 *
	 * @param condition
	 * @return
	 */
	public Integer saveInspectContent(PostInspectContentCondition condition) {
		return (Integer) insert("spsc.checklist.inspect.insert.content", condition);
	}

	/**
	 * 점검내용 삭제
	 *
	 * @param request
	 */
	public void deleteInspectContent(DeleteInspectContentRequest request) {
		delete("spsc.checklist.inspect.delete.content", request);
	}
}
