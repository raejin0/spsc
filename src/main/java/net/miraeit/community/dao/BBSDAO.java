package net.miraeit.community.dao;

import net.miraeit.cmm.dao.BaseDAO;
import net.miraeit.community.condition.FindPostCondition;
import net.miraeit.community.model.*;
import net.miraeit.fx.listing.model.PagedList;
import org.springframework.stereotype.Repository;

@Repository
public class BBSDAO extends BaseDAO {

	/**
	 * 게시물 목록 및 개수를 조회한다.
	 *
	 * @param request
	 * @return
	 */
	public PagedList<FindPostResponse> findPosts(FindPostListRequest request) {
		return selectPagedList(
				"spsc.community.select.post.list.cnt",
				"spsc.community.select.post.list",
				request
		);
	}

	/**
	 * 게시물을 단건 조회한다.
	 *
	 * @param condition
	 * @return
	 */
	public FindPostResponse findPost(FindPostCondition condition) {
	   return (FindPostResponse) select("spsc.community.select.post", condition);
	}

	/**
	 * 게시물을 저장한다.
	 *
	 * @param createPostDTO
	 * @return
	 */
	public Integer savePost(CreatePostDTO createPostDTO) {
		 return (Integer) insert("spsc.community.insert.post", createPostDTO);
	}

	/**
	 * 게시물을 삭제한다.
	 *
	 * @param deletePostRequest
	 */
	public void deletePost(DeletePostRequest deletePostRequest) {
		delete("spsc.community.delete.post", deletePostRequest);
	}

	/**
	 * 게시물 정보를 갱신한다.
	 *
	 * @param updatePostDTO
	 */
	public void updatePost(UpdatePostDTO updatePostDTO) {
		update("spsc.community.update.post", updatePostDTO);
	}
}
