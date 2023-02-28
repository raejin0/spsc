package net.miraeit.cmm.util;

import lombok.RequiredArgsConstructor;
import net.miraeit.cmm.dao.CMMDAO;
import net.miraeit.cmm.exception.NoneCodeException;
import net.miraeit.cmm.model.CMMInside;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CMMUtil {
    private final CMMDAO cmmDAO;

    public void codeCheck(String code, String codeId){
        CMMInside cmmInside = new CMMInside();
        cmmInside.setCode(code);
        cmmInside.setCodeId(codeId);
        if (cmmDAO.checkCode(cmmInside) == null) throw new NoneCodeException();
    }
}
