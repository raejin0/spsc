<%@page import="com.initech.eam.api.NXNLSAPI"%>
<%@page import="com.initech.eam.smartenforcer.SECode"%>
<%@page import="java.util.Vector"%>
<%@page import="com.initech.eam.nls.CookieManager"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.log4j.PropertyConfigurator"%>
<%@page import="com.initech.eam.api.NXContext"%>
<%!
/**[INISAFE NEXESS JAVA AGENT]**********************************************************************
* �����ý��� ���� ���� (���� ȯ�濡 �°� ����)
***************************************************************************************************/


/***[SERVICE CONFIGURATION]***********************************************************************/
	private String SERVICE_NAME = "Web";
	private String SERVER_URL 	= "http://nlstest.initech.com";
	private String SERVER_PORT = "8090";
	private String ASCP_URL = SERVER_URL + ":" + SERVER_PORT + "/agent/sso/login_exec.jsp";
	
	//Custom Login Url
	//private String custom_url = SERVER_URL + ":" + SERVER_PORT + "/agent/sso/loginFormPageCoustom.jsp";
	private String custom_url = "";
/*************************************************************************************************/


/***[SSO CONFIGURATION]**]***********************************************************************/
	private String NLS_URL 		 = "http://nlstest.initech.com";
	private String NLS_PORT 	 = "8080";
	private String NLS_LOGIN_URL = NLS_URL + ":" + NLS_PORT + "/nls3/clientLogin.jsp";
	//private String NLS_LOGIN_URL = NLS_URL + ":" + NLS_PORT + "/nls3/cookieLogin.jsp";
	private String NLS_LOGOUT_URL= NLS_URL + ":" + NLS_PORT + "/nls3/NCLogout.jsp";
	private String NLS_ERROR_URL = NLS_URL + ":" + NLS_PORT + "/nls3/error.jsp";
	private static String ND_URL1 = "http://ndtest.initech.com:5480";
	private static String ND_URL2 = "http://ndtest.initech.com:5481";

	private static Vector PROVIDER_LIST = new Vector();

	private static final int COOKIE_SESSTION_TIME_OUT = 3000000;

	// ���� Ÿ�� (ID/PW ��� : 1, ������ : 3)
	private String TOA = "1";
	private String SSO_DOMAIN = ".initech.com";

	private static final int timeout = 15000;
	private static NXContext context = null;
	static{
		//PropertyConfigurator.configureAndWatch("D:/INISafeNexess/site/4.1.0/src/Web/WebContent/WEB-INF/logger.properties");
		List<String> serverurlList = new ArrayList<String>();
		serverurlList.add(ND_URL1);
		serverurlList.add(ND_URL2);

		context = new NXContext(serverurlList,timeout);
		CookieManager.setEncStatus(true);

		PROVIDER_LIST.add("dev.initech.com");
		PROVIDER_LIST.add("nxtest.initech.com");
		
		//NLS3 web.xml�� CookiePadding ���� ���ƾ� �Ѵ�. �ȱ׷� ���� ���ϳ�
		//InitechEamUID +"_V42" .... ���·� ��� ������
		SECode.setCookiePadding("_V42");
	}

	// ���� SSO ID ��ȸ
	public String getSsoId(HttpServletRequest request) {
		String sso_id = null;
		sso_id = CookieManager.getCookieValue(SECode.USER_ID, request);
		return sso_id;
	}
	// ���� SSO �α��������� �̵�
	public void goLoginPage(HttpServletResponse response)throws Exception {
		CookieManager.addCookie(SECode.USER_URL, ASCP_URL, SSO_DOMAIN, response);
		CookieManager.addCookie(SECode.R_TOA, TOA, SSO_DOMAIN, response);
		
	       //��ü �α����� �Ұ�� �α��� ������ Setting
	    if(custom_url.equals(""))
	   	{
	    	//CookieManager.addCookie("CLP", "", SSO_DOMAIN, response);
	    }else{
	    	CookieManager.addCookie("CLP", custom_url , SSO_DOMAIN, response);
	    }
		
		response.sendRedirect(NLS_LOGIN_URL);
	}

	// �������� ������ üũ �ϱ� ���Ͽ� ���Ǵ� API
	public String getEamSessionCheckAndAgentVaild(HttpServletRequest request,HttpServletResponse response){
		String retCode = "";
		try {
			retCode = CookieManager.verifyNexessCookieAndAgentVaild(request, response, 10, COOKIE_SESSTION_TIME_OUT, PROVIDER_LIST, SERVER_URL, context);
		} catch(Exception npe) {
			npe.printStackTrace();
		}
		return retCode;
	}
	
	
	// �������� ������ üũ �ϱ� ���Ͽ� ���Ǵ� API(Agent ���� ���� �Լ�, �������)
	//@deprecated
	public String getEamSessionCheck(HttpServletRequest request,HttpServletResponse response){
		String retCode = "";
		try {
			retCode = CookieManager.verifyNexessCookie(request, response, 10, COOKIE_SESSTION_TIME_OUT,PROVIDER_LIST);
		} catch(Exception npe) {
			npe.printStackTrace();
		}
		return retCode;
	}
	
	
	//ND API�� ����ؼ� ��Ű�����ϴ°�(���� ǥ�ؿ����� ������, �ٵ� �ص� �Ǳ�� ��)
	public String getEamSessionCheck2(HttpServletRequest request,HttpServletResponse response)
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

	// SSO ���������� URL
	public void goErrorPage(HttpServletResponse response, int error_code)throws Exception {
		CookieManager.removeNexessCookie(SSO_DOMAIN, response);
		CookieManager.addCookie(SECode.USER_URL, ASCP_URL, SSO_DOMAIN, response);
		response.sendRedirect(NLS_ERROR_URL + "?errorCode=" + error_code);
	}

	
	/**
	 * ������� Ư�� Ȯ�� �ʵ� ���� ��ȸ
	 * return : String
	 * ����ڰ� �������� �ʰų� Ȯ���ʵ尡 ���ٸ� return null
	 */
	public boolean isMatchPassword(String userid, String password)
	throws Exception {
		NXUserAPI userAPI = new NXUserAPI(context);
		boolean bRet = false;

		try {
			bRet = userAPI.isMatchPassword(userid, password);
		} catch (EmptyResultException e) {
	 		// ����ڰ� �������� �ʰų� Ȯ���ʵ尡 ����
		} catch (APIException e) {
			throw e;
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			throw e;
			//e.printStackTrace();
		}
		return bRet;
	}
%>
