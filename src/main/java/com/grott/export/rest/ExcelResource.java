package com.grott.export.rest;

import com.grott.export.rest.dto.DtoSheet;
import com.grott.export.service.ExcelBuilder;
import java.util.Collection;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("excel")
public class ExcelResource {

    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponses(value = {
        @APIResponse(
                responseCode = "200",
                description = "Generated excel report",
                content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM))
    })
    public Response createExcelReport(
            @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = DtoSheet.class)),
                    description = "payload of excel document") Collection<DtoSheet> input, //

            @QueryParam("fileName") //
            @DefaultValue("export.xlsx") String fileName) {
        ExcelBuilder excel = new ExcelBuilder(input);
        return Response.ok(excel.createReport()) //
                .header("content-disposition", "attachment; filename=" + fileName) //
                .build();
    }
}
