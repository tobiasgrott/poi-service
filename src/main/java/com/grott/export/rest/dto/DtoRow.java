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
public class DtoRow {
    private List<DtoCell> cells;

    /**
     * @return the cells
     */
    public List<DtoCell> getCells() {
        return cells;
    }

    /**
     * @param cells the cells to set
     */
    public void setCells(List<DtoCell> cells) {
        this.cells = cells;
    }
    
    
}
