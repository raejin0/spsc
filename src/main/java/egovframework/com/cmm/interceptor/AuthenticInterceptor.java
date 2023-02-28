package egovframework.com.cmm.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.miraeit.cmm.authentication.TokenProvider;
import net.miraeit.user.exception.UnauthorizedException;
import net.miraeit.user.model.LoginVO;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

/**
 * 인증여부 체크 인터셉터
 * @author 공통서비스 개발팀 서준식
 * @since 2011.07.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2011.07.01  서준식          최초 생성
 *  2011.09.07  서준식          인증이 필요없는 URL을 패스하는 로직 추가
 *  2014.06.11  이기하          인증이 필요없는 URL을 패스하는 로직 삭제(xml로 대체)
 *  </pre>
 */
@RequiredArgsConstructor
@Slf4j
public class AuthenticInterceptor extends WebContentInterceptor {

	private final TokenProvider tokenProvider;

	/**
	 * 세션에 계정정보(LoginVO)가 있는지 여부로 인증 여부를 체크한다.
	 * 계정정보(LoginVO)가 없다면, 로그인 페이지로 이동한다.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {

		String jwt = tokenProvider.extractJwt(request);  // jwt 추출
		tokenProvider.validateToken(jwt);                // jwt 예외처리
		LoginVO loginVO = tokenProvider.getLoginVO(jwt); // 토큰에서 사용자 정보 추출
//		=====================================================================================
//		개발환경에서 스웨거 화면 보려면 윗줄을 주석처리하고 아래 내용을 적용
//		LoginVO loginVO = new LoginVO();
//		loginVO.setId("mng01");
//		loginVO.setUsername("mng01");
//		loginVO.setMngYn("Y");
//		loginVO.setOrgId("1393");
//		loginVO.setAuthGroupId(2);
//		=====================================================================================

		// contextHolder에 decode한 로그인 정보를 추가 - 로그인 상태를 확인하기 위함
		RequestContextHolder.getRequestAttributes().setAttribute("LoginVO", loginVO, RequestAttributes.SCOPE_REQUEST);
		log.debug("Request Context 에 '{}' 인증 정보를 저장했습니다, uri: {}", loginVO.getId(), request.getRequestURI());

		authCheck(loginVO, request);
		return true;
	}

	/**
	 * 권한을 검증하는 메소드
	 * @param loginVO
	 * @param request
	 */
	private void authCheck(LoginVO loginVO, HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		String[] adminRoleURIs = {
				"/api/user/*"
		};

		// 관리자 그룹이 아닌 경우
		if ( loginVO.getAuthGroupId() != 1) {
			// 정의된 url에 포함되지 않는 경우 exception
			for (String url : adminRoleURIs) {
				// 특정 rootPath 하단의 전체 url일 경우
				if(url.endsWith("*")){
					String rootPath = url.substring(0, url.length() - 2);
					if (requestURI.contains(rootPath))
						throw new UnauthorizedException();

				} else if (!url.equals(requestURI))
					throw new UnauthorizedException();
			}
		}
	}
}
