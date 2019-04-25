/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest.dto;

import java.util.List;

/**
 *
 * @author tobia
 */
public class DtoSheet {
    private String sheetName;
    private List<String> headers;
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
