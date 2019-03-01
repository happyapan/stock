package com;

import com.stock.utils.StockFetchUtils;
import com.stock.utils.StockReadUtils;

import java.util.Arrays;

/**
 * Created by Jeff on 2019/2/28.
 */
public class BuildDateMain {
    public static void main(String[]agrs){
        StockFetchUtils.refreshStockData(Arrays.asList("600770"));
//        StockFetchUtils.refreshStockData(StockReadUtils.getAllStockCode());
    }

}
