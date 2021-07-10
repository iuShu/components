package org.iushu.weboot.component;

/**
 * @author iuShu
 * @since 6/30/21
 */
public class CacheKeys {

    public static final String H_STAFF = "h:stf:";
    public static final String H_CUSTOMER = "h:cst:";
    public static final String H_ACTOR = "h:act:";

    public static final String TOKEN = "tkn";
    public static final String TOKEN_EXP = "tkn:exp";

    public static final String ACCESS_LIMIT = "aclm:";

    public static String userKey(short userId) {
        return H_STAFF + userId;    // role to be finished
    }

    public static String accessLimitKey(String ip) {
        return ACCESS_LIMIT + ip;
    }

}
