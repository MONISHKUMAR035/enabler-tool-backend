package com.Employeestatus.Tool.Service;

import com.Employeestatus.Tool.Model.User;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;

@Service
public class ExcelService {

    private static final String FILE_PATH = "Excel/RegisteredUsers.xlsx";

    public void writeUserToExcel(User user) {
        try {
            Path path = Paths.get("Excel");
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

            File file = new File(FILE_PATH);
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                workbook = new XSSFWorkbook(new FileInputStream(file));
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Users");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Full Name");
                header.createCell(1).setCellValue("Email");
                header.createCell(2).setCellValue("Username");
                header.createCell(3).setCellValue("Password");
            }

            int lastRow = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(lastRow);
            row.createCell(0).setCellValue(user.getFullname());
            row.createCell(1).setCellValue(user.getEmail());
            row.createCell(2).setCellValue(user.getUsername());
            row.createCell(3).setCellValue(user.getPassword());

            FileOutputStream out = new FileOutputStream(FILE_PATH);
            workbook.write(out);
            out.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
