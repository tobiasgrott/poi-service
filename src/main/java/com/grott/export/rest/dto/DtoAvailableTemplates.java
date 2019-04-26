/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest.dto;

import java.util.Collection;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author tobia
 */
@Schema(type=SchemaType.OBJECT, implementation=DtoAvailableTemplates.class)
public class DtoAvailableTemplates {
    @Schema(type=SchemaType.STRING)
    private String path;
    @Schema(type=SchemaType.ARRAY, implementation=String.class)
    private Collection<String> templates;

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the templates
     */
    public Collection<String> getTemplates() {
        return templates;
    }

    /**
     * @param templates the templates to set
     */
    public void setTemplates(Collection<String> templates) {
        this.templates = templates;
    }
    
    
    
}
