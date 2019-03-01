package com.stock.analysis;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 计算第一条均线突破第二条均线
 */
public class AvgLineCrossBreak extends BaseAnalysis {
    /**
     * 计算第一条均线突破第二条均线
     *
     * @param stockCodes
     * @param compareDate
     * @param downLine    下方N天均线(
     * @param upLine      上方N天均线
     * @param holdDay     保持上下关系天数
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, String compareDate, int downLine, int upLine, int holdDay) {
        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }

        if (isEmpty(compareDate)) {
            compareDate = DateUtil.GET_CURRENT_DATE();
        }

        int dataCount = 0;
        if (downLine > upLine) {
            dataCount = holdDay + downLine;
        } else {
            dataCount = holdDay + upLine;
        }

        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, compareDate, null, dataCount);
            if(records.size()!=dataCount){
                //数据不够，就算了
                continue;
            }
            Float downLineAvg=0f;
            Float upLineAvg=0f;
            //计算均值
            for(int i=dataCount-1;i>=holdDay;i--){
                if(i<=(downLine+holdDay-1)){
                    downLineAvg=downLineAvg+F(records.get(i).getOverPrice());
                }
                if(i<=(upLine+holdDay-1)){
                    upLineAvg=upLineAvg+F(records.get(i).getOverPrice());
                }
            }

            downLineAvg=downLineAvg/downLine;
            upLineAvg=upLineAvg/upLine;

            //开始比较
            for(int i=holdDay-1;i>=0;i--){
                Float currentOverPrice=F(records.get(i).getOverPrice());
                downLineAvg=downLineAvg+(currentOverPrice-F(records.get(downLine+i).getOverPrice()))/downLine;
                upLineAvg=upLineAvg+(currentOverPrice-F(records.get(upLine+i).getOverPrice()))/upLine;

                int compareResult=downLineAvg.compareTo(upLineAvg);
                if(i>0){
                    if(compareResult>0){
                        //已经突破
                        break;
                    }
                }else if(i==0){
                    if(compareResult>=0){
                        //X
                        resultRecords.add(records.get(0));
                    }
                }
            }


        }
        return resultRecords;
    }
}
