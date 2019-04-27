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
@Schema(type=SchemaType.OBJECT, implementation=DtoFillCell.class)
public class DtoFillCell {
    @Schema(type=SchemaType.STRING)
    private String value;
    @Schema(type=SchemaType.STRING, enumeration = {"TEXT", "NUMBER", "DATE", "TIME", "DATETIME", "BOOLEAN"})
    private CellTypeEnum type;
    @Schema(type=SchemaType.INTEGER)
    private int column;
    @Schema(type=SchemaType.INTEGER)
    private int row;    
    @Schema(type=SchemaType.STRING)
    private String format;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

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

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }
    
    
}
