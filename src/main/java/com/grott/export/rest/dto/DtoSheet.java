/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest.dto;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author tobia
 */
@Schema(type = SchemaType.OBJECT, implementation = DtoSheet.class)
public class DtoSheet {

    @Schema(implementation = String.class)
    private String sheetName;
    @Schema(implementation = String.class, type = SchemaType.ARRAY)
    private List<String> headers;
    @Schema(implementation = DtoRow.class, type = SchemaType.ARRAY)
    private List<DtoRow> rows;

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @return the headers
     */
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * @param headers the headers to set
     */
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    /**
     * @return the rows
     */
    public List<DtoRow> getRows() {
        return rows;
    }

    /**
     * @param rows the rows to set
     */
    public void setRows(List<DtoRow> rows) {
        this.rows = rows;
    }

}
