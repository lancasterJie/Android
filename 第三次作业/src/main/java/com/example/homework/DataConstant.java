package com.example.homework;

/**
 * 数据传递的键名常量类
 */
public class DataConstant {
    // Activity→Activity 传递键名
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_USER_AGE = "user_age";
    public static final String KEY_IS_STUDENT = "is_student";

    // Activity→Fragment 传递键名
    public static final String KEY_FRAGMENT_INIT_DATA = "init_data";

    // Fragment→Activity 返回结果键名
    public static final String KEY_FRAGMENT_RESULT = "fragment_result";

    // Fragment→Fragment 中转键名
    public static final String KEY_FRAGMENT_TO_FRAGMENT = "fragment_to_fragment";
}