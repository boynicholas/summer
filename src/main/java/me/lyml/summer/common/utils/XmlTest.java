/*
 * Copyright 2016 Cnlyml
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.lyml.summer.common.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: XmlTest
 * @author: cnlyml
 * @date: 2016/8/30 14:00
 */
public class XmlTest {
    private static void handlerXml() throws IOException, InvalidFormatException {
        XSSFWorkbook xwb = new XSSFWorkbook(new File("F:\\2014.xlsx"));

        XSSFSheet sheet = xwb.getSheet("2014");

        XSSFRow row;

        Map<String, String> headMaps = handlerHead();
        List<Map<String, String>>data = new ArrayList<>();
        data.add(headMaps);
        for(int i = 2; i < sheet.getLastRowNum(); i++) {
            row = sheet.getRow(i);
            Map<String, String> bodyMap = new HashMap<>();
            for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++ ) {
                boolean isMerge = isMergedRow(sheet, i, j);
                if(row.getCell(j) == null) {
                    continue;
                }

                Set<String> keys = headMaps.keySet();
                for(String key : keys){
                    String value = headMaps.get(key);
                    if(value.equals(sheet.getRow(0).getCell(j).toString())) {
                        if(row.getCell(j).getCellType() == Cell.CELL_TYPE_BLANK) {
                            // 跳过
                        }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_STRING) {
                            bodyMap.put(key, row.getCell(j).toString());
                            /*System.out.print(row.getCell(j).toString()+ "    ");*/
                        }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_FORMULA) {
                            bodyMap.put(key, row.getCell(j).toString());
                            /*System.out.print(row.getCell(j).toString()+ "    ");*/
                        }else if(row.getCell(j).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                            DecimalFormat df = new DecimalFormat("#");
                            if(row.getCell(j).getCellStyle().getDataFormat() == 58) {
                                bodyMap.put(key, new SimpleDateFormat("yyyy-MM-dd").format(row.getCell(j).getDateCellValue()));
                                /*System.out.print(new SimpleDateFormat("yyyy-MM-dd").format(row.getCell(j).getDateCellValue()) + "    ");*/
                            }else{
                                bodyMap.put(key, df.format(row.getCell(j).getNumericCellValue()));
                                /*System.out.print(df.format(row.getCell(j).getNumericCellValue()) + "    ");*/
                            }
                        }

                        if(bodyMap.size() >= headMaps.size()) {
                            data.add(bodyMap);
                            bodyMap = new HashMap<>();

                        }
                        break;
                    }
                }

            }
            System.out.println("已处理：" + i + "行数据");

        }

        // 处理完成以后，对数据进行校验
        System.out.println("处理完成，对数据进行校验");
        int errorNum = 0;
        for(Map<String, String> d : data) {
            if(d.size() != headMaps.size()) {
                errorNum++;
            }
        }
        System.out.println("数据校验完成，总计数据：" + (data.size() - 1) + "条，错误数据：" + errorNum + "条");
        StringBuffer sb = new StringBuffer();
        sb.append("insert into bak_policy_record_2014 (id, policyRecordDate, departmentName, saleUserName, mobile, licenseName, plateNumber, insureCompanyName) values");

        for(int i = 1; i < data.size(); i++) {
            Map<String, String> d = data.get(i);
            sb.append("(NULL,'").append(d.get("policyRecordDate")).append("','").append(d.get("departmentName")).append("','").append(d.get("saleUserName")).append("','").append(d.get("mobile")).append("','").append(d.get("licenseName")).append("','").append(d.get("plateNumber")).append("','").append(d.get("insureCompanyName")).append("'),\r\n");
        }


        File file = new File("F:\\ddd.txt");
        if(!file.exists()) {
            file.createNewFile();
        }
        System.out.println(sb.toString());

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(sb.toString());
        fileWriter.close();

    }

    public static void main(String[] args) throws IOException, InvalidFormatException {
        XmlTest.handlerXml();
    }

    private static boolean isMergedRow(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row == firstRow && row == lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }

        return false;
    }

    private static Map<String, String> handlerHead() {
        Map<String, String> headMaps = new LinkedHashMap<>();
        headMaps.put("policyRecordDate", "开单日期");
        headMaps.put("departmentName", "销售部门");
        headMaps.put("saleUserName", "车险顾问");
        headMaps.put("mobile", "电话");
        headMaps.put("licenseName", "被保险人");
        headMaps.put("plateNumber", "车牌");
        headMaps.put("insureCompanyName", "保险公司");

        return headMaps;
    }

}
