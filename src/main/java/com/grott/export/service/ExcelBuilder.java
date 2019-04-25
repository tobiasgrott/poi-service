/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.service;

import com.grott.export.rest.dto.DtoCell;
import com.grott.export.rest.dto.DtoSheet;
import com.grott.export.rest.dto.DtoRow;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author tobia
 */
public class ExcelBuilder {
    private final Collection<DtoSheet> sheets;
    
    public ExcelBuilder(Collection<DtoSheet> sheets){
        this.sheets = sheets;
    }
    
    public byte[] createReport(){
        byte[] byteStream = null;
        try(XSSFWorkbook wb = new XSSFWorkbook()){
            for(DtoSheet dtoSheet : sheets){
                XSSFSheet spreadSheet = wb.createSheet(dtoSheet.getSheetName());
                writeHeaders(spreadSheet, dtoSheet.getHeaders());
                for(int i=0; i<dtoSheet.getRows().size();i++){
                    writeRow(spreadSheet, dtoSheet.getRows().get(i), i+1);
                }
                autoSizeColumns(spreadSheet,dtoSheet.getHeaders().size());
            }
            try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
                wb.write(outputStream);
                byteStream = outputStream.toByteArray();
            }
        }catch(final IOException ex){
        }
        return byteStream;
    }
    private void writeHeaders(XSSFSheet sheet, List<String> headers){
        final XSSFRow row = sheet.createRow(0);
        for(int i=0;i<headers.size();i++){
            final XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(getHeaderStyle(sheet));
        }
    }
    private XSSFCellStyle getHeaderStyle(final XSSFSheet sheet){
        final XSSFCellStyle titleStyle = sheet.getWorkbook().createCellStyle();
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(FillPatternType.FINE_DOTS);
        final XSSFFont myFont = sheet.getWorkbook().createFont();
        myFont.setBold(true);
        titleStyle.setFont(myFont);
        return titleStyle;
    }

    private void autoSizeColumns(XSSFSheet sheet, int columns){
        for(int i=0;i<columns;i++){
            sheet.autoSizeColumn(i);
        }
    }
    
    private void writeRow(XSSFSheet sheet ,DtoRow dtoRow,int rowNumber){
        final XSSFRow row = sheet.createRow(rowNumber);
        for(int i=0;i<dtoRow.getCells().size();i++){
            DtoCell inputCell = dtoRow.getCells().get(i);
            switch(inputCell.getType()){
                case BOOLEAN:
                    setCellValue(row, i, Boolean.valueOf(inputCell.getValue()));
                    break;
                case DATE:
                    setCellValue(row, i, LocalDate.parse(inputCell.getValue()));
                    break;
                case DATETIME:
                    setCellValue(row, i, LocalDateTime.parse(inputCell.getValue()));
                    break;
                case NUMBER:
                    setCellValue(row, i, new BigDecimal(inputCell.getValue()));
                    break;
                case TEXT:
                    setCellValue(row, i, inputCell.getValue());
                    break;
                case TIME:
                    setCellValue(row, i, LocalTime.parse(inputCell.getValue()));
                    break;
            }
        }
    }
    private void setCellValue(XSSFRow row, int column, String value){
        XSSFCell cell = row.createCell(column);
        cell.setCellType(CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(getCellStyle(row));
    }
    private void setCellValue(XSSFRow row, int column, BigDecimal value){
        XSSFCell cell = row.createCell(column);
        if(value!=null){
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(value.doubleValue());
        }else{
            cell.setCellType(CellType.BLANK);
        }
        cell.setCellStyle(getCellStyle(row));
    }
    private void setCellValue(XSSFRow row, int column, Boolean value){
        XSSFCell cell = row.createCell(column);
        if(value!=null){
            cell.setCellType(CellType.BOOLEAN);
            cell.setCellValue(value);
        }else{
            cell.setCellType(CellType.BLANK);
        }
        cell.setCellStyle(getCellStyle(row));
    }
    private XSSFCellStyle getDateStyle(XSSFRow row, String format){
        XSSFCellStyle cellStyle = getCellStyle(row);
        CreationHelper creationHelper = row.getSheet().getWorkbook().getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(format));
        return cellStyle;
    }
    private void setCellValue(XSSFRow row, int column, LocalTime value){
        Date date = value!=null?java.sql.Time.valueOf(value) : null;
        XSSFCell cell = row.createCell(column);
        cell.setCellValue(date);
        cell.setCellStyle(getDateStyle(row, "h:mm"));
    }
    private void setCellValue(XSSFRow row, int column, LocalDateTime value){
        Date date = value!=null?java.sql.Timestamp.valueOf(value) : null;
        XSSFCell cell = row.createCell(column);
        cell.setCellValue(date);
        cell.setCellStyle(getDateStyle(row, "m/d/yy h:mm"));
    }
    private void setCellValue(XSSFRow row, int column, LocalDate value){
        Date date = value!=null?java.sql.Date.valueOf(value) : null;
        XSSFCell cell = row.createCell(column);
        cell.setCellValue(date);
        cell.setCellStyle(getDateStyle(row, "m/d/yy"));
    }
    private XSSFCellStyle getCellStyle(XSSFRow row){
        XSSFCellStyle cellStyle = row.getSheet().getWorkbook().createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }
}
