package net.miraeit.cmm.util;

import com.initech.eam.api.NXContext;
import com.initech.eam.api.NXNLSAPI;
import com.initech.eam.api.NXUserAPI;
import com.initech.eam.base.APIException;
import com.initech.eam.base.EmptyResultException;
import com.initech.eam.nls.CookieManager;
import com.initech.eam.smartenforcer.SECode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

@Component
public class SSOUtil {
	// 추후 프로퍼티스로 번경해서 테스트
	private String SERVICE_NAME = "KOMIPO";
	private String SERVER_URL = "http://cpnet.komipo.co.kr";                // 운영서버 도메인 주소
//	private String SERVER_URL = "http://cpnetdev.komipo.co.kr";                // 개발서버 도메인 주소
	private String SERVER_PORT = "8099";                                    // 웹포트
	private String ASCP_URL = SERVER_URL + ":" + SERVER_PORT + "/api/sso";  // 중부발전 sso 설정 필요자료

	private String NLS_URL 		 = "http://sso.komipo.co.kr";
	private String NLS_PORT 	 = "8080";
	private String NLS_LOGIN_URL = NLS_URL + ":" + NLS_PORT + "/nls3/clientLogin.jsp";
	private String NLS_LOGOUT_URL= NLS_URL + ":" + NLS_PORT + "/nls3/NCLogout.jsp";
	private String NLS_ERROR_URL = NLS_URL + ":" + NLS_PORT + "/nls3/error.jsp";
	private static String ND_URL1 = "http://sso.komipo.co.kr:5480";
	private static String ND_URL2 = "http://nsso2.komipo.co.kr:5480";


	private static final int COOKIE_SESSION_TIME_OUT = 3000000;
	private static Vector PROVIDER_LIST = new Vector();

	// 인증 타입 (ID/PW 방식 : 1, 인증서 : 3)
	private String TOA = "1";
	private String SSO_DOMAIN = ".komipo.co.kr";

	private static final int timeout = 15000;
	private static NXContext context; // = null;

	static {
		List<String> serverurlList = new ArrayList<String>();
		serverurlList.add(ND_URL1);
		serverurlList.add(ND_URL2);

		context = new NXContext(serverurlList,timeout);
		CookieManager.setEncStatus(true);

		PROVIDER_LIST.add("sso.komipo.co.kr");
		SECode.setCookiePadding("_V42");
	}

	// 통합 SSO ID 조회
	public String getSsoId(HttpServletRequest request) {
		return CookieManager.getCookieValue(SECode.USER_ID, request);
	}

	// 통합인증 세션을 체크 하기 위하여 사용되는 API
	public String getEamSessionCheckAndAgentValid(HttpServletRequest request, HttpServletResponse response){
		String retCode = "";
		try {
			retCode = CookieManager.verifyNexessCookieAndAgentVaild(request, response, 10, COOKIE_SESSION_TIME_OUT, PROVIDER_LIST, SERVER_URL, context);
		} catch(Exception npe) {
			npe.printStackTrace();
		}
		return retCode;
	}


	//ND API를 사용해서 쿠키검증하는것(현재 표준에서는 사용안함, 근데 해도 되기는 함)
	public String getEamSessionCheck2(HttpServletRequest request, HttpServletResponse response)
	{
		String retCode = "";
		try {
			NXNLSAPI nxNLSAPI = new NXNLSAPI(context);
			retCode = nxNLSAPI.readNexessCookie(request, response, 0, 0);
		} catch(Exception npe) {
			npe.printStackTrace();
		}
		return retCode;
	}

	// SSO 에러페이지 URL
	public void goErrorPage(HttpServletResponse response, int error_code)throws Exception {
		CookieManager.removeNexessCookie(SSO_DOMAIN, response);
		CookieManager.addCookie(SECode.USER_URL, ASCP_URL, SSO_DOMAIN, response);
		response.sendRedirect(NLS_ERROR_URL + "?errorCode=" + error_code);
	}


	/**
	 * 사용자의 특정 확장 필드 정보 조회
	 * return : String
	 * 사용자가 존재하지 않거나 확장필드가 없다면 return null
	 */
	public boolean isMatchPassword(String userid, String password)
	throws Exception {
		NXUserAPI userAPI = new NXUserAPI(context);
		boolean bRet = false;

		try {
			bRet = userAPI.isMatchPassword(userid, password);
		} catch (EmptyResultException e) {
	 		// 사용자가 존재하지 않거나 확장필드가 없음
		} catch (APIException e) {
			throw e;
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw e;
			//e.printStackTrace();
		}
		return bRet;
	}
}
