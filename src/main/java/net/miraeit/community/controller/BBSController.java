package net.miraeit.community.controller;

import egovframework.rte.fdl.property.EgovPropertyService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.dao.FileDAO;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.cmm.util.FileStore;
import net.miraeit.community.condition.FindPostCondition;
import net.miraeit.community.dao.BBSDAO;
import net.miraeit.community.model.*;
import net.miraeit.community.service.BBSService;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.user.model.LoginVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Api(tags = {"게시물 API를 제공하는 Controller"})
@RequestMapping("/api/bbs")
@RestController
@RequiredArgsConstructor
public class BBSController {
	private final BBSService bbsService;
	private final BBSDAO bbsdao;
	private final FileDAO fileDAO;
	private final FileStore fileStore;
	private final EgovPropertyService propertiesService;

	// 검색 조회 post 방식
	/*@PostMapping("/{bbsId}/posts")
	@ApiOperation(value = "게시물 목록 조회", notes ="검색 조건에 따른 게시물 목록을 조회한다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bbsId", value = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실)"),
			@ApiImplicitParam(name = "searchCnd", value = "검색주제(제목, 내용)"),
			@ApiImplicitParam(name = "searchWrd", value = "검색어"),
			@ApiImplicitParam(name = "currentPageNo" , value = "페이지번호"),
			@ApiImplicitParam(name = "fetchCount" , value = "항목 수")
	})
	public SuccessResponse<PagedList<FindPostResponse>> getPosts(@PathVariable Integer bbsId, @RequestBody FindPostListRequest request){
		request.setBbsId(bbsId);
		return new SuccessResponse(bbsService.findPosts(request)); // 조회 및 결과 반환
	}*/

	// 검색 조회 get 방식
	@GetMapping("/{bbsId}/posts")
	@ApiOperation(value = "게시물 목록 조회", notes ="검색 조건에 따른 게시물 목록을 조회한다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bbsId", value = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실)"),
			@ApiImplicitParam(name = "searchCnd", value = "검색주제(제목, 내용)"),
			@ApiImplicitParam(name = "searchWrd", value = "검색어"),
			@ApiImplicitParam(name = "currentPageNo" , value = "페이지번호"),
			@ApiImplicitParam(name = "fetchCount" , value = "항목 수")
	})

	public SuccessResponse<PagedList<FindPostResponse>> getPosts(@PathVariable Integer bbsId, @ModelAttribute FindPostListRequest request){
		request.setBbsId(bbsId);
		return new SuccessResponse(bbsService.findPosts(request)); // 조회 및 결과 반환
	}

	@GetMapping("/{bbsId}/{nttId}")
	@ApiOperation(value = "게시물 단건 조회")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "bbsId", value = "게시판 ID ( 1: 공지사항, 2: 게시판, 3: 자료실)"),
			@ApiImplicitParam(name = "nttId", value = "게시글 id")})
	public SuccessResponse<FindPostResponse> getPost(@PathVariable Integer bbsId,
	                                                 @PathVariable Integer nttId) {
		FindPostCondition condition = FindPostCondition.builder()
				.bbsId(bbsId)
				.nttId(nttId)
				.build();

		FindPostResponse findPostResponse = bbsService.findPost(condition);
		return new SuccessResponse(findPostResponse);
	}

	public SuccessResponse<CreatePostResponse> savePost(@Valid @RequestBody CreatePostRequest createPostRequest) throws UnsupportedEncodingException {
		// 로그인 정보 가져오기
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_REQUEST);
		// 게시물 저장 및 게시물 id 받아오기
		Integer nttId = bbsService.savePost(createPostRequest, loginVO.getId());
		return new SuccessResponse(new CreatePostResponse(nttId));
	}

	@PostMapping("/post")
	@ApiOperation(value = "게시물 등록", notes = "게시물 내용과 파일을 저장한다.")
	public SuccessResponse<CreatePostResponse> savePostWithFile(
			@Valid CreatePostRequest createPostRequest, @RequestPart(required=false) MultipartFile file
	) throws IOException {
		// 로그인 정보 가져오기
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_REQUEST);
		// 게시물 저장 및 게시물 id 받아오기
		Integer nttId = bbsService.savePost(createPostRequest, loginVO.getId(), file);
		return new SuccessResponse(new CreatePostResponse(nttId));
	}

	@DeleteMapping("/post")
	@ApiOperation(value = "게시물 삭제", notes = "삭제 목록: 게시물 내용, 업로드 파일, 파일정보, 파일 마스터 ID")
	public SuccessResponse deletePost(@RequestBody DeletePostRequest deletePostRequest) {
		// 로그인 정보 가져오기
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_REQUEST);

		Integer atchFileId = deletePostRequest.getAtchFileId(); // 편의 변수

		if (atchFileId != null) {
			List<String> filePathList = fileDAO.findUploadFilePathList(atchFileId); // 파일명 조회
			fileStore.removeFiles(filePathList);        // 업로드 파일 삭제
			fileDAO.deleteFileDetailByFileId(atchFileId);   // 파일 정보 삭제 (DETAIL, FILE)
			fileDAO.deleteFileById(atchFileId);             // 파일 마스터 정보 삭제
		}

		bbsService.deletePost(deletePostRequest, loginVO.getId());       // 게시글 삭제
		return new SuccessResponse(null);
	}

	public SuccessResponse patchPost(@Valid @RequestBody UpdatePostRequest updatePostRequest) {
		// 로그인 정보 가져오기
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_REQUEST);

		bbsService.updatePost(updatePostRequest, loginVO.getId()); // 로직 처리

		return new SuccessResponse(null);
	}

	@PostMapping("/post-mod")
	@ApiOperation(value = "게시물 수정", notes = "1. 업로드 파일 및 파일정보 삭제 2. 새로운 파일 및 파일정보 저장 3. 게시글 수정 ")
	public SuccessResponse<Void> patchPost(@Valid UpdatePostRequest updatePostRequest, @RequestPart(required = false) MultipartFile file) throws IOException {
		// 로그인 정보 가져오기
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_REQUEST);
		bbsService.updatePost(updatePostRequest, loginVO.getId(), file); // 로직 처리
		return new SuccessResponse(null);
	}

	/*@PatchMapping("/post")
	@ApiOperation(value = "게시물 수정", notes = "1. 업로드 파일 및 파일정보 삭제 2. 새로운 파일 및 파일정보 저장 3. 게시글 수정 ")
	public SuccessResponse<Void> patchPost(@Valid UpdatePostRequest updatePostRequest, @RequestPart(required = false) MultipartFile file) throws IOException {
		// 로그인 정보 가져오기
		LoginVO loginVO = (LoginVO) RequestContextHolder.getRequestAttributes().getAttribute("LoginVO", RequestAttributes.SCOPE_REQUEST);
		bbsService.updatePost(updatePostRequest, loginVO.getId(), file); // 로직 처리
		return new SuccessResponse(null);
	}*/
}
