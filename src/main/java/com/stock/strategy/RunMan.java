package com.stock.strategy;

import com.common.utils.P;
import com.stock.calculate.ContinueDayKGreen;

/**
 * Created by Jeff on 2019/3/1.
 */
public class RunMan {

    public static void main(String []args){
        ContinueDayKGreen continueDayKGreen=new ContinueDayKGreen();
        P.P_RESULT(continueDayKGreen.anlysis(null,1,"2019-02-01",new Integer(3)));
//        P.P_RESULT(continueDayKGreen.anlysis(Arrays.asList("600018"),1,"2019-02-01",new Integer(2)));

    }
}
