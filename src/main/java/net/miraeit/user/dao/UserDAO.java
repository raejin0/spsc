package net.miraeit.user.dao;

import net.miraeit.cmm.dao.BaseDAO;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.user.model.*;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAO extends BaseDAO {

	/**
	 * 사용자 정보를 조회한다.
	 *
	 * @param loginRequest
	 * @return
	 */
	public LoginVO findUser(LoginRequest loginRequest) {
		return (LoginVO) select("spsc.user.select.user", loginRequest);
	}

	/**
	 * 사용자 정보를 저장한다.
	 *
	 * @param request
	 */
	public void saveUser(CreateUserRequest request) {
		insert("spsc.user.insert.user", request);
	}

	/**
	 * 사용자 정보를 갱신한다.
	 *
	 * @param request
	 */
	public void updateUser(UpdateUserRequest request) {
		update("spsc.user.update.user", request);
	}

	/**
	 * 사용자 정보를 삭제한다.
	 *
	 * @param request
	 */
	public void deleteUser(DeleteUserRequest request) {
		delete("spsc.user.delete.user", request);
	}

	/**
	 * 사용자 목록 및 개수를 조회한다.
	 *
	 * @param request
	 */
	public PagedList<FindUserListResponse> findUsers(FindUserListRequest request) {
		return selectPagedList(
				"spsc.user.select.user.list.count",
				"spsc.user.select.user.list",
				request
		);
	}
}
