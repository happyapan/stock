package com.stock.analysis;

import com.stock.vo.StockRecordBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ccc016 on 2017/11/20.
 */
public class BaseAnalysis {

    public Float F(String value){
        return new Float(value);
    }

    public boolean isEmpty(String value){
        if(value==null || value.trim().length()==0){
            return true;
        }else{
            return false;
        }
    }
    public boolean isNotEmpty(String value){
        return !isEmpty(value);
    }

    public List<String> getStockCodeList(List<StockRecordBean> stockBeans){
        if(stockBeans==null || stockBeans.size()==0){
            return null;
        }
        Set<String> stockCodes=new HashSet<String>();
        for(StockRecordBean one:stockBeans){
            stockCodes.add(one.getStockCode());
        }
        List<String> values=new ArrayList<String>();
        values.addAll(stockCodes);
        return values;
    }

    public List<String> getStockCodeList(StockRecordBean stockBean){
        if(stockBean==null ){
            return null;
        }

        List<String> values=new ArrayList<String>();
        values.add(stockBean.getStockCode());
        return values;
    }
}
