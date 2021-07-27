package cc.niushuai.nti56.issue.util;

import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author niushuai
 * @date 2021/7/26 15:10:28
 */
public class RegUtil {

    public static String DEFAULT_SOURCE_STR_PATTERN = "[a-zA-Z0-9-]+/[a-zA-Z0-9-]+/[a-zA-Z0-9-]+/[a-zA-Z0-9-]+#[0-9]+";
    public static String SOURCE_STR_PATTERN = "[a-zA-Z0-9-]+/[a-zA-Z0-9-]+/[a-zA-Z0-9-]+/[a-zA-Z0-9-]+#[0-9]+";

    public static boolean isMatch(String source) {

        Pattern pattern = Pattern.compile(SOURCE_STR_PATTERN);
        return pattern.matcher(source).find();
    }
}
