package com.stock.analysis;


import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算连续K线是绿
 */
public class ContinueDayKGreen extends BaseAnalysis {

    /**
     * 计算几天内的涨幅
     * @param stockCodes
     * @param minDownPrice 最小收盘价与开盘价价差
     * @param dateLine 最后计算日期
     * @param dayCount 计算天数
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, Float minDownPrice,String dateLine,Integer dayCount) {
        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }

        if(minDownPrice==null){
            minDownPrice=0f;
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
                boolean continueGreen=true;
                for(int i=0;continueGreen && i<records.size();i++){
                    StockRecordBean currentRecord=records.get(i);
                    Float downPrice =F(currentRecord.getOpenPrice())-F(currentRecord.getOverPrice());
                    if(downPrice.compareTo(minDownPrice)>=0){
                        if(i>0){
                            theLastestRecord.compareSuccessRecords.add(currentRecord);
                        }
                    }else{
                        continueGreen=false;
                    }
                }
                if(continueGreen){
                    resultRecords.add(theLastestRecord);
                }
            }
        }
        return resultRecords;
    }

}
