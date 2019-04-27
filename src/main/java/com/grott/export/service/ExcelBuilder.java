/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grott.export.service;

import com.grott.export.rest.dto.CellTypeEnum;
import com.grott.export.rest.dto.DtoCell;
import com.grott.export.rest.dto.DtoSheet;
import com.grott.export.rest.dto.DtoRow;
import com.grott.export.service.enums.DefaultDateFormat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private XSSFCellStyle headerStyle;
    private XSSFCellStyle dataStyle;
    private Map<CellTypeEnum, XSSFCellStyle> cellStyles;

    public ExcelBuilder(Collection<DtoSheet> sheets) {
        this.sheets = sheets;
    }

    public byte[] createReport() {
        byte[] byteStream = null;
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            headerStyle = getHeaderStyle(wb);
            dataStyle = getCellStyle(wb);
            cellStyles = new HashMap<>();

            XSSFCellStyle cellStyle = getCellStyle(wb);
            CreationHelper creationHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DefaultDateFormat.DATE.getFormat()));
            cellStyles.put(CellTypeEnum.DATE, cellStyle);

            cellStyle = getCellStyle(wb);
            creationHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DefaultDateFormat.DATETIME.getFormat()));
            cellStyles.put(CellTypeEnum.DATETIME, cellStyle);

            cellStyle = getCellStyle(wb);
            creationHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DefaultDateFormat.TIME.getFormat()));
            cellStyles.put(CellTypeEnum.TIME, cellStyle);

            sheets.forEach((DtoSheet dtoSheet) -> {
                XSSFSheet spreadSheet = wb.createSheet(dtoSheet.getSheetName());
                writeHeaders(spreadSheet, dtoSheet.getHeaders());
                for (int i = 0; i < dtoSheet.getRows().size(); i++) {
                    writeRow(spreadSheet, dtoSheet.getRows().get(i), i + 1);
                }
                autoSizeColumns(spreadSheet, dtoSheet.getHeaders().size());
            });
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                wb.write(outputStream);
                byteStream = outputStream.toByteArray();
            }
        } catch (final IOException ex) {
        }
        return byteStream;
    }

    private XSSFCellStyle getCustomCellStyle(XSSFWorkbook wb, String format) {
        XSSFCellStyle cellStyle = getCellStyle(wb);
        CreationHelper creationHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(format));
        return cellStyle;
    }

    private void writeHeaders(XSSFSheet sheet, List<String> headers) {
        final XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            final XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }
    }

    private XSSFCellStyle getHeaderStyle(final XSSFWorkbook wb) {
        final XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        titleStyle.setFillPattern(FillPatternType.FINE_DOTS);
        final XSSFFont myFont = wb.createFont();
        myFont.setBold(true);
        titleStyle.setFont(myFont);
        return titleStyle;
    }

    private void autoSizeColumns(XSSFSheet sheet, int columns) {
        for (int i = 0; i < columns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void writeRow(XSSFSheet sheet, DtoRow dtoRow, int rowNumber) {
        final XSSFRow row = sheet.createRow(rowNumber);
        for (int i = 0; i < dtoRow.getCells().size(); i++) {
            DtoCell inputCell = dtoRow.getCells().get(i);
            XSSFCell cell = row.createCell(i);
            if (inputCell.getValue() == null) {
                cell.setCellType(CellType.BLANK);
            } else {
                switch (inputCell.getType()) {
                    case BOOLEAN:
                        cell.setCellType(CellType.BOOLEAN);
                        cell.setCellValue(Boolean.valueOf(inputCell.getValue()));
                        cell.setCellStyle(dataStyle);
                        break;
                    case DATE:
                        cell.setCellValue(java.sql.Date.valueOf(LocalDate.parse(inputCell.getValue())));
                        cell.setCellStyle(cellStyles.get(CellTypeEnum.DATE));
                        break;
                    case DATETIME:
                        cell.setCellValue(java.sql.Timestamp.valueOf(LocalDateTime.parse(inputCell.getValue())));
                        cell.setCellStyle(cellStyles.get(CellTypeEnum.DATETIME));
                        break;
                    case NUMBER:
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue(Double.valueOf(inputCell.getValue()));
                        cell.setCellStyle(dataStyle);                        
                        break;
                    case TEXT:
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(inputCell.getValue());
                        cell.setCellStyle(dataStyle);                        
                        break;
                    case TIME:
                        cell.setCellValue(java.sql.Time.valueOf(LocalTime.parse(inputCell.getValue())));
                        cell.setCellStyle(cellStyles.get(CellTypeEnum.TIME));
                        break;
                    case CUSTOM:
                        cell.setCellValue(Double.valueOf(inputCell.getValue()));
                        cell.setCellStyle(getCustomCellStyle(sheet.getWorkbook(), inputCell.getFormat()));
                }
            }
        }
    }    
    
    private XSSFCellStyle getCellStyle(XSSFWorkbook wb) {
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        return cellStyle;
    }
}
