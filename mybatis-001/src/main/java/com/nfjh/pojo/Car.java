package com.nfjh.pojo;

/**
 * 封装汽车相关信息的pojo类。普通的java类。
 * @author 动力节点
 * @version 1.0
 * @since 1.0
 */
public class Car {
    // 数据库表当中的字段应该和pojo类的属性一一对应。
    // 建议使用包装类，这样可以防止null的问题。
    private Long id;
    private String car_Num;
    private String brand;
    private Double guide_Price;
    private String produce_Time;
    private String car_Type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCar_Num() {
        return car_Num;
    }

    public void setCar_Num(String car_Num) {
        this.car_Num = car_Num;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getGuide_Price() {
        return guide_Price;
    }

    public void setGuide_Price(Double guide_Price) {
        this.guide_Price = guide_Price;
    }

    public String getProduce_Time() {
        return produce_Time;
    }

    public void setProduce_Time(String produce_Time) {
        this.produce_Time = produce_Time;
    }

    public String getCar_Type() {
        return car_Type;
    }

    public void setCar_Type(String car_Type) {
        this.car_Type = car_Type;
    }

    public Car(Long id, String car_Num, String brand, Double guide_Price, String produce_Time, String car_Type) {
        this.id = id;
        this.car_Num = car_Num;
        this.brand = brand;
        this.guide_Price = guide_Price;
        this.produce_Time = produce_Time;
        this.car_Type = car_Type;
    }

    public Car(String car_Num, String brand, Double guide_Price, String produce_Time, String car_Type) {
        this.car_Num = car_Num;
        this.brand = brand;
        this.guide_Price = guide_Price;
        this.produce_Time = produce_Time;
        this.car_Type = car_Type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", car_Num='" + car_Num + '\'' +
                ", brand='" + brand + '\'' +
                ", guide_Price=" + guide_Price +
                ", produce_Time='" + produce_Time + '\'' +
                ", car_Type='" + car_Type + '\'' +
                '}';
    }
}
