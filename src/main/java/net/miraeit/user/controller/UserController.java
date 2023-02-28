package net.miraeit.user.controller;


import egovframework.com.cmm.EgovMessageSource;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.authentication.TokenProvider;
import net.miraeit.cmm.model.SuccessResponse;
import net.miraeit.cmm.util.SSOUtil;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.user.dao.UserDAO;
import net.miraeit.user.exception.InvalidRetCodeException;
import net.miraeit.user.exception.NoUserFoundBySsoIdException;
import net.miraeit.user.exception.SSOIdNotFoundException;
import net.miraeit.user.model.*;
import net.miraeit.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = {"사용자 관련 API 를 제공하는 Controller"})
@RequestMapping
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserDAO userDAO;
	private final SSOUtil ssoUtil;
	private final TokenProvider tokenProvider;
	private final EgovMessageSource msgSrc;

	private void systemLog(String message) {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<10; i++) sb.append("==============================================\r\n");
		sb.append(message).append("\r\n");
		for(int i=0; i<10; i++) sb.append("==============================================\r\n");
		System.out.println("sb = " + sb);
	}


	private void systemLog(String format, String... format_arg) {
		String message = String.format(format, format_arg);
		systemLog(message);
	}

/*	@GetMapping("/login")
	@ApiOperation(value = "SSO 로그인 처리")
	public ModelAndView loginPage(HttpServletRequest request, HttpServletResponse response, ModelAndView mv) {
		 //http://nlstest.initech.com:8090/agent/sso/login_exec.jsp : 꼭 도메인으로 호출해야 된다.

		// token 파라미터 있으면 index로 이동 ( index에서 토큰 체크 )
		String tokenParam = request.getParameter("token");
		if (tokenParam != null && !tokenParam.equals("")) {
			// 토큰 정제 (.jsp 제거)
			int tokenLen = tokenParam.length();
			int suffixLen = ".jsp".length();
			String trimmedToken = tokenParam.substring(0, tokenLen - suffixLen);

			// token에서 id 추출
			LoginVO loginVO = tokenProvider.getLoginVO(trimmedToken);
			String id = loginVO.getId();

			loginVO = userService.findUserById(id); // ssoId로 회원정보 조회
			LoginResponse loginResponse = new LoginResponse(loginVO, tokenParam);
			mv.addObject("data", loginResponse);
			mv.setViewName("index");
			return mv;
		}

		// sso 관련 변수
		String ssoId = ssoUtil.getSsoId(request);                                      // SSO ID 수신
		String retCode = ssoUtil.getEamSessionCheckAndAgentValid(request, response);    // 통합인증 세션 체크

		// 테스트용 todo 추후 삭제
		ssoId = "admin";
		retCode = "0";

		// exception handling
		if(ssoId == null || ssoId.equals("")) throw new SSOIdNotFoundException(msgSrc.getMessage("S000"));
		if (!retCode.equals("0")) throw new InvalidRetCodeException(msgSrc.getMessage("S001")); //쿠키 유효성 확인 :0(정상)

		// SSO 정보로 로그인 처리
		LoginVO loginVO = userService.findUserById(ssoId);                                           // ssoId로 회원정보 조회
		if ( loginVO == null) throw new NoUserFoundBySsoIdException(msgSrc.getMessage("S002")); // exception handling ( 조회 결과 없음 )

		String token = tokenProvider.createToken(loginVO); // jwt 토큰 생성
		mv.setViewName("/login?token=" + token);
		return mv;
	}

	*//**
	 * 로그인처리 직후 토큰 유효성 검사를 위해 호출됨
	 *
	 * @param request
	 * @return
	 *//*
	@PostMapping("/api/validate-token")
	@ApiOperation("단순 토큰 체크")
	public SuccessResponse tokenCheck(@RequestBody ValidateTokenRequest request) {
		tokenProvider.validateToken(request.getToken());
		return new SuccessResponse(null);
	}*/

	@GetMapping("/api/sso")
	@ApiOperation(value = "SSO")
	public ResponseEntity<SuccessResponse> sso(HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String tryingTimeMark = sdf.format(new Date());

		// sso 관련 변수
		String ssoId = "";   // SSO ID 수신
		String retCode = ""; // 통합인증 세션 체크

		try {
			// exception handling
			systemLog("[%s] trying to check sso", tryingTimeMark);
			ssoId = ssoUtil.getSsoId(request);                                      // SSO ID 수신
			retCode = ssoUtil.getEamSessionCheckAndAgentValid(request, response);   // 통합인증 세션 체크
			systemLog("[%s] SSO ID: %s, Return Code is [%s]", tryingTimeMark, ssoId, retCode);


			if(StringUtils.isEmpty(ssoId)) throw new SSOIdNotFoundException(msgSrc.getMessage("S000"));
			if (!"0".equals(retCode)) throw new InvalidRetCodeException(msgSrc.getMessage("S001")); //쿠키 유효성 확인 :0(정상)

			// SSO 정보로 로그인 처리
			LoginVO loginVO = userService.findUserById(ssoId);                                           // ssoId로 회원정보 조회
			if ( loginVO == null) throw new NoUserFoundBySsoIdException(msgSrc.getMessage("S002")); // exception handling ( 조회 결과 없음 )

			String token = tokenProvider.createToken(loginVO);                  // jwt 토큰 생성
			LoginResponse loginResponse = new LoginResponse(loginVO, token);    // 반환 객체 생성
			loginResponse.setSsoId(ssoId);
			loginResponse.setRetCode(retCode);

			return ResponseEntity.ok(new SuccessResponse(loginResponse));
		} catch(Exception e) {
			LoginResponse loginResponse = LoginResponse.builder()
				.ssoId(ssoId)
				.retCode(retCode)
				.loginErrorMessage(e.getMessage())
				.build();
			return ResponseEntity.ok(new SuccessResponse(loginResponse));
		}
	}

	@PostMapping("/api/login")
	@ApiOperation(value = "KCPG 로그인")
	public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
		LoginResponse loginResponse = userService.login(loginRequest);
		return ResponseEntity.ok(new SuccessResponse(loginResponse));
	}

	@PostMapping("/api/logout")
	@ApiOperation(value = "로그아웃")
	public SuccessResponse logout() {
		RequestContextHolder.getRequestAttributes().removeAttribute("LoginVO", RequestAttributes.SCOPE_SESSION);
		return new SuccessResponse(null);
	}

	// 검색 조회 post 방식
	/*@PostMapping("/api/user/list")
	@ApiOperation(value = "사용자 목록 조회", notes ="검색 조건에 따른 사용자 목록을 조회한다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNm", value = "조직(사업소)명"),
			@ApiImplicitParam(name = "userNm", value = "사용자 이름"),
			@ApiImplicitParam(name = "authGroupId", value = "권한그룹 ID"),
			@ApiImplicitParam(name = "currentPageNo" , value = "페이지번호"),
			@ApiImplicitParam(name = "fetchCount" , value = "페이지당 조회 건수")
	})
	public SuccessResponse<PagedList<FindUserListResponse>> getUsers(@RequestBody FindUserListRequest request) {
		return new SuccessResponse(userService.findUsers(request)); // 조회 및 성공결과 반환
	}*/

	// 검색 조회 get 방식
	@GetMapping("/api/user/list")
	@ApiOperation(value = "사용자 목록 조회", notes ="검색 조건에 따른 사용자 목록을 조회한다.")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orgNm", value = "조직(사업소)명"),
			@ApiImplicitParam(name = "userNm", value = "사용자 이름"),
			@ApiImplicitParam(name = "authGroupId", value = "권한그룹 ID"),
			@ApiImplicitParam(name = "currentPageNo" , value = "페이지번호"),
			@ApiImplicitParam(name = "fetchCount" , value = "페이지당 조회 건수")
	})
	public SuccessResponse<PagedList<FindUserListResponse>> getUsers(@ModelAttribute FindUserListRequest request) throws UnsupportedEncodingException {
		String userNm = request.getUserNm();
		String fullOrgNm = request.getFullOrgNm();
		System.out.println("userNm = " + userNm);
		System.out.println("fullOrgNm = " + fullOrgNm);

		if (!StringUtils.isEmpty(userNm)) {
			// request.setUserNm(URLDecoder.decode(userNm, "UTF-8"));
			System.out.println("decodedUserNm = " + URLDecoder.decode(userNm, "UTF-8"));
		}

		if (!StringUtils.isEmpty(fullOrgNm)) {
			// request.setFullOrgNm(URLDecoder.decode(fullOrgNm, "UTF-8"));
			System.out.println("decodedFullOrgNm = " + URLDecoder.decode(fullOrgNm, "UTF-8"));
		}

		return new SuccessResponse(userService.findUsers(request)); // 조회 및 성공결과 반환
	}


	@PostMapping("/api/user")
	@ApiOperation(value = "사용자 신규등록")
	public SuccessResponse postUser(@RequestBody CreateUserRequest request) throws Exception {
	   userService.saveUser(request);
	   return new SuccessResponse(null);
	}

	@PatchMapping("/api/user")
	@ApiOperation(value = "사용자 정보 수정")
	public SuccessResponse patchUser(@RequestBody UpdateUserRequest request) throws Exception {
		userService.updateUser(request);
		return new SuccessResponse(null);
	}

	@DeleteMapping("/api/user")
	@ApiOperation(value = "사용자 삭제")
	public SuccessResponse deleteUser(@RequestBody DeleteUserRequest request) {
		userDAO.deleteUser(request);
		return new SuccessResponse(null);
	}
}
