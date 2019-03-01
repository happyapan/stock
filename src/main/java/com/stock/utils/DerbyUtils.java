package com.stock.utils;
import com.stock.StockConstans;
import com.stock.vo.StockRecordBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DerbyUtils {
    private static Connection conn = null;

    private static Connection getDerbtConnection(){
        if(conn==null){
            try{
                String driver = "org.apache.derby.jdbc.EmbeddedDriver";
                Class.forName(driver).newInstance();
                Connection conn = null;
                conn = DriverManager.getConnection(StockConstans.DERBY_DB_PATH);
                return conn;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConnect(){
        if(conn!=null){
            try{
                conn.close();
                conn=null;
            }catch (Exception ex){

            }
        }
    }

    public static void saveStockRecord(StockRecordBean oneStock){
        String insertSql="";
        try {
            Connection derbyConn=getDerbtConnection();
            if(derbyConn!=null){
                Statement s = derbyConn.createStatement();
                String delSql="delete from stock_his where STOCK_DATE='"+oneStock.getStockDate()+"' and STOCK_CODE='"+oneStock.getStockCode()+"'";
//                System.out.println(delSql);
                s.executeUpdate(delSql);
                 insertSql="INSERT INTO STOCK_HIS (STOCK_DATE, STOCK_CODE, STOCK_NAME, OVER_PRICE, HIGH_PRICE, LOW_PRICE, OPEN_PRICE, LAST_OVER_PRICE, PLUS_PRICE, CHANGE_RATE, DONE_COUNT, DONE_PRICE,TOTAL_MARKET_AMOUNT,CAN_SALE_PRICE) VALUES (" ;
                insertSql+="'"+getFormatSet(oneStock.getStockDate())+"',";//STOCK_NAME
                insertSql+="'"+getFormatSet(oneStock.getStockCode().replaceAll("'", ""))+"',";//STOCK_CODE
                insertSql+="'"+""+"',";//STOCK_NAME
                insertSql+= getFormatSet(oneStock.getOverPrice())+",";//OVER_PRICE
                insertSql+= getFormatSet(oneStock.getHighPrice())+",";//HIGH_PRICE
                insertSql+= getFormatSet(oneStock.getLowPrice())+",";//LOW_PRICE
                insertSql+= getFormatSet(oneStock.getOpenPrice())+",";//OPEN_PRICE
                insertSql+= getFormatSet(oneStock.getLastOverPrice())+",";//LAST_OVER_PRICE
                insertSql+= getFormatSet(oneStock.getPlusPrice())+",";//PLUS_PRICE
                insertSql+= getFormatSet(oneStock.getChangeRate())+",";//CHANGE_RATE
                insertSql+= getFormatSet(oneStock.getDoneCount())+",";//DONE_COUNT
                insertSql+= getFormatSet(oneStock.getDonePrice())+",";//DONE_PRICE
                insertSql+= getFormatSet(oneStock.getTotalMarketAmount())+",";//TOTAL_MARKET_AMOUNT
                insertSql+= getFormatSet(oneStock.getCanSalePrice())+"";//CAN_SALE_PRICE
                insertSql+=")";

                byte[] bytes = insertSql.getBytes();
                String sql = new String(bytes,"GBK");
                s.executeUpdate(sql);
            }

        } catch (Exception e) {
            System.out.println("Exception: " + insertSql);
            e.printStackTrace();
        }
    }

    private static String getFormatSet(String value){
        if(value==null || "".equals(value.trim())){
            return "0";
        }
        value=value.trim().toLowerCase();
        if(value.equals("none")){
            return "0";
        }else if(value.indexOf("+")>0){
            return "0";
        }else{
            return value;
        }
    }


}
