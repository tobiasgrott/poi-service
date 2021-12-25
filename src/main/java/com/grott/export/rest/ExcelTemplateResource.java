package com.grott.export.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import com.grott.export.rest.dto.DtoAvailableTemplates;
import com.grott.export.rest.dto.DtoFillTemplate;
import com.grott.export.service.ExcelTemplateBuilder;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("excel/templates")
public class ExcelTemplateResource {

    @Resource(lookup = "POI_SERVICE")
    private String templatePath;

    private Collection<String> getTemplates() {
        File folder = new File(templatePath);
        File[] listOfFiles = folder.listFiles();
        Collection<String> retval = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.getName().endsWith(".xlsx")) {
                retval.add(file.getName());
            }
        }
        return retval;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTemplates() {
        DtoAvailableTemplates retval = new DtoAvailableTemplates();
        retval.setPath(templatePath);
        retval.setTemplates(getTemplates());
        return Response.ok(retval).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response fillTemplate(DtoFillTemplate input) {
        Collection<String> availableTemplates = getTemplates();
        if (!availableTemplates.contains(input.getTemplate())) {
            throw new BadRequestException("Template not existing");
        }
        ExcelTemplateBuilder builder = new ExcelTemplateBuilder( //
                templatePath + "/" + input.getTemplate(),
                input.getSheets());

        return Response.ok(builder.createReport()).build();
    }
}
