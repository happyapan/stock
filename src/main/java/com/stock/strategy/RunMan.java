package com.stock.strategy;

import com.common.utils.P;
import com.stock.calculate.DayGoldenX;

/**
 * Created by Jeff on 2019/3/1.
 */
public class RunMan {

    public static void main(String[] args) {
//        ContinueDayKGreen calculateTool=new ContinueDayKGreen();
//        P.P_RESULT(calculateTool.anlysis(null,1,"2019-02-01",new Integer(7)));
//
        DayGoldenX calculateTool = new DayGoldenX();
        P.P_RESULT(calculateTool.anlysis(null, 1, "2019-02-23","2019-02-19"));
//        P.P_RESULT(continueDayKGreen.anlysis(Arrays.asList("600018"),1,"2019-02-01",new Integer(2)));

    }
}
