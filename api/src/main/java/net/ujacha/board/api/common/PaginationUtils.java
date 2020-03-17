package net.ujacha.board.api.common;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

public class PaginationUtils {
    public static String qs(Map<String, Object> param, String pageKey, int pageNum){

        return param.entrySet().stream()
                .peek(p -> {
                    System.out.println("K = " + p.getKey() + " / V = " + ((String[])p.getValue())[0]);
                })
                .map(p -> {
                    if(p.getKey().equals(pageKey)){
                        return urlEncodeUTF8(p.getKey()) + "=" + pageNum;
                    }
                    return urlEncodeUTF8(p.getKey()) + "=" + urlEncodeUTF8(((String[]) p.getValue())[0]);
                })
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");

    }

    private static String urlEncodeUTF8(String key) {
        return URLEncoder.encode(key, Charset.forName("UTF-8"));
    }
}
