package com.stock.utils;

import com.stock.vo.StockRecordBean;

/**
 * Created by Jeff on 2019/2/28.
 */
public class StockRecordUtils {
    public static StockRecordBean formatDataFrom163(String oneStrRecord) {
        //����	��Ʊ����	����	���̼�	��߼�	��ͼ�	���̼�	ǰ����	�ǵ���	�ǵ���	������	�ɽ���	�ɽ����	����ֵ	��ͨ��ֵ
        String[] record = oneStrRecord.split(",");
        if (record.length >= 13) {
            StockRecordBean stockRecordBean = new StockRecordBean();
            //����
            stockRecordBean.setStockDate(record[0]);
            // ��Ʊ����
            stockRecordBean.setStockCode(record[1].replaceAll("'", ""));
            // ����
            stockRecordBean.setStockName(record[2]);
            // ���̼�
            stockRecordBean.setOverPrice(record[3]);
            // ��߼�
            stockRecordBean.setHighPrice(record[4]);
            // ��ͼ�
            stockRecordBean.setLowPrice(record[5]);
            // ���̼�
            stockRecordBean.setOpenPrice(record[6]);
            // 	ǰ����
            stockRecordBean.setLastOverPrice(record[7]);
            // �ǵ���
            stockRecordBean.setPlusPrice(record[8]);
            // �ǵ���
            stockRecordBean.setPlusRate(record[9]);
            // ������
            stockRecordBean.setChangeRate(record[10]);
            // �ɽ���
            stockRecordBean.setDoneCount(record[11]);
            // �ɽ����
            stockRecordBean.setDonePrice(record[12]);

            if (record.length >= 14) {
                // ����ֵ
                stockRecordBean.setTotalMarketAmount(record[13]);
            }
            if (record.length >= 15) {
                // ��ͨ��ֵ
                stockRecordBean.setCanSalePrice(record[14]);
            }
            return stockRecordBean;
        }
        return null;
    }

}
