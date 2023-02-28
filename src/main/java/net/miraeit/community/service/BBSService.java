package net.miraeit.community.service;

import egovframework.rte.fdl.property.EgovPropertyService;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.condition.FileDetailConditions;
import net.miraeit.cmm.model.file.UploadFileResponse;
import net.miraeit.cmm.service.FileService;
import net.miraeit.community.condition.FindPostCondition;
import net.miraeit.community.dao.BBSDAO;
import net.miraeit.community.exception.DifferentUserException;
import net.miraeit.community.exception.NoPostFoundException;
import net.miraeit.community.model.*;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.user.dao.UserDAO;
import net.miraeit.user.model.LoginRequest;
import net.miraeit.user.model.LoginVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class BBSService {
	private final BBSDAO BBSDAO;
	private final FileDAO fileDAO;
	private final FileService fileService;
	private final UserDAO userDAO;

	@Resource(name = "propertiesService")
    public EgovPropertyService propertiesService;

	/**
	 * 게시물 목록과 파일정보를 조회하고, 페이징정보를 포함한 결과를 반환한다.
	 *
	 * @return
	 */
	public PagedList<FindPostResponse> findPosts(FindPostListRequest request) {
		PagedList<FindPostResponse> posts = BBSDAO.findPosts(request);
		Iterator<FindPostResponse> iterator = posts.getList().iterator();
		while(iterator.hasNext()) {
			FindPostResponse response = iterator.next();
			if( response.getAtchFileId() != null)
				findAndSetFileDetails(response);
		}
		return posts;
	}

	/**
	 * 게시물 및 파일 정보를 조회한다.
	 *
	 * @param condition
	 * @return
	 */
	public FindPostResponse findPost(FindPostCondition condition) {
	   // LETTNBBS(게시글) 테이블에서 정보 조회 및 예외 처리
	   FindPostResponse findPostResponse = BBSDAO.findPost(condition);
	   if (findPostResponse == null) throw new NoPostFoundException();

	   if ( findPostResponse.getAtchFileId() != null)
			findAndSetFileDetails(findPostResponse);
	   return findPostResponse;
	}

	/**
	 * 게시물을 저장한다.
	 *
	 * @param createPostRequest
	 * @param userId
	 * @return
	 */
	public Integer savePost(CreatePostRequest createPostRequest, String userId) {
	   // atchFileId, bbsId, 등록자 id 추가한 DTO 객체 생성
	   CreatePostDTO createPostDTO = CreatePostDTO.builder()
	         .bbsId(createPostRequest.getBbsId())
	         .nttSj(createPostRequest.getNttSj())
	         .nttCn(createPostRequest.getNttCn())
	         .atchFileId(createPostRequest.getAtchFileId())
	         .createdBy(userId)
	         .build();

	   return BBSDAO.savePost(createPostDTO);
	}

	public Integer savePost(CreatePostRequest createPostRequest, String userId, MultipartFile file) throws IOException {
		if (file != null)
			createPostRequest.setAtchFileId(fileService.saveFileDetail(file));
		else
			createPostRequest.setAtchFileId(fileDAO.createFileId());
		return savePost(createPostRequest, userId);
	}

	/**
	 * 쿼리용 DTO로 변환하여 DAO 메소드를 호출한다.
	 *
	 * @param request
	 * @param userId
	 */
	public void updatePost(UpdatePostRequest request, String userId) {

		FindPostResponse post = BBSDAO.findPost(new FindPostCondition(request.getBbsId(), request.getNttId())); // 목록 조회
		LoginVO loginVO = userDAO.findUser(new LoginRequest(userId));

		// 예외처리
		if (post == null) throw new NoPostFoundException(); // 존재하지 않는 게시물
		if (!userId.equals(post.getCreatedBy()) && loginVO.getAuthGroupId() != 1) // 등록자도 아니고 관리자도 아님
			throw new DifferentUserException();

		BBSDAO.updatePost(new UpdatePostDTO(request, userId));
	}

	public void updatePost(UpdatePostRequest updatePostRequest, String userId, MultipartFile file) throws IOException {
		if(updatePostRequest.isDoDeleteFile()) fileDAO.deleteFileDetailByFileId(updatePostRequest.getAtchFileId());
		if(file!=null) {
			if(updatePostRequest.getAtchFileId()==null || updatePostRequest.getAtchFileId()==0) {
				updatePostRequest.setAtchFileId(fileService.saveFileDetail(file));
			} else {
				fileDAO.deleteFileDetailByFileId(updatePostRequest.getAtchFileId());
				fileService.saveFileDetail(file, updatePostRequest.getAtchFileId());
			}
		}
		updatePost(updatePostRequest, userId);
	}

	/**
	 * 파일 정보를 조회하고 response 객체에 저장한다.
	 *
	 * @param response
	 */
	private void findAndSetFileDetails(FindPostResponse response) {
		FileDetailConditions fileDetailConditions = new FileDetailConditions(response.getAtchFileId()); // 조회용 parameter 객체 생성
		List<UploadFileResponse> files = fileDAO.findFileDetails(fileDetailConditions); // 조회
		response.setUploadFiles(files);
	}

	public void deletePost(DeletePostRequest request, String userId) {
		FindPostResponse post = BBSDAO.findPost(new FindPostCondition(request.getBbsId(), request.getNttId())); // 조회
		LoginVO loginVO = userDAO.findUser(new LoginRequest(userId));

		// 예외 처리
		if (post == null) throw new NoPostFoundException(); // 존재하지 않는 게시물
		if (!userId.equals(post.getCreatedBy()) && loginVO.getAuthGroupId() != 1) // 등록자도 아니고 관리자도 아님
			throw new DifferentUserException();

		BBSDAO.deletePost(request);
	}
}
