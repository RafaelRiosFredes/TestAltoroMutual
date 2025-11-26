package org.example.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    private static XSSFWorkbook excelWBook;
    private static XSSFSheet excelWSheet;
    private static final DataFormatter formatter = new DataFormatter();


    public static void setExcelFileSheet(String filePath, String sheetName) throws IOException {
        FileInputStream excelFile = new FileInputStream(filePath);
        excelWBook = new XSSFWorkbook(excelFile);
        excelWSheet = excelWBook.getSheet(sheetName);

        if (excelWSheet == null) {
            throw new RuntimeException("No se encontró la hoja: " + sheetName);
        }
    }

    public static String getCellData(int rowNum, int colNum) {
        if (excelWSheet == null) {
            throw new IllegalStateException("Debe llamarse primero a setExcelFileSheet().");
        }

        XSSFRow row = excelWSheet.getRow(rowNum);
        if (row == null) {
            return "";
        }

        XSSFCell cell = row.getCell(colNum);
        if (cell == null) {
            return "";
        }

        return formatter.formatCellValue(cell);
    }



    public static void setCellData(String value, int rowNum, int colNum, String filePath) throws IOException {
        if (excelWSheet == null) {
            throw new IllegalStateException("Debe llamarse primero a setExcelFileSheet().");
        }

        XSSFRow row = excelWSheet.getRow(rowNum);
        if (row == null) {
            row = excelWSheet.createRow(rowNum);
        }

        XSSFCell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }

        cell.setCellValue(value);

        try (FileOutputStream outFile = new FileOutputStream(filePath)) {
            excelWBook.write(outFile);
        }
    }
}
