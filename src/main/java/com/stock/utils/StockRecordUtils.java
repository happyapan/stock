package com.stock.utils;

import com.stock.vo.StockRecordBean;

/**
 * Created by Jeff on 2019/2/28.
 */
public class StockRecordUtils {
    public static StockRecordBean formatDataFrom163(String oneStrRecord) {
        //日期	股票代码	名称	收盘价	最高价	最低价	开盘价	前收盘	涨跌额	涨跌幅	换手率	成交量	成交金额	总市值	流通市值
        String[] record = oneStrRecord.split(",");
        if (record.length >= 13) {
            StockRecordBean stockRecordBean = new StockRecordBean();
            //日期
            stockRecordBean.setStockDate(record[0]);
            // 股票代码
            stockRecordBean.setStockCode(record[1].replaceAll("'", ""));
            // 名称
            stockRecordBean.setStockName(record[2]);
            // 收盘价
            stockRecordBean.setOverPrice(record[3]);
            // 最高价
            stockRecordBean.setHighPrice(record[4]);
            // 最低价
            stockRecordBean.setLowPrice(record[5]);
            // 开盘价
            stockRecordBean.setOpenPrice(record[6]);
            // 	前收盘
            stockRecordBean.setLastOverPrice(record[7]);
            // 涨跌额
            stockRecordBean.setPlusPrice(record[8]);
            // 涨跌幅
            stockRecordBean.setPlusRate(record[9]);
            // 换手率
            stockRecordBean.setChangeRate(record[10]);
            // 成交量
            stockRecordBean.setDoneCount(record[11]);
            // 成交金额
            stockRecordBean.setDonePrice(record[12]);

            if (record.length >= 14) {
                // 总市值
                stockRecordBean.setTotalMarketAmount(record[13]);
            }
            if (record.length >= 15) {
                // 流通市值
                stockRecordBean.setCanSalePrice(record[14]);
            }
            return stockRecordBean;
        }
        return null;
    }

}
