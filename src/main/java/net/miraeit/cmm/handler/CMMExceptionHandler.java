package net.miraeit.cmm.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.miraeit.cmm.exception.*;
import net.miraeit.cmm.exception.file.FileDetailNotFoundException;
import net.miraeit.cmm.exception.file.NotImageException;
import net.miraeit.cmm.exception.file.NullFileException;
import net.miraeit.cmm.exception.jwt.InvalidJwtException;
import net.miraeit.cmm.exception.jwt.JwtNotFoundException;
import net.miraeit.cmm.model.ErrorResponse;
import net.miraeit.cmm.model.ErrorResponseHelper;
import net.miraeit.community.exception.DifferentUserException;
import net.miraeit.community.exception.NoPostFoundException;
import net.miraeit.master.exception.*;
import net.miraeit.pointout.exception.ExistingAdvNoException;
import net.miraeit.pointout.exception.OrganizationCodeNotFoundException;
import net.miraeit.pointout.exception.SummaryOrganizationCodeNotFoundException;
import net.miraeit.pointout.exception.SheetNameNotMatchedException;
import net.miraeit.user.exception.*;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CMMExceptionHandler {

    private final ErrorResponseHelper errorResponseHelper;

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
    	log.error("handleException", e);
        return errorResponseHelper.code("C000");
    }

    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException e) {
    	log.error("handleNullPointerException", e);
        return errorResponseHelper.code("C002");
    }

    @ExceptionHandler({ MstLawTplTimeOverlapException.class })
    public ResponseEntity<ErrorResponse> handleMstLawTplTimeOverlapException() {
        return errorResponseHelper.code("M000");
    }

    @ExceptionHandler({CMMValidationException.class })
    public ResponseEntity<ErrorResponse> handleYearCMMValidationException(CMMValidationException e) {
        return errorResponseHelper.code(e.getCode());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return errorResponseHelper.code(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({ MasterTemplateGetNullException.class })
    public ResponseEntity<ErrorResponse> handleMasterTemplateGetNullException() {
        return errorResponseHelper.code("M001");
    }

    @ExceptionHandler({ AppliedPeriodNullException.class })
    public ResponseEntity<ErrorResponse> handleAppliedPeriodNullException() {
        return errorResponseHelper.code("M002");
    }

    @ExceptionHandler({ MasterSubDataExistException.class })
    public ResponseEntity<ErrorResponse> handleMainExistByTemplateIdException() {
        return errorResponseHelper.code("M003");
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException() {
        return errorResponseHelper.code("V005");
    }

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
    	log.error("handleHttpMessageNotReadableException", e);
        return errorResponseHelper.code("C006");
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
	    log.error("handleDataIntegrityViolationException", e);

	    if (e.getClass().getName().contains("DuplicateKeyException")) return errorResponseHelper.code("DI000"); // 중복값

        return errorResponseHelper.code("C007");
    }

    @ExceptionHandler({ NoneCodeException.class })
    public ResponseEntity<ErrorResponse> handleNoneCodeException() {
        return errorResponseHelper.code("C008");
    }

    @ExceptionHandler({ MasterMainOverlapException.class })
    public ResponseEntity<ErrorResponse> handleMainOverlapException() {
        return errorResponseHelper.code("M004");
    }

    @ExceptionHandler({ MasterImplementationExistException.class })
    public ResponseEntity<ErrorResponse> handleMasterImplementationExistException() {
        return errorResponseHelper.code("M005");
    }

    @ExceptionHandler({ MasterNoLastTemplateException.class })
    public ResponseEntity<ErrorResponse> handleMasterNoLastTemplateException() {
        return errorResponseHelper.code("M006");
    }

    @ExceptionHandler({ MasterSubOverlapException.class })
    public ResponseEntity<ErrorResponse> handleMasterSubOverlapException() {
        return errorResponseHelper.code("M007");
    }

    @ExceptionHandler({ NoneCodeIdException.class })
    public ResponseEntity<ErrorResponse> handleNoneCodeIdException(NoneCodeIdException exception) {
        return errorResponseHelper.code("C009", exception.getCodeId());
    }

    @ExceptionHandler({ NoneOrganizationKomipoException.class })
    public ResponseEntity<ErrorResponse> handleNoneOrganizationKomipoException() {
        return errorResponseHelper.code("C010");
    }

    @ExceptionHandler({ BadSqlGrammarException.class })
    public ResponseEntity<ErrorResponse> handleBadSqlGrammarException(BadSqlGrammarException e) {
    	log.error("handleBadSqlGrammarException", e);
        return errorResponseHelper.code("C011");
    }

    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    	log.error("handleHttpRequestMethodNotSupportedException", e);
        return errorResponseHelper.code("C012");
    }

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		log.error("handleMissingServletRequestParameterException", e);
		return errorResponseHelper.code("C013");
	}

    @ExceptionHandler({ BindException.class })
    public ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        return errorResponseHelper.code(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({ NoSuchMessageException.class })
    public ResponseEntity<ErrorResponse> handleBindException() {
        return errorResponseHelper.code("C014");
    }

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    	log.error("handleHttpMediaTypeNotSupportedException", e);
    	return errorResponseHelper.code("C015");

	}

    // UserExceptionHandler ( user 패키 하위에 추가하면 동작하지 않음 )
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerLoginFailException(LoginFailException e) {
	log.error("handlerLoginFailException", e);
	   return errorResponseHelper.code("U001");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerUnauthorizedException(UnauthorizedException e) {
	log.error("handlerUnauthorizedException", e);
	 return errorResponseHelper.code("U002");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerNullPageNoException(NullPageNoException e) {
		log.error("handlerNullPageNoException", e);
		return errorResponseHelper.code("U003");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerNullFetchCountException(NullFetchCountException e) {
		log.error("handlerNullFetchCountException", e);
		return errorResponseHelper.code("U004");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerInvalidPageNoException(InvalidPageNoException e) {
		log.error("handlerInvalidPageNoException", e);
		return errorResponseHelper.code("U005");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerPageNoExceededException(PageNoExceededException e) {
		log.error("handlerPageNoExceededException", e);
		return errorResponseHelper.code("U006");
	}

	// ----- SSO -------------------------------
	@ExceptionHandler
	public ModelAndView handleSSOIdNotFoundException(SSOIdNotFoundException e) {
		log.info("handleSSOIdNotFoundException", e);
		ModelAndView mv = new ModelAndView("index"); // model 객체 받아서 쓸수는 없나?
		return mv;  // 로그인 페이지로 이동
	}

	@ExceptionHandler
	public ModelAndView handleInvalidRetCodeException(InvalidRetCodeException e) {
		log.info("handleInvalidRetCodeException", e);
		ModelAndView mv = new ModelAndView("index"); // model 객체 받아서 쓸수는 없나?
		return mv;  // 로그인 페이지로 이동
	}
	// ///// SSO ///////////////////////////////


	// ----- token -------------------------------
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleInvalidJwtException(InvalidJwtException e) {
		return errorResponseHelper.code("T000");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleJsonwebtokenSecurityException(io.jsonwebtoken.security.SecurityException e) {
		return errorResponseHelper.code("T001");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException e) {
		return errorResponseHelper.code("T001");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException e) {
		return errorResponseHelper.code("T002");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleUnsupportedJwtException(UnsupportedJwtException e) {
		return errorResponseHelper.code("T003");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleJwtNotFoundException(JwtNotFoundException e) {
		return errorResponseHelper.code("T004");
	}
	// ///// token ///////////////////////////////

	// ----- file -------------------------------
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleNotImageException(NotImageException e) {
		log.error("handleNotImageException", e);
		return errorResponseHelper.code("F000");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleFileDetailNotFoundException(FileDetailNotFoundException e) {
		log.error("handleFileDetailNotFoundException", e);
		return errorResponseHelper.code("F001");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerNullFileException(NullFileException e) {
		log.error("handlerNullFileException", e);
		return errorResponseHelper.code("F002");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
		log.error("handlerMaxUploadSizeExceededException", e);
		return errorResponseHelper.code("F003");
	}

	// ///// file ///////////////////////////////


	// ----- community -------------------------------
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleNoPostFoundException(NoPostFoundException e) {
		log.error("handleNoPostFoundException", e);
		return errorResponseHelper.code("B000");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleDifferentUserException(DifferentUserException e) {
		log.error("handleDifferentUserException", e);
		return errorResponseHelper.code("B001");
	}


	// ///// community ///////////////////////////////


	// ----- mail -------------------------------
	@ExceptionHandler
	public void handleNullMailSettingException(NullMailSettingException e) {
		log.error("handleNullMailSettingException", e);
	}

	// ///// mail ///////////////////////////////


	// ----- point-out 지적/조치사항 -------------------------------
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleSummaryOrganizationCodeNotFoundException(SummaryOrganizationCodeNotFoundException e) {
    	log.error("handleSummaryOrganizationCodeNotFoundException", e);
    	return errorResponseHelper.code("P000");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleSheetNameNotMatchedException(SheetNameNotMatchedException e) {
    	log.error("handleSheetNameNotMatchedException", e);
    	return errorResponseHelper.code("P001");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleOrganizationCodeNotFoundException(OrganizationCodeNotFoundException e) {
    	log.error("handleOrganizationCodeNotFoundException", e);
    	return errorResponseHelper.code("P002");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleExistingAdvNoException(ExistingAdvNoException e) {
    	log.error("handleExistingAdvNoException", e);
    	return errorResponseHelper.code("P003");
	}

	// ///// point-out 지적/조치사항 ///////////////////////////////
}
