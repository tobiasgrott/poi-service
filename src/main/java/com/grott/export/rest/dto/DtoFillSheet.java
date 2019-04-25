/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest.dto;

import java.util.Collection;

/**
 *
 * @author tobia
 */
public class DtoFillSheet {
    private String sheetName;
    private Collection<DtoFillCell> cells;

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
     * @return the cells
     */
    public Collection<DtoFillCell> getCells() {
        return cells;
    }

    /**
     * @param cells the cells to set
     */
    public void setCells(Collection<DtoFillCell> cells) {
        this.cells = cells;
    }
    
    
}
