package com.grott.export.rest;

import com.grott.export.rest.dto.DtoAvailableTemplates;
import com.grott.export.rest.dto.DtoFillTemplate;
import com.grott.export.service.ExcelTemplateBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("excel/templates")
public class ExcelTemplateResource {

    @Resource(lookup= "POI_SERVICE")
    private String templatePath;

    private Collection<String> getTemplates(){
        File folder = new File(templatePath);
        File[] listOfFiles = folder.listFiles();
        Collection<String> retval = new ArrayList<>();
        for(int i=0;i<listOfFiles.length;i++){
            if(listOfFiles[i].getName().endsWith(".xlsx")){
                retval.add(listOfFiles[i].getName());
            }
        }
        return retval;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAvailableTemplates(){
        DtoAvailableTemplates retval = new DtoAvailableTemplates();
        retval.setPath(templatePath);
        retval.setTemplates(getTemplates());        
        return Response.ok(retval).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response fillTemplate(DtoFillTemplate input){
        Collection<String> availableTemplates = getTemplates();
        if(!availableTemplates.contains(input.getTemplate())){
            throw new BadRequestException("Template not existing");
        } 
        ExcelTemplateBuilder builder = new ExcelTemplateBuilder( //
            templatePath+"/"+input.getTemplate(),
            input.getSheets());
        
        return Response.ok(builder.createReport()).build();
    }
}
