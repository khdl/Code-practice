package com.liu.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.liu.po.DataInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: DataExcelListener
 * @author: yu.liu
 * @date: 2019/6/19 09:58
 * @description: 解析过程中的监听器
 * 每解析一行会回调invoke()方法。
 * 整个excel解析结束会执行doAfterAllAnalysed()方法
 */
public class DataExcelListener extends AnalysisEventListener {

    private List<Object> datas = new ArrayList<>();
    @Override
    public void invoke(Object object, AnalysisContext context) {
        System.out.println("当前行：" + context.getCurrentRowNum());
        System.out.println(object);
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
        //根据自己业务做处理
        doSomething(object);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
    private void doSomething(Object object) {
        //1、入库调用接口
    }
}
