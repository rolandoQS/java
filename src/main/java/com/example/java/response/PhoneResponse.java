package com.example.java.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


public class PhoneResponse {
    private UUID id;

    @JsonProperty
    private Long number;

    @JsonProperty
    private Integer city_code;

    @JsonProperty
    private Integer contry_code;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
