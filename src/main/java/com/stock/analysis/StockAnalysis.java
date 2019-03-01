package com.stock.analysis;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.P;

import java.util.List;

public class StockAnalysis {
    public static void main(String[]args){
        List<String> allStockCodes = StockReadUtils.getAllStockCode();
        P.P(StockRecordBean.getSourceTitle());
        //stocks.add("600001");


//        //日十字星
//        String goldenXDay="2017-11-17";
//        List<StockRecordBean> goldenX= (new DayGoldenX()).anlysis(allStockCodes,0.01f,goldenXDay);
//        P.P(goldenXDay+"--------------十字星");
//        for(StockRecordBean one:goldenX){
//            P.P(one.getSource());
//        }
//
//        //吊颈线
//        String tlinesDay="2017-11-17";
//        List<StockRecordBean> tlines= (new TLine()).anlysis(allStockCodes,0.03f,null,tlinesDay);
//
//        P.P(tlinesDay+"--------------吊颈线");
//        for(StockRecordBean one:tlines){
//            P.P(one.getSource());
//        }
//
//        //锤头线
//        String tClinesDay="2017-11-17";
//        List<StockRecordBean> t_C_lines= (new T_C_Line()).anlysis(allStockCodes,0.03f,null,tClinesDay);
//        P.P(tClinesDay+"--------------锤头线");
//        for(StockRecordBean one:t_C_lines){
//            P.P(one.getSource());
//        }
//
//        //几日内跌幅
//        int downDay=20;
//        float downRate=50f;
//        String downRatesDay="2017-11-17";
//        List<StockRecordBean> downRates= (new DaysDownRate()).anlysis(allStockCodes,downRate,downRatesDay,downDay);
//        P.P(downRatesDay+"--------------"+downDay+"交易日内跌幅达到"+downRate+"%");
//        for(StockRecordBean one:downRates){
//            P.P(one.getSource());
//            P.P(one.touchLineRecord.getSource());
//        }
//
//        //几日涨幅
//        int upDay=20;
//        float upRate=30f;
//        String upRatesDay="2017-11-17";
//        List<StockRecordBean> upRates= (new DaysUpRate()).anlysis(allStockCodes,upRate,upRatesDay,upDay);
//        P.P(upRatesDay+"--------------"+upDay+"交易日内涨幅达到"+upRate+"%");
//        for(StockRecordBean one:upRates){
//            P.P(one.getSource());
//            P.P(one.touchLineRecord.getSource());
//        }
//
//        //几日K线一直是红的
//        int continueRedDayCount=3;
//        float minUpPrice=0.05f;
//        String continueRedDay="2017-11-17";
//        List<StockRecordBean> continueRed= (new ContinueDayKRed()).anlysis(allStockCodes,minUpPrice,continueRedDay,continueRedDayCount);
//        P.P(continueRedDay+"--------------"+continueRedDayCount+"交易日内K线连续红线，并且收盘价高于开盘价"+minUpPrice+"");
//        for(StockRecordBean one:continueRed){
//            P.P(one.getSource());
//            for(StockRecordBean two:one.compareSuccessRecords){
//                P.P(two.getSource());
//            }
//        }
//
//        //几日K线一直是绿的
//        int continueGreenDayCount=4;
//        float minDownPrice=0.05f;
//        String continueRedDay2="2017-11-17";
//        List<StockRecordBean> continueGreen= (new ContinueDayKGreen()).anlysis(allStockCodes,minDownPrice,continueRedDay2,continueGreenDayCount);
//        P.P(continueRedDay2+"--------------"+continueGreenDayCount+"交易日内K线连续绿线，并且收盘价低于开盘价"+minDownPrice+"");
//        for(StockRecordBean one:continueGreen){
//            P.P(one.getSource());
//            for(StockRecordBean two:one.compareSuccessRecords){
//                P.P(two.getSource());
//            }
//        }
//
//        //跌破N日均线
//        int avgLineDayCount=20;
//        float minBufferPrice=0.05f;
//        String avgLineAnalysisDate="2017-11-17";
//        List<StockRecordBean> continueGreen2= (new AvgLinePriceDownBack()).anlysis(allStockCodes, minBufferPrice, avgLineAnalysisDate, avgLineDayCount);
//        P.P(avgLineAnalysisDate+"--------------向下触及"+avgLineDayCount+"日均线Buffer为"+minBufferPrice);
//        for(StockRecordBean one:continueGreen2){
//            P.P(one.getSource());
//            for(StockRecordBean two:one.compareSuccessRecords){
//                P.P(two.getSource());
//            }
//        }

        //跌破N日均线
        int belowAllAvgDayCount=35;
        float belowAllAvgBufferPrice=0.01f;
        String belowAllAvgDate="2017-12-26";
        int[] belowAllAvgLines={5,10,20,30};
        List<StockRecordBean> belowAllAvg= (new BelowAllAvgLine()).anlysis(allStockCodes, belowAllAvgDate, belowAllAvgLines,belowAllAvgDayCount, belowAllAvgBufferPrice);
        P.P("至"+belowAllAvgDate+"-------5日线-------连续"+belowAllAvgDayCount+"天一直在均线"+"10,20,30（平行）"+"下方，Buffer为"+belowAllAvgBufferPrice);
        for(StockRecordBean one:belowAllAvg){
            P.P(one.getSource());
        }

    }
}
