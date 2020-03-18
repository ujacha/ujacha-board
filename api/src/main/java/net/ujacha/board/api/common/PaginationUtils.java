package net.ujacha.board.api.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.swing.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PaginationUtils {
    public static String link(String baseUrl, String queryString, @NotEmpty  String pageKey, @Min(1) int pageNum) {


        if (baseUrl == null){
            baseUrl = "";
        }

        return baseUrl + "?" + updatePage(queryString, pageKey, pageNum);

    }

    private static String updatePage(String queryString, String pageKey, int pageNum) {

        if(StringUtils.isEmpty(queryString)){
            return pageKey + "=" + pageNum;
        }

        List<String[]> params = Arrays
                .stream(queryString.split("&")) // & 로 분리 -> param 단위
                .map(p -> p.split("=")) // = 로 분리 -> key - value
                .filter(p -> !p[0].equals(pageKey)).collect(Collectors.toList());

        params.add(new String[]{pageKey, String.valueOf(pageNum)});

        return params.stream().map(p -> p[0] + "=" + p[1]) // "key=value" 형태로 변경
                .reduce((p1, p2) -> p1 + "&" + p2) // & 로 param 결합
                .orElse("");

    }

    private static String urlEncodeUTF8(String key) {
        return URLEncoder.encode(key, Charset.forName("UTF-8"));
    }
}
