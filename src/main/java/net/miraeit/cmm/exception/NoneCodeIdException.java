package net.miraeit.cmm.exception;

import lombok.Getter;

@Getter
public class NoneCodeIdException extends RuntimeException {
    private String codeId;
    public NoneCodeIdException() { super(); }
    public NoneCodeIdException(String codeId) {
        this();
        this.codeId = codeId;
    }
}
