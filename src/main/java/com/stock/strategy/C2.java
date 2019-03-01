package com.stock.strategy;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.stock.analysis.AvgLineCrossBreak;
import com.common.utils.P;

import java.util.List;

/**
 * 计算今天是否有金X
 */
public class C2 {
    public  static  void main(String[]args){
        List<String> allStockCodes = StockReadUtils.getAllStockCode();
        AvgLineCrossBreak avgLineCrossBreak=new AvgLineCrossBreak();
        List<StockRecordBean> result=avgLineCrossBreak.anlysis(allStockCodes,"2017-11-20",5,10,5);
        P.P(StockRecordBean.getSourceTitle());
        for(StockRecordBean one:result){
            P.P(one.getSource());
        }
    }
}
