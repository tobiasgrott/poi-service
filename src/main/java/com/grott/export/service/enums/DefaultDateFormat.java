/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.service.enums;

/**
 *
 * @author tobia
 */
public enum DefaultDateFormat {
    DATETIME("m/d/yy h:mm"), //
    DATE("m/d/yy"), //
    TIME("h:mm");
    private final String format;
    private DefaultDateFormat(String format){
        this.format = format;
    }
    
    public String getFormat(){
        return this.format;
    }
}
