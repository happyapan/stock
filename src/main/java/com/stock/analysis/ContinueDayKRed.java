package com.stock.analysis;


import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算连续K线是红色
 */
public class ContinueDayKRed extends BaseAnalysis {

    /**
     * 计算几天内的涨幅
     * @param stockCodes
     * @param minUpPrice 最小收盘价与开盘价价差
     * @param dateLine 最后计算日期
     * @param dayCount 计算天数
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, Float minUpPrice,String dateLine,Integer dayCount) {
        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }

        if(minUpPrice==null){
            minUpPrice=0f;
        }
        if(isEmpty(dateLine)){
            dateLine= DateUtil.GET_CURRENT_DATE();
        }
        if(dayCount== null){
            dayCount=30;
        }
        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, dateLine, null, dayCount);
            if(records!=null && records.size()>=dayCount){
                StockRecordBean theLastestRecord=records.get(0);
                boolean continueRed=true;
                for(int i=0;continueRed && i<records.size();i++){
                    StockRecordBean currentRecord=records.get(i);
                    Float upPrice=F(currentRecord.getOverPrice()) -F(currentRecord.getOpenPrice());
                    if(upPrice.compareTo(minUpPrice)>=0){
                        if(i>0){
                            theLastestRecord.compareSuccessRecords.add(currentRecord);
                        }
                    }else{
                        continueRed=false;
                    }
                }
                if(continueRed){
                    resultRecords.add(theLastestRecord);
                }
            }
        }
        return resultRecords;
    }

}
