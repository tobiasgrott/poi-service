/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.grott.export.rest.dto.CellTypeEnum;
import com.grott.export.rest.dto.DtoFillCell;
import com.grott.export.rest.dto.DtoFillSheet;
import com.grott.export.service.enums.DefaultDateFormat;

/**
 *
 * @author tobia
 */
public class ExcelTemplateBuilder {

    private final Collection<DtoFillSheet> sheets;
    private final String template;
    private Map<CellTypeEnum, XSSFCellStyle> cellStyles;

    public ExcelTemplateBuilder(String template, Collection<DtoFillSheet> sheets) {
        this.sheets = sheets;
        this.template = template;
    }

    public byte[] createReport() {
        byte[] byteStream = null;
        try (XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(template))) {

            XSSFCellStyle cellStyle = wb.createCellStyle();
            CreationHelper creationHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DefaultDateFormat.DATE.getFormat()));
            cellStyles.put(CellTypeEnum.DATE, cellStyle);

            cellStyle = wb.createCellStyle();
            creationHelper = wb.getCreationHelper();
            cellStyle
                    .setDataFormat(creationHelper.createDataFormat().getFormat(DefaultDateFormat.DATETIME.getFormat()));
            cellStyles.put(CellTypeEnum.DATETIME, cellStyle);

            cellStyle = wb.createCellStyle();
            creationHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DefaultDateFormat.TIME.getFormat()));
            cellStyles.put(CellTypeEnum.TIME, cellStyle);
            List<String> sheetNames = new ArrayList<>();
            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                sheetNames.add(wb.getSheetName(i));
            }
            for (DtoFillSheet dtoSheet : sheets) {
                XSSFSheet spreadSheet;
                if (sheetNames.contains(dtoSheet.getSheetName())) {
                    spreadSheet = wb.getSheet(dtoSheet.getSheetName());
                } else {
                    spreadSheet = wb.createSheet(dtoSheet.getSheetName());
                }
                for (DtoFillCell dtoCell : dtoSheet.getCells()) {
                    XSSFRow row = spreadSheet.getRow(dtoCell.getRow());
                    if (row == null) {
                        row = spreadSheet.createRow(dtoCell.getRow());
                    }
                    XSSFCell cell = row.createCell(dtoCell.getColumn());
                    if (dtoCell.getValue() == null) {
                        cell.setCellType(CellType.BLANK);
                    } else {
                        switch (dtoCell.getType()) {
                            case BOOLEAN:
                                cell.setCellType(CellType.BOOLEAN);
                                cell.setCellValue(Boolean.valueOf(dtoCell.getValue()));
                                break;
                            case DATE:
                                cell.setCellValue(java.sql.Date.valueOf(LocalDate.parse(dtoCell.getValue())));
                                cell.setCellStyle(cellStyles.get(CellTypeEnum.DATE));
                                break;
                            case DATETIME:
                                cell.setCellValue(java.sql.Timestamp.valueOf(LocalDateTime.parse(dtoCell.getValue())));
                                cell.setCellStyle(cellStyles.get(CellTypeEnum.DATETIME));
                                break;
                            case NUMBER:
                                cell.setCellType(CellType.NUMERIC);
                                cell.setCellValue(Double.valueOf(dtoCell.getValue()));
                                break;
                            case TEXT:
                                cell.setCellType(CellType.STRING);
                                cell.setCellValue(dtoCell.getValue());
                                break;
                            case TIME:
                                cell.setCellValue(java.sql.Time.valueOf(LocalTime.parse(dtoCell.getValue())));
                                cell.setCellStyle(cellStyles.get(CellTypeEnum.TIME));
                                break;
                            case CUSTOM:
                                cell.setCellValue(dtoCell.getValue());
                                cell.setCellStyle(cellStyles.get(CellTypeEnum.TEXT));
                                break;
                        }
                    }
                }
            }

            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                wb.write(outputStream);
                byteStream = outputStream.toByteArray();
            }
        } catch (IOException ex) {
        }
        return byteStream;
    }
}
