package com.stock.utils;



import com.common.utils.P;
import com.stock.StockConstans;
import com.stock.vo.StockRecordBean;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ccc016 on 2017/11/17.
 */
public class StockReadUtils {
//    public static List<StockRecordBean> records

    /**
     * 获的所有股票代码
     * @return
     */
    public static List<String> getAllStockCode(){
        List<String> allStockCode=new ArrayList<String>();
        File file = new File(StockConstans.ALL_STOCK_FILE_PATH);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String stockCode = null;

            while ((stockCode = reader.readLine()) != null) {
                String oneStockCode=stockCode.split(StockConstans.SPLIT)[0].replace("sh","").replace("sz","");
                allStockCode.add(oneStockCode);
            }
        }catch (Exception ex){


        }
        return allStockCode;
    }

    /**
     * 获得一个代码的所有记录
     * @param code
     * @param endDate 结束时间
     * @param beginDate 开始时间
     * @return  最新的数据排在上面
     */
    public static List<StockRecordBean> getOneStockTotalData(String code,String endDate,String beginDate,Integer maxDataCount){
        if(maxDataCount==null || maxDataCount.intValue()==0){
            maxDataCount=Integer.MAX_VALUE;
        }
        List<StockRecordBean> records=new ArrayList<StockRecordBean>();
        try {
            File stockFile=new File(StockConstans.STOCK_FILE_READ_PATH+"\\"+code+StockConstans.DATA_FILE_TYPE);
            BufferedReader reader = null;
            if(stockFile.exists()){
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(stockFile),"GBK"));
                String oneRecord = null;
                int lineCount=0;
                while ((oneRecord = reader.readLine()) != null) {
                    lineCount++;
                    if(lineCount>1){
                        StockRecordBean one= StockRecordUtils.formatDataFrom163(oneRecord);

                        if("".equals(one.getOverPrice()) || "0".equals(one.getOverPrice()) || "0.0".equals(one.getOverPrice()) ){
                            continue;
                        }
                        if(endDate !=null && !"".equals(endDate)){
                            //当前记录时间大于
                            if(one.getStockDate().compareTo(endDate)>0){
                                continue;
                            }
                        }
                        if(beginDate !=null && !"".equals(beginDate)){
                            //当前记录时间小于
                            if(one.getStockDate().compareTo(beginDate)<0){
                                continue;
                            }
                        }
                        records.add(one);
                        if(records.size()>=maxDataCount){
                            break;
                        }
                    }
                }
            }
        }catch (Exception ex){

        }
        Collections.reverse(records);
        return records;
    }

    /**
     * 获得当天的数据
     * @param code
     * @param date
     * @return
     */
    public static StockRecordBean getStockDataOneDay(String code,String date){
        List<StockRecordBean> datas=getOneStockTotalData(code,date,date,1);
        if(datas==null || datas.size()==0){
            return null;
        }else{
            return datas.get(0);
        }

    }


    /**
     * 计算涨幅
     * @param baseStock
     * @param compareDate
     * @return
     */
    public static Float calUpRate(StockRecordBean baseStock,String compareDate){
        List<StockRecordBean> records = getOneStockTotalData(baseStock.getStockCode(), compareDate, compareDate, 1);
        if(records==null || records.size()==0){
            return null;
        }
        StockRecordBean compareDateStock=records.get(0);
//        P.P(baseStock.getSource());
//        P.P(compareDateStock.getSource());
        return 100f*(F(compareDateStock.getOverPrice())-F(baseStock.getOverPrice()))/F(baseStock.getOverPrice());
    }

    /**
     * 计算几天之后的涨幅涨幅
     * @param baseStock
     * @param afterCount
     * @return
     */
    public static Float calUpRateAfter(StockRecordBean baseStock,int afterCount,boolean isShowDetail){

        StockRecordBean compareDateStock=getAfterDayStock(baseStock,afterCount);
        if(compareDateStock==null){
            return null;
        }

        Float result=100f*(F(compareDateStock.getOverPrice())-F(baseStock.getOverPrice()))/F(baseStock.getOverPrice());
        if(isShowDetail){
            P.P("------------->" + baseStock.getStockCode() + "[" + baseStock.getStockName() + "] " + baseStock.getStockDate() + " TO " + compareDateStock.getStockDate() + " Change Rate: " + result.floatValue());
            P.P(baseStock.getSource());
            P.P(compareDateStock.getSource());
        }
        return result;
    }

    /**
     * 获得N个交易日之后的数据
     * @param baseStock
     * @param afterCount
     * @return
     */
    public static StockRecordBean getAfterDayStock(StockRecordBean baseStock,int afterCount){
        List<StockRecordBean> records = getOneStockTotalData(baseStock.getStockCode(), null, baseStock.getStockDate(), 9999);
        //最后一天的数据是baseStock，所以也要排除
        if(records==null || records.size()<=afterCount){
            return null;
        }
        return records.get(records.size()-1-afterCount);
    }


    public static Float F(String value){
        return new Float(value);
    }


    public  static void main(String[]args){
        StockRecordBean baseRecord= StockRecordUtils.formatDataFrom163("2017-11-14,'601919,中远海控,6.84,6.85,6.6,6.77,6.79,0.05,0.7364,0.4172,31854044,214296881.0,69879316601.9,52228012601.9");
        P.P("" + calUpRateAfter(baseRecord, 3, true).floatValue());
        P.P("" + calUpRate(baseRecord, "2017-11-15").floatValue());
        P.P(getAfterDayStock(baseRecord, 1).getSource());

    }

}
