package org.example.util;

public class NumberUtil {
    public static boolean isIntNumber(String text){
        try {
            Integer.parseInt(text);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static int StringInInteger(String text){
         return Integer.parseInt(text);
    }
    public static boolean isNumberInRange(int number, int min, int max){
        return number >= min && number <= max;
    }
}
