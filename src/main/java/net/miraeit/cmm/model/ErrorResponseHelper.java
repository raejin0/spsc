package net.miraeit.cmm.model;

import egovframework.com.cmm.EgovMessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ErrorResponseHelper {

    private final EgovMessageSource egovMessageSource;

    public ResponseEntity<ErrorResponse> code(String code){
        return ResponseEntity.status(400).body(new ErrorResponse(code,egovMessageSource.getMessage(code)));
    }

    public ResponseEntity<ErrorResponse> code(String code, String appendMessage) {
        if(StringUtils.isEmpty(appendMessage)) return code(code);

        return ResponseEntity.status(400).body(
            new ErrorResponse(
                code,
                String.format("%s: [%s]", egovMessageSource.getMessage(code), appendMessage)
            )
        );
    }

    public ResponseEntity<ErrorResponse> errorHelper(String code, String message){
        return ResponseEntity.status(400).body(new ErrorResponse(code,message));
    }
}
