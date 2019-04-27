/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest.dto;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author tobia
 */
@Schema(type=SchemaType.OBJECT, implementation=DtoCell.class)
public class DtoCell {

    @Schema(type = SchemaType.STRING)
    private String value;
    @Schema(type = SchemaType.STRING, enumeration = {"TEXT", "NUMBER", "DATE", "TIME", "DATETIME", "BOOLEAN","CUSTOM"})
    private CellTypeEnum type;
    @Schema(type = SchemaType.STRING)
    private String format;

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
    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

}
