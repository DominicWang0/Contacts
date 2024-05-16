package com.example.contacts.dataProcessing;

import java.util.regex.Pattern;

public class dataValid {

    //验证手机号合法   1开头，第二位3-9，剩余9位自由组成
    public static boolean isPhoneValid(String phone) {
        if ((phone != null) && (!phone.isEmpty())) {
            return Pattern.matches("^1[3-9]\\d{9}$", phone);
        }
        return false;
    }

    //验证邮箱合法    （由a～z的英文字母（不区分大小写）开头；可由英文字母、0～9的数字、点、减号或下划线组成；长度为3～18个字符；不能以点、减号结尾）@（只能有一个点，点和"@"之间不能为空；可由英文字母、0～9的数字、点、减号或下划线组成；不能以点、减号或下划线结尾）
    public static boolean isEmailValid(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){1,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }

    //验证密码合法    密码长度8～16
    public static boolean isPasscodeValid(String passcode) {
        return (passcode.length() >= 8) && (passcode.length() <= 16);
    }

    //字符串判空
    public static boolean isEmpty(String data) {
        return (data.length() == 0) || (data == "");
    }
}
