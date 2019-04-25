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
public class DtoFillTemplate {
    private String template;
    private Collection<DtoFillSheet> sheets;

    /**
     * @return the template
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @return the sheets
     */
    public Collection<DtoFillSheet> getSheets() {
        return sheets;
    }

    /**
     * @param sheets the sheets to set
     */
    public void setSheets(Collection<DtoFillSheet> sheets) {
        this.sheets = sheets;
    }
    
    
}
