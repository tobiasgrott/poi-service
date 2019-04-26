package com.grott.export.rest;

import com.grott.export.rest.dto.DtoSheet;
import com.grott.export.service.ExcelBuilder;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

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
