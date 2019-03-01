package com.stock.strategy;

import com.stock.calculate.DayGoldenX;
import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.stock.analysis.*;
import com.common.utils.P;

import java.util.ArrayList;
import java.util.List;

/**
 * 思路：超跌，反弹
 * 1.连续20天 日线5<10<20<30
 * 2.最近3天有特殊线（十字星，T 或倒T）
 */
public class C1 extends BaseAnalysis {

    public void anlysis(){
        List<String> allStockCodes = StockReadUtils.getAllStockCode();
        P.P(StockRecordBean.getSourceTitle());
        //跌破N日均线
        int belowAllAvgDayCount=10;
        float belowAllAvgBufferPrice=0.01f;
        String belowAllAvgDate="2017-12-26";
        int[] belowAllAvgLines={5,10,20,30};
        List<StockRecordBean> belowAllAvg= (new BelowAllAvgLine()).anlysis(allStockCodes, belowAllAvgDate, belowAllAvgLines,belowAllAvgDayCount, belowAllAvgBufferPrice);
        P.P("至" + belowAllAvgDate + "-------5日线-------连续" + belowAllAvgDayCount + "天一直在均线" + "10,20,30（平行）" + "下方，Buffer为" + belowAllAvgBufferPrice);

        List<StockRecordBean> fiterResults=new ArrayList<StockRecordBean>();

        for(StockRecordBean one:belowAllAvg){
            for(int i=1;i<=3;i++){
                StockRecordBean afterNDayStock= StockReadUtils.getAfterDayStock(one, i);
                if(afterNDayStock==null){
                    continue;
                }
                //十字星
                DayGoldenX goldenX =new DayGoldenX();
                List<StockRecordBean> golds=goldenX.anlysis(this.getStockCodeList(afterNDayStock), 2, afterNDayStock.getStockDate(), afterNDayStock.getStockDate());

                //吊颈线
                TLine tLine=new TLine();
                List<StockRecordBean> ts=tLine.anlysis(this.getStockCodeList(afterNDayStock),0.02f,20f,afterNDayStock.getStockDate());

                //锤头线
                T_C_Line tcLine=new T_C_Line();
                List<StockRecordBean> tcs=tcLine.anlysis(this.getStockCodeList(afterNDayStock),0.02f,20f,afterNDayStock.getStockDate());

                fiterResults.addAll(golds);
                fiterResults.addAll(ts);
                fiterResults.addAll(tcs);
            }
        }

        if (fiterResults.size() > 0) {
//            List<String> fiterStocks = this.getStockCodeList(fiterResults);
//            for (String stockCode : fiterStocks) {
//                StockRecordBean oneDayStock=StockReadUtils.getStockDataOneDay(stockCode,belowAllAvgDate);
//                int compareDayCount=3;
//                P.P("Check "+ stockCode+" From "+ belowAllAvgDate+ " "+compareDayCount+" Days Rate");
//                if(oneDayStock!=null){
//                    StockReadUtils.calUpRateAfter(oneDayStock,compareDayCount,true);
//                }
//
//            }

            for (StockRecordBean one : fiterResults) {

                int compareDayCount=5;
                P.P("Check "+ one.getStockCode()+" From "+ one.getStockDate()+ " "+compareDayCount+" Days Rate");
                 StockReadUtils.calUpRateAfter(one, compareDayCount, true);


            }

//            for (StockRecordBean one : fiterResults) {
//                P.P(one.getSource());
//            }
        }
    }

    public  static  void main(String[]args){
        (new C1()).anlysis();
    }
}
