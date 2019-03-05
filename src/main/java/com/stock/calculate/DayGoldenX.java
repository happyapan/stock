package com.stock.calculate;

import com.common.utils.DateUtil;
import com.common.utils.P;
import com.stock.analysis.BaseAnalysis;
import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;

import java.util.ArrayList;
import java.util.List;

public class DayGoldenX extends BaseAnalysis {

    /**
     * 找一段时间内出现十字星
     * @param stockCodes
     * @param bufferPriceFeng buffer 分
     * @param endDate 比较日期
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes,  int bufferPriceFeng,String endDate,String startDate) {
        List<StockRecordBean> resultRecords=new ArrayList<StockRecordBean>();

        if(isEmpty(startDate)){
            startDate= DateUtil.GET_CURRENT_DATE();
        }
        if(isEmpty(endDate)){
            endDate= DateUtil.GET_CURRENT_DATE();
        }
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }
        for (String stockCode : stockCodes) {
            P.PN("-");
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode,endDate,startDate,null);
            if(records==null || records.size()==0){
                continue;
            }

            for(StockRecordBean oneDayStock:records){
                //涨停,跌停的不要
                if (Math.abs(new Float(oneDayStock.getPlusRate())) > 9.5f) {
                    continue;
                }
                if (bufferPriceFeng == 0 ) {
                    if (oneDayStock.getOpenPrice().equals(oneDayStock.getOverPrice())) {
                        resultRecords.add(oneDayStock);
                    }
                }  else{
                    if ( Math.abs(F(oneDayStock.getOpenPrice())*100 - F(oneDayStock.getOverPrice())*100)<=bufferPriceFeng){
                        //最发涨跌额度
                        resultRecords.add(oneDayStock);
                    }
                }
            }
        }
        return resultRecords;
    }

}

