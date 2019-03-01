package com.common.utils;


public class StringUtils {

    /**
     * check the string is empty(include 'null' and 'multiple null string') or not
     * @param value string value
     * @return booean
     */
    public static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }

        if (value instanceof String) {
            if ("".equals(((String) value).trim())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean isNotEmpty(Object value) {
        return !isEmpty(value);
    }

    public static String lowerCaseTheFirstLetter(String str){
        if(isEmpty(str)){
            return "";
        }else if(str.length()==1){
            return str.toLowerCase();
        }else{
            return str.substring(0,1).toLowerCase()+str.substring(1);
        }
    }

}
