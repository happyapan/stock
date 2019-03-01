package com.stock.analysis;


import com.stock.utils.StockReadUtils;
import com.stock.vo.StockRecordBean;
import com.common.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 突破N天均线
 */
public class AvgLinePriceBreak extends BaseAnalysis {

    /**
     * 价格突破N天均线。（至少N天一直在均线下方）
     * @param stockCodes
     * @param minBuffer 触点的buffer
     * @param dateLine 最后计算日期
     * @param dayCount 计算天数
     * @return
     */
    public List<StockRecordBean> anlysis(List<String> stockCodes, Float minBuffer,String dateLine,Integer dayCount) {
        List<StockRecordBean> resultRecords = new ArrayList<StockRecordBean>();
        if (stockCodes == null || stockCodes.size() == 0) {
            stockCodes = StockReadUtils.getAllStockCode();
        }

        if(isEmpty(dateLine)){
            dateLine= DateUtil.GET_CURRENT_DATE();
        }
        if(dayCount== null){
            return null;
        }
        for (String stockCode : stockCodes) {
            List<StockRecordBean> records = StockReadUtils.getOneStockTotalData(stockCode, dateLine, null, dayCount*2);
            if(records!=null && records.size()==dayCount*2){//数据不够就不用算了
                //比如要计算20天均线，就要先算前21~40天的均线值
                Float avgPrice=0f;
                for(int i=dayCount;i<dayCount*2;i++){
                    avgPrice=avgPrice+F(records.get(i).getOverPrice());
                }
                avgPrice=avgPrice/dayCount;//前N天的均价

                //日后每一天的价格都应该大于均价（保持在均线上方）
                for(int i=dayCount-1;i>=0;i--){
                    //均值*20-队列最后一位价+当前计算位置的价格
                    avgPrice=avgPrice*dayCount-F(records.get(dayCount+i).getOverPrice())+F(records.get(i).getOverPrice());
                    avgPrice=avgPrice/dayCount;//当前均价
                    if(i>0){
                        if(F(records.get(i).getOverPrice()).compareTo(avgPrice)>0){
                            //已经突破20均线
                            break;
                        }
                    }else if(i==0){
                        //一直在均线上方，开始比较分析日当天的值
                        if(F(records.get(i).getOverPrice()).compareTo(avgPrice)>=0){
                            //破了均线
                            resultRecords.add(records.get(0));
                        }else  if(minBuffer!=null){
                            //有buffer
                            Float compareDayDiff= Math.abs(F(records.get(i).getOverPrice()) - avgPrice);
                            if(compareDayDiff.compareTo(minBuffer)<=0){
                                resultRecords.add(records.get(0));
                            }
                        }
                    }
                }
            }
        }
        return resultRecords;
    }

}
