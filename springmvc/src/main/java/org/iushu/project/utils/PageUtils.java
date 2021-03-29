package org.iushu.project.utils;

/**
 * @author iuShu
 * @since 3/29/21
 */
public class PageUtils {

    /**
     * @param pageNo the page number
     * @param pageSize default 10 and max 100
     */
    public static int limitBegin(int pageNo, int pageSize) {
        if (pageNo < 1)
            pageNo = 1;
        if (pageSize < 1 || pageSize > 100)
            pageSize = 10;
        return (pageNo - 1) * pageSize;
    }

    public static void main(String[] args) {
        System.getenv().forEach((k, v) -> System.out.println(k + "\t" + v));
    }

}
