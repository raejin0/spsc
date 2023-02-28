package net.miraeit.cmm.controller;

import net.miraeit.cmm.model.ErrorResponse;
import net.miraeit.cmm.model.ErrorResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WasErrorController {

    @Autowired
    private ErrorResponseHelper errorResponseHelper;

    @GetMapping("/cmmError")
    public ResponseEntity<ErrorResponse> cmmError() {
        return errorResponseHelper.code("C003");
    }

    @GetMapping("/error404")
    public ResponseEntity<ErrorResponse> error404() {
        return errorResponseHelper.code("C001");
    }

    @GetMapping("/error500")
    public ResponseEntity<ErrorResponse> error500() {
        return errorResponseHelper.code("C004");
    }

    @GetMapping("/error400")
    public ResponseEntity<ErrorResponse> error400() {
        return errorResponseHelper.code("C005");
    }
}
