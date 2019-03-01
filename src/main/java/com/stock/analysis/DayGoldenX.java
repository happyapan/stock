package com.stock.analysis;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;

import java.util.ArrayList;
import java.util.List;

public class DayGoldenX {

    /**
     * 找十字星，允许有buffer maxUpRate 和 maxUpPrice 2选1，不然优先使用rate
     * @param stockCodes
     * @param bufferPrice
     * @param compareDate 比较日期
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes,  Float bufferPrice,String compareDate) {
        List<StockRecordBean> resultRecords=new ArrayList<StockRecordBean>();


        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }
        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode,compareDate,compareDate,1);
            if(records==null || records.size()==0){
                continue;
            }
            StockRecordBean compareStock = null;

            if (compareDate == null ||compareDate.equals("")) {
                compareStock = records.get(0);
            } else {
                for (StockRecordBean currntBean : records) {
                    if (currntBean.getStockDate().equals(compareDate)) {
                        compareStock = currntBean;
                        break;
                    }
                }
            }
            if (compareStock == null) {
                continue;
            }

            //涨停,跌停的不要
            if (Math.abs(new Float(compareStock.getPlusRate())) > 9.5f) {
                continue;
            }
            if ((bufferPrice == null || bufferPrice.compareTo(0f) == 0)  ) {
                if (compareStock.getOpenPrice().equals(compareStock.getOverPrice())) {
                    resultRecords.add(compareStock);
                }
            }  else if (bufferPrice !=null ){
                if ( bufferPrice.compareTo(Math.abs(new Float(compareStock.getOpenPrice()) - new Float(compareStock.getOverPrice()))) >= 0){
                    //最发涨跌额度
                    resultRecords.add(compareStock);
                }
            }
        }
        return resultRecords;
    }

}

