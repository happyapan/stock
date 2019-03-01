package com.common.utils;

import com.stock.vo.StockRecordBean;

import java.util.List;

/**
 * Created by ccc016 on 2017/4/12.
 */
public class P {
    public static void P(String str){
        if(StringUtils.isEmpty(str)){
            System.out.println("");
        }else{
            System.out.println(str);
        }
    }
    public static void P_RESULT(List<StockRecordBean> recordBeans){
        if(recordBeans!=null && recordBeans.size()>0){
            recordBeans.stream().forEach(one ->P.P(one.getFormatData()));
            P("Total:" + recordBeans.size());
        }else{
            P("Total:0");
        }

    }
}
