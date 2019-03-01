package com.stock.analysis;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算几天内的涨幅到一定比率
 */
public class DaysUpRate extends BaseAnalysis {

    /**
     * 计算几天内的涨幅到一定比率
     * @param stockCodes
     * @param upRate 涨幅 正数
     * @param dateLine 最后计算日期
     * @param dayCount 计算天数
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, Float upRate,String dateLine,Integer dayCount) {
        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }

        if(upRate==null || upRate.compareTo(0.0f)==0){
            upRate=30f;
        }
        if(isEmpty(dateLine)){
            dateLine= DateUtil.GET_CURRENT_DATE();
        }
        if(dayCount== null){
            dayCount=30;
        }
        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, dateLine, null, dayCount);
            if(records!=null && records.size()>1){
                StockRecordBean theLastestRecord=records.get(0);
                float dateLinePrice=F(theLastestRecord.getOverPrice());
                for(StockRecordBean oneRecord:records){
                    float compareRate=100f*(dateLinePrice-F(oneRecord.getOverPrice()))/F(oneRecord.getOverPrice());
                    if(compareRate>=upRate){
                        theLastestRecord.touchLineRecord=oneRecord;
                        resultRecords.add(theLastestRecord);
                        break;
                    }
                }
            }
        }
        return resultRecords;
    }

}
