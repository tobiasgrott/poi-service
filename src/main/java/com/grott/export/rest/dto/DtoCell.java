/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest.dto;

/**
 *
 * @author tobia
 */
public class DtoCell {
    private String value;
    private CellTypeEnum type;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public CellTypeEnum getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(CellTypeEnum type) {
        this.type = type;
    }
    
    
}
