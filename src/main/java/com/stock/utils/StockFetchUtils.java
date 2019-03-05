package com.stock.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.utils.DateUtil;
import com.common.utils.HttpConnectionUtils;
import com.common.utils.P;
import com.common.utils.StringUtils;
import com.stock.StockConstans;
import com.stock.vo.StockRecordBean;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.io.FileUtils;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Jeff on 2019/2/28.
 */
public class StockFetchUtils {
    public static void saveAllStockCodeFromWeb() throws Exception {
        // check https://www.nowapi.com/api/finance.stock_list
        URL u=new URL("http://api.k780.com/?app=finance.stock_list&category=hs&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=json");
//        {
//            "success": "1",
//            "result": {
//                "totline": "4839",
//                "disline": "4839",
//                 "page": "1",
//                 "lists":
//                    [
//                        {
//                            "stoid": "1",
//                             "symbol": "sh600000",
//                             "sname": "浦发银行"
//                         }
//                    ]
//            }
//        }
        InputStream in=u.openStream();
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        try {
            byte buf[]=new byte[1024];
            int read = 0;
            while ((read = in.read(buf)) > 0) {
                out.write(buf, 0, read);
            }
        }  finally {
            if (in != null) {
                in.close();
            }
        }
        byte b[]=out.toByteArray( );
        JSONObject jsonObject = JSON.parseObject(new String(b, "utf-8"));
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray=result.getJSONArray("lists");
        List<String> allStockCode=new ArrayList<String>();
        for (Object obj : jsonArray) {
            JSONObject oneStock = (JSONObject) obj;
            allStockCode.add(oneStock.getString("symbol") + StockConstans.SPLIT + oneStock.getString("sname"));
        }
        FileUtils.writeLines(new File(StockConstans.ALL_STOCK_FILE_PATH), allStockCode, false);
    }

    /**
     * 获取股票交易信息存储临时文件中
     *
     * @param stockCode
     * @param startDate
     * @param endData
     * @return
     */
    private static String fetchSaleDataToTempFileFrom163(String stockCode, String startDate, String endData) {

        if (stockCode.startsWith("6")) {
            stockCode = "0" + stockCode;
        } else if (stockCode.startsWith("0")) {
            stockCode = "1" + stockCode;
        } else if (stockCode.startsWith("3")) {
            stockCode = "1" + stockCode;
        } else {
            return null;
        }
        String fileName = stockCode + UUID.randomUUID().toString() + ".csv";
        String stockHead = "http://quotes.money.163.com/service/chddata.html?code=";
        String dateScope = "&start=" + startDate.replaceAll("-","") + "&end=" + endData.replaceAll("-","");
        String stockEnd = "&fields=TCLOSE;HIGH;LOW;TOPEN;LCLOSE;CHG;PCHG;TURNOVER;VOTURNOVER;VATURNOVER;TCAP;MCAP";
        String dataUrl = stockHead + stockCode + dateScope + stockEnd;
//        P.P(dataUrl);
        try {
            HttpConnectionUtils.storeFileFromHttp(dataUrl, StockConstans.STOCK_FILE_TEMP_PATH + "\\" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
//        System.out.println("Save Data to  " + fileName);
        return fileName;
    }

    public static void refreshStockData(List<String>stockCodes){

        if(stockCodes==null || stockCodes.size()==0){
            stockCodes=StockReadUtils.getAllStockCode();
        }
        int totalSize=stockCodes.size();
        for(String stockCode:stockCodes){
            refreshStockData(stockCode);
            P.P("remain +"+--totalSize);
        }
    }

    public static void refreshStockData(String stockCode){
        boolean isExistOldFile=false;
        String startDate= DateUtil.THREE_YEAR_AGE;
        String endData= DateUtil.GET_CURRENT_DATE();
        List<StockRecordBean> records =StockReadUtils.getOneStockTotalData(stockCode, null,DateUtil.THREE_YEAR_AGE , 9000);
        if(records!=null && records.size()>0){
            isExistOldFile=true;
            StockRecordBean lastestStockRecord=records.get(0);
            startDate=DateUtil.GET_DATE_AFTER_ONE_DAY(lastestStockRecord.getStockDate());
        }

        if(endData.compareTo(startDate)<0){
            P.P("No Need Refresh "+stockCode + " From " + startDate + " To " + endData);
        }
        String tempStockFileName=fetchSaleDataToTempFileFrom163(stockCode,startDate,endData);

        appendStockDate(stockCode, tempStockFileName, isExistOldFile);

        P.P("Refresh " + stockCode + " From " + startDate + " To " + endData);

        try {
            FileUtils.forceDelete(new File(StockConstans.STOCK_FILE_TEMP_PATH + "\\" + tempStockFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void appendStockDate(String stockCode,String tempStockFileName,boolean isExistOldFile){
        try {
            List<String> newStockData=FileUtils.readLines(new File(StockConstans.STOCK_FILE_TEMP_PATH + "\\" + tempStockFileName));
            List<String> validateStockData=new ArrayList<String>();
            boolean isExistData=false;
            if(newStockData!=null && newStockData.size()>1 ){
                if(!isExistOldFile){
                    validateStockData.add(StockConstans.STOCK_FILE_TITLE);
                }
                for(int i=newStockData.size()-1;i>0;i--){
                    if(StringUtils.isNotEmpty(newStockData.get(i))){
                        if(newStockData.get(i).indexOf("None")==-1){
                            validateStockData.add(newStockData.get(i));
                            isExistData=true;
                        }
                    }
                }
            }
            if(isExistData){
                FileUtils.writeLines(new File(StockConstans.STOCK_FILE_READ_PATH + "\\" + stockCode + StockConstans.DATA_FILE_TYPE), validateStockData, true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
