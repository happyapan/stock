package com.stock.analysis;

import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.P;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断当前时间点，在所有均线下方
 */
public class BelowAllAvgLine extends BaseAnalysis {

    /**
     *
     * @param stockCodes
     * @param dateLine 比较点时间
     * @param lines 均线列表，从小到大排列，比如[5,10,20,30]
     * @param shortRemainDay 最小维持天数
     * @param minBuffer 计算Buffer
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes,String dateLine,int[]lines,int shortRemainDay, Float minBuffer) {
        if(minBuffer==null){
            minBuffer=0f;
        }
        if(lines.length!=4){
            P.P("均线参数的数据不为4，不处理");
            return null;
        }
        if(lines[0]< lines[1] && lines[1]< lines[2]  && lines[2]< lines[3] ){

        }else{
            P.P("均线参数的数据不是从小到大排列，不处理");
            return null;
        }



        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }
        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, dateLine, null, lines[3] +shortRemainDay);
            if(records.size()!=lines[3] +shortRemainDay){
                //数据不够分享，跳过
                continue;
            }
//            if(stockCode.equals("601919")){
//                P.P(stockCode);
//            }
            Float line4Ave=0f;
            Float line3Ave=0f;
            Float line2Ave=0f;
            Float line1Ave=0f;
            //计算 比较日期前的平均值
            for(int i=lines[3] +shortRemainDay-1;i>=0;i--){
                Float currentOverPrice=F(records.get(i).getOverPrice());
                if(i<(lines[3] +shortRemainDay)){
                    line4Ave=line4Ave+currentOverPrice;
                }
                if(i<(lines[2] +shortRemainDay)){
                    line3Ave=line3Ave+currentOverPrice;
                }
                if(i<(lines[1] +shortRemainDay)){
                    line2Ave=line2Ave+currentOverPrice;
                }
                if(i<(lines[0] +shortRemainDay)){
                    line1Ave=line1Ave+currentOverPrice;
                }
                if(i<=shortRemainDay){
                    break;
                }
            }
            line4Ave=line4Ave/lines[3];
            line3Ave=line3Ave/lines[2];
            line2Ave=line2Ave/lines[1];
            line1Ave=line1Ave/lines[0];

            //开始比较均线连续在下方
            for(int i =shortRemainDay-1;i>=0;i--){
                Float currentOverPrice=F(records.get(i).getOverPrice());
                line4Ave=line4Ave+(currentOverPrice-F(records.get(i+lines[3]).getOverPrice()))/lines[3];
                line3Ave=line3Ave+(currentOverPrice-F(records.get(i+lines[2]).getOverPrice()))/lines[2];
                line2Ave=line2Ave+(currentOverPrice-F(records.get(i+lines[1]).getOverPrice()))/lines[1];
                line1Ave=line1Ave+(currentOverPrice-F(records.get(i+lines[0]).getOverPrice()))/lines[0];
                if(i>0){
                    //任何一条短平均线突破长平均线，都过滤掉
                    if(line1Ave.compareTo(line2Ave)>0 || line2Ave.compareTo(line3Ave)>0 || line3Ave.compareTo(line4Ave)>0 ){
                        break;
                    }
                }else if(i==0){
                    if(line1Ave.compareTo(line2Ave+minBuffer)<=0 && line2Ave.compareTo(line3Ave+minBuffer)<=0 && line3Ave.compareTo(line4Ave+minBuffer)<=0){
                        resultRecords.add(records.get(0));
                    }
                }
            }
        }
        return resultRecords;
    }
}
