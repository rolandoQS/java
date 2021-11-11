package com.example.java.Request;



public class CreatePhoneRequest {

    private Long number;

    private Integer city_code;

    private Integer contry_code;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getCity_code() {
        return city_code;
    }

    public void setCity_code(Integer city_code) {
        this.city_code = city_code;
    }

    public Integer getContry_code() {
        return contry_code;
    }

    public void setContry_code(Integer contry_code) {
        this.contry_code = contry_code;
    }
}
