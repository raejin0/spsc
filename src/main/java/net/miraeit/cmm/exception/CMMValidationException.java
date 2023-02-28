package net.miraeit.cmm.exception;

public class CMMValidationException extends RuntimeException {
    private String code;

    public CMMValidationException(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
