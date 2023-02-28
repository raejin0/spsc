package net.miraeit.user.service;

import egovframework.let.utl.sim.service.EgovFileScrty;
import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.authentication.TokenProvider;
import net.miraeit.fx.listing.model.PagedList;
import net.miraeit.user.dao.UserDAO;
import net.miraeit.user.exception.LoginFailException;
import net.miraeit.user.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;


@Service
@RequiredArgsConstructor
public class UserService {

	private final UserDAO userDAO;
	private final TokenProvider tokenProvider;


	public LoginResponse login(LoginRequest loginRequest) throws Exception {

		// 조회 및 exception 처리
		LoginVO loginVO = findUser(loginRequest);
		if(loginVO == null) throw new LoginFailException();

		// contextHolder에 로그인 정보 추가- 로그인 상태를 확인하기 위함
		RequestContextHolder.getRequestAttributes().setAttribute("LoginVO", loginVO, RequestAttributes.SCOPE_REQUEST);

		// jwt 토큰 생성
		String token = tokenProvider.createToken(loginVO);

		// DTO 생성 및 반환
		return new LoginResponse(loginVO, token);
	}

	/**
	 * 사용자 정보를 조회 및 반환한다.
	 *
	 * @param loginRequest
	 * @return
	 * @throws Exception
	 */
	public LoginVO findUser(LoginRequest loginRequest) throws Exception {
		// 1. 입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(loginRequest.getPassword(), loginRequest.getId());
		loginRequest.setPassword(enpassword);

		// 2. 결과 반환
		return userDAO.findUser(loginRequest);
	}

	public LoginVO findUserById(String id) {
		LoginRequest loginRequest = LoginRequest.builder()
				.id(id)
				.build();
		return userDAO.findUser(loginRequest);
	}

	/**
	 * 사용자 정보를 저장한다.
	 *
	 * @param request
	 * @throws Exception
	 */
	public void saveUser(CreateUserRequest request) throws Exception {
		// 입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(request.getPassword(), request.getId());
		request.setPassword(enpassword);

		userDAO.saveUser(request);
	}

	/**
	 * 사용자 정보를 갱신한다.
	 *
	 * @param request
	 * @throws Exception
	 */
	public void updateUser(UpdateUserRequest request) throws Exception {
		// 입력한 비밀번호를 암호화한다.
		String enpassword = EgovFileScrty.encryptPassword(request.getPassword(), request.getId());
		request.setPassword(enpassword);

		userDAO.updateUser(request);
	}

	/**
	 * 사용자 목록과 페이징정보를 포함한 결과를 반환한다.
	 *
	 * @param request
	 * @return
	 */
	public PagedList<FindUserListResponse> findUsers(FindUserListRequest request) {
		return userDAO.findUsers(request);
	}
}
