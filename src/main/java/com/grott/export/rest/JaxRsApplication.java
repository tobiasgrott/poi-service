/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.rest;

import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.grott.export.rest.dto.DtoSheet;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 *
 * @author tobia
 */
@ApplicationPath("rest")
@OpenAPIDefinition(
        tags = {
            @Tag(name = "excel", description = "Operations about creating Excel file"),
            @Tag(name = "templates", description = "Operations about excel templates")
        },
        info = @Info(
                title = "Excel Export API",
                version = "1.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html")),
        servers = {
            @Server(url = "https://localhost:8080/export-service", description = "The test API server")},
        components = @Components(
                schemas = {
                    @Schema(name = "DtoSheets", title = "Sheet", type = SchemaType.ARRAY, implementation = DtoSheet.class)
                },
                requestBodies = {
                    @RequestBody(name = "sheets", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DtoSheet.class)), required = true, description = "example review to add")}
                ))
@Schema(
        name = "Excel Export API",
        description = "APIs for creating Excel files")
public class JaxRsApplication extends Application {

}
