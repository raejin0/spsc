package net.miraeit.cmm.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import net.miraeit.fx.listing.PagedListValuesGenerator;
import net.miraeit.fx.listing.model.CommonPagingConditions;
import net.miraeit.fx.listing.model.PagedList;

import java.util.List;

public class BaseDAO extends EgovAbstractDAO {

	@SuppressWarnings("unchecked")
	public <T> T selectOne(String queryId) {
		return (T)select(queryId);
	}

	@SuppressWarnings("unchecked")
	public <T> T selectOne(String queryId, Object conditions) {
		return (T)select(queryId, conditions);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> selectList(String queryId) {
		return (List<T>)list(queryId);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> selectList(String queryId, Object conditions) {
		return (List<T>)list(queryId, conditions);
	}

	public <T> PagedList<T> selectPagedList(String countQueryId, String listQueryId, CommonPagingConditions conditions) {
		long count = ((Number)selectOne(countQueryId, conditions)).longValue();
		PagedList<T> pagedList = PagedListValuesGenerator.getPagedListVarValues(conditions, count);
		List<T> list = selectList(listQueryId, conditions);
		pagedList.setList(list);
		return pagedList;
	}
}
