package util;

import imports.DeptRow;
import imports.ImportCollection;
import imports.StudentRow;
import imports.TeacherRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;

public class ExcelImport {
    private final String fileName;
    private final InputStream inputStream;
    private Workbook workbook;

    public ExcelImport(String fileName, InputStream inputStream){
        this.fileName = fileName;
        this.inputStream = inputStream;
    }

    public boolean isValid(){
        return workbook != null;
    }

    public ImportCollection getImported(){
        ImportCollection collection = new ImportCollection();
        Sheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rows; ++i){
            Row row = sheet.getRow(i);
            collection.getDepts().add(new DeptRow(i,
                getCellValue(row.getCell(0)),
                getCellValue(row.getCell(1)),
                getCellValue(row.getCell(2))
            ));
        }
        sheet = workbook.getSheetAt(1);
        rows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rows; ++i){
            Row row = sheet.getRow(i);
            collection.getStudents().add(new StudentRow(i,
                    getCellValue(row.getCell(0)),
                    getCellValue(row.getCell(1)),
                    getCellValue(row.getCell(2)),
                    getCellValue(row.getCell(3))
            ));
        }
        sheet = workbook.getSheetAt(2);
        rows = sheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rows; ++i){
            Row row = sheet.getRow(i);
            collection.getTeachers().add(new TeacherRow(i,
                    getCellValue(row.getCell(0)),
                    getCellValue(row.getCell(1)),
                    getCellValue(row.getCell(2)),
                    getCellValue(row.getCell(3)),
                    getCellValue(row.getCell(4)),
                    getCellValue(row.getCell(5))
            ));
        }
        return collection;
    }

    private String getCellValue(Cell cell){
        if(cell == null){
            return null;
        }
        switch (cell.getCellType()){
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case STRING:
                return cell.getRichStringCellValue().getString();
            case FORMULA:
                if(DateUtil.isCellDateFormatted(cell)){
                    return cell.getDateCellValue().toString();
                } else{
                    return String.valueOf(cell.getNumericCellValue());
                }
            default:
                return null;
        }
        //return cell.getRichStringCellValue().getString();
    }

    public void read(){
        //Workbook workbook = null;
        String ext = fileName.substring(fileName.lastIndexOf("."));
        try{
            if(".xls".equals(ext)){
                this.workbook = new HSSFWorkbook(inputStream);
            } else if(".xlsx".equals(ext)){
                this.workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
