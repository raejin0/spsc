package net.miraeit.cmm.property;

public class Regex {
    public static final String YEAR = "^(1|2)\\d{3}$";      // 년도 유효성 맨 앞자리가 1 또는 2로 되어야 하고 총 4자리로 이루어져야 한다.
    public static final String HALF_YEAR= "^(S|F)$";         // 반기 유효성 S 또는 F 로 이루어 져야하고 총 1자리로 이루어져야 한다.
    public static final String DATE= "^(1|2)\\d{3}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";         // 날짜 유효성 맨 앞자리 1 또는 2 이며 YYYY-MM-dd 형식
    public static final String INSPECT= "^(N|X|V)$";
    public static final String INSPECT_STATE= "^(I|G)$";
    public static final String ORG_TYPE= "^(1|3)$";
    public static final String USE_YN= "^(Y|N)$";
}