package com.example.a.fd.Util;

/**
 * @Package: com.example.a.fd.Util
 * @ClassName: NumUtil
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/23 12:01
 */
public class NumUtil {
    private static java.text.DecimalFormat OneDecimal = new java.text.DecimalFormat("0.0");;
    public static String setOneDecimal(double num){
        return OneDecimal.format(num);
    }

}
