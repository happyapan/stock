package com.stock.analysis;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 锤头线
 */
public class T_C_Line extends BaseAnalysis {
    /**
     * 找吊锤头线，允许有buffer  maxUpPrice
     * @param stockCodes
     * @param maxBuffer  锤头线下面条线和全天最低值的差距，为空默认设置为0
     * @param hangRate  锤头线实体的最小比值 为空就默认设置为25%
     * @param compareDate 比较日期
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, Float maxBuffer,Float hangRate,String compareDate) {
        if (maxBuffer == null) {
            maxBuffer=0f;
        }

        if(hangRate==null){
            hangRate=25f;
        }

        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();

        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }
        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, compareDate, compareDate,1);
            if(records==null || records.size()==0){
                continue;
            }
            StockRecordBean currentRecord=records.get(0);
            //1.比较收盘价 和 吊颈线上面条线和全天最高值的差距

            Float openPrice=F(currentRecord.getOpenPrice());
            Float overPrice=F(currentRecord.getOverPrice());
            Float comparePrice=openPrice.compareTo(overPrice)>0?overPrice:openPrice;
            Float lowPrice=F(currentRecord.getLowPrice())+maxBuffer;
            //锤头线下面条线 要小于等于最低
            if(comparePrice.compareTo(lowPrice)<=0){
                //2. 吊颈线实体的最小比值
                //100*（开盘价-收盘价）/(最高价-最低价)
                Float rate=100f*(Math.abs(openPrice - overPrice))/(
                F(currentRecord.getHighPrice())-F(currentRecord.getLowPrice()));
                if(rate.compareTo(hangRate)<=0){
                    resultRecords.add(currentRecord);
                }
            }
        }
        return resultRecords;
    }
}
