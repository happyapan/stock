package com.stock.calculate;


import com.stock.analysis.BaseAnalysis;
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
     * @param minDownFeng 最小收盘价与开盘价价差,单位为分
     * @param dateLine 最后计算日期
     * @param dayCount 计算天数
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, int minDownFeng,String dateLine,Integer dayCount) {
        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }

        if(isEmpty(dateLine)){
            dateLine= DateUtil.GET_CURRENT_DATE();
        }
        if(dayCount== null){
            dayCount=30;
        }
        int tmpCompareSuccessDay=0;
        for (String stockCode : stockCodes) {
            tmpCompareSuccessDay=0;

            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, dateLine, null, null);
            if(records!=null && records.size()>=dayCount){
                StockRecordBean theLastestRecord=records.get(0);
                for(int i=0;i<records.size();i++){
                    StockRecordBean currentRecord=records.get(i);
                    //开盘价-收盘价
                    Float diffPrice =F(currentRecord.getOpenPrice())-F(currentRecord.getOverPrice());
                    diffPrice=diffPrice*100;
                    //>0 表示绿的
                    if(diffPrice.intValue()>0 && diffPrice.intValue() >= minDownFeng){
                        tmpCompareSuccessDay++;
                    }else{
                       break;
                    }
                    if(tmpCompareSuccessDay>=dayCount){
                        resultRecords.add(theLastestRecord);
                        break;
                    }
                }

            }
        }
        return resultRecords;
    }

}
