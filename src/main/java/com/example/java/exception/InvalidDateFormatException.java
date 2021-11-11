package com.example.java.exception;

import java.util.Collection;

public class InvalidDateFormatException extends ApiException{

    private String date;
    private Collection<String> formats;

    public InvalidDateFormatException(String date, Collection<String> formats) {
        super("Error parseando fecha", "El valor " + date + " no se puede parsear usando los formatos aceptados: " + String.join(",", formats), 420);
        this.date = date;
        this.formats = formats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Collection<String> getFormats() {
        return formats;
    }

    public void setFormats(Collection<String> formats) {
        this.formats = formats;
    }
}
