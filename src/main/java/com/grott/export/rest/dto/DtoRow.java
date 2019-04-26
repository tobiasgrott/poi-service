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
@Schema(type = SchemaType.OBJECT, implementation = DtoRow.class)
public class DtoRow {

    @Schema(implementation = DtoCell.class, type = SchemaType.ARRAY)
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
