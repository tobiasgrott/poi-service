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

@Path("excel")
public class ExcelResource {
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createExcelReport(Collection<DtoSheet> input,  //
            @QueryParam("fileName") @DefaultValue("export.xlsx") String fileName){
        ExcelBuilder excel = new ExcelBuilder(input);
        return Response.ok(excel.createReport()) //
                .header("content-disposition","attachment; filename=" + fileName) //
                .build();
    }    
}
