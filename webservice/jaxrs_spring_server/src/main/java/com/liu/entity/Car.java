package com.liu.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: Car
 * @Auther: yu
 * @Date: 2019/2/19 15:39
 * @Description:
 */
@XmlRootElement(name="Car")
public class Car {
    private Integer id;
    private String carname;
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
