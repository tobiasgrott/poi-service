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
@Schema(type=SchemaType.STRING, enumeration={"TEXT", "NUMBER", "DATE", "TIME", "DATETIME", "BOOLEAN"})
public enum CellTypeEnum {
    TEXT, NUMBER, DATE, TIME, DATETIME, BOOLEAN;
}
