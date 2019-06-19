package com.liu.po;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @className: DataInfo
 * @author: yu.liu
 * @date: 2019/6/19 09:34
 * @description:  解析excel中每行的数据
 */
public class DataInfo extends BaseRowModel {

    @ExcelProperty(index = 0)
    private String comName;

    @ExcelProperty(index = 1)
    private String comCode;

    @ExcelProperty(index = 2)
    private String comAddr;

    @ExcelProperty(index = 3)
    private String strName;

    @ExcelProperty(index = 4)
    private String strCode;

    @ExcelProperty(index = 5)
    private String strAddr;

    @ExcelProperty(index = 6)
    private String regName;

    @ExcelProperty(index = 7)
    private String regCode;

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getComAddr() {
        return comAddr;
    }

    public void setComAddr(String comAddr) {
        this.comAddr = comAddr;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getStrCode() {
        return strCode;
    }

    public void setStrCode(String strCode) {
        this.strCode = strCode;
    }

    public String getStrAddr() {
        return strAddr;
    }

    public void setStrAddr(String strAddr) {
        this.strAddr = strAddr;
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getRegCode() {
        return regCode;
    }

    public void setRegCode(String regCode) {
        this.regCode = regCode;
    }
}
