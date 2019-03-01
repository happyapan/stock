package com.stock.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccc016 on 2017/11/17.
 */
public class StockRecordBean {

    //-------------分析比较存储区

    public List<StockRecordBean> compareSuccessRecords;

    public StockRecordBean touchLineRecord;

    public StockRecordBean(){
        compareSuccessRecords=new ArrayList<StockRecordBean>();
    }

    //原始数据
    private String source;
    //日期
    private String stockDate;
    // 股票代码
    private String stockCode;
    // 名称
    private String stockName;
    // 收盘价
    private String overPrice;
    // 最高价
    private String highPrice;
    // 最低价
    private String lowPrice;
    // 开盘价
    private String openPrice;
    // 	前收盘
    private String lastOverPrice;
    // 涨跌额
    private String plusPrice;
    // 涨跌幅
    private String plusRate;
    // 换手率
    private String changeRate;
    // 成交量
    private String doneCount;
    // 成交金额
    private String donePrice;
    // 总市值
    private String totalMarketAmount;
    // 流通市值
    private String canSalePrice;

    public String getStockDate() {
        return stockDate;
    }

    public void setStockDate(String stockDate) {
        this.stockDate = stockDate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getOverPrice() {
        return overPrice;
    }

    public void setOverPrice(String overPrice) {
        this.overPrice = overPrice;
    }

    public String getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(String highPrice) {
        this.highPrice = highPrice;
    }

    public String getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(String lowPrice) {
        this.lowPrice = lowPrice;
    }

    public String getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(String openPrice) {
        this.openPrice = openPrice;
    }

    public String getLastOverPrice() {
        return lastOverPrice;
    }

    public void setLastOverPrice(String lastOverPrice) {
        this.lastOverPrice = lastOverPrice;
    }

    public String getPlusPrice() {
        return plusPrice;
    }

    public void setPlusPrice(String plusPrice) {
        this.plusPrice = plusPrice;
    }

    public String getPlusRate() {
        return plusRate;
    }

    public void setPlusRate(String plusRate) {
        this.plusRate = plusRate;
    }

    public String getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(String changeRate) {
        this.changeRate = changeRate;
    }

    public String getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(String doneCount) {
        this.doneCount = doneCount;
    }

    public String getDonePrice() {
        return donePrice;
    }

    public void setDonePrice(String donePrice) {
        this.donePrice = donePrice;
    }

    public String getTotalMarketAmount() {
        return totalMarketAmount;
    }

    public void setTotalMarketAmount(String totalMarketAmount) {
        this.totalMarketAmount = totalMarketAmount;
    }

    public String getCanSalePrice() {
        return canSalePrice;
    }

    public void setCanSalePrice(String canSalePrice) {
        this.canSalePrice = canSalePrice;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFormatData(){
        return this.stockDate+",'"+this.stockCode+","+this.stockName+",开"+this.openPrice+",收"+this.getOverPrice()+",Rate"+this.getPlusRate()+"%,昨收"+this.lastOverPrice;
    }
    public static String getFormatTitle(){
        return "日期,编码,名称,开盘,收盘,涨幅,昨收";
    }
    public static String getSourceTitle(){
        return "日期,股票代码,名称,收盘价,最高价,最低价,开盘价,前收盘,涨跌额,涨跌幅,换手率,成交量,成交金额,总市值,流通市值";
    }

    public static float F_VALUE(String value){
        return new Float(value);
    }



}
