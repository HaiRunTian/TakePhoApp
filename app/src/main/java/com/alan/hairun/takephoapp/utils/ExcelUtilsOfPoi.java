package com.alan.hairun.takephoapp.utils;

import android.content.Context;
import android.util.SparseArray;
import android.widget.Toast;


import com.alan.hairun.gen.DaoSession;
import com.alan.hairun.gen.ProjectBeanDao;
import com.alan.hairun.takephoapp.MainActivity;
import com.alan.hairun.takephoapp.bean.CheckDataBean;
import com.alan.hairun.takephoapp.bean.ProjectBean;
import com.alan.hairun.takephoapp.config.MyApplication;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 使用poi 库生成excel表 读取excel表
 * HSSFWorkbook() 2003 ~20007
 * XSSFWorkbook(); 2007以上
 *
 * @author HaiRun
 * @time 2019/6/19.14:42
 */
public class ExcelUtilsOfPoi {

    /**
     * 创建excel表
     *
     * @return
     */
    private static Workbook createWorkbook() {
        //xls
        return new HSSFWorkbook();
    }



    /**
     * 初始化 当天检测记录表
     *
     * @Params :
     * @author :HaiRun
     * @date :2019/6/26  18:00
     */
    public static void initExcelPsSheet(String fileName, List<List<String>> list) {
//        int[] width = new int[]{5, 11, 13, 20, 30, 8, 8, 8, 8, 8};
//        fileName = SuperMapConfig.DEFAULT_DATA_PATH + SuperMapConfig.DEFAULT_DATA_EXCEL_PATH + "/检测记录表.xls";
        FileOutputStream outputStream = null;
        try {
            FileInputStream is = new FileInputStream(new File(fileName));
            Workbook workbook = new HSSFWorkbook(is);
            SparseArray<CellStyle> borderedStyle = createBorderedStyle(workbook);
            Sheet sheet = workbook.getSheetAt(0);
            //填入数据
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    List<String> list1 = list.get(i);
                    Row row = sheet.createRow(6 + i);
                    row.setHeight((short) (256 * 1.2));
                    for (int i1 = 0; i1 < list1.size(); i1++) {
                        Cell cell = row.createCell(i1);
                        cell.setCellStyle(borderedStyle.get(3));
                        cell.setCellValue(list1.get(i1));
                    }
                }
            }
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private static void setCloumLine(SparseArray<CellStyle> borderedStyle, Row row) {
        for (int i = 0; i < 10; i++) {
            row.createCell(i).setCellStyle(borderedStyle.get(1));
        }
    }

    /**
     * 方法描述：初始化Excel表头
     *
     * @param colNameP  点表字段集合
     * @Params : fileName excel文件名
     * @author :HaiRun
     * @date :2019/6/19  16:27
     */
    public static void initExcel(Context context,String fileName, List<CheckDataBean> colNameP) {
        FileOutputStream outputStream = null;
        String[] array = new String[]{"类型","单项工程编号","规格指标","计量单位","上报量","第一次","第二次","第三次"};
        Workbook workbook = null;
        try {
            workbook = createWorkbook();
            SparseArray<CellStyle> borderedStyle = createBorderedStyle(workbook);

            //建立新的point sheet对象 excel表单
            Sheet sheetPoint = workbook.createSheet("sheet0");
            //在sheet里创建第一行，参数为行索引  0~65535直接
            Row row1 = sheetPoint.createRow(0);
            //创建单元格 0~255
            for (int i = 0; i < array.length; i++) {
                Cell cell = row1.createCell(i);
                cell.setCellStyle(borderedStyle.get(2));
                cell.setCellValue(array[i]);
            }

            for (int i = 0; i < colNameP.size(); i++) {
                Row row = sheetPoint.createRow(i+1);
                CheckDataBean dataBean = colNameP.get(i);
                Cell cell = row.createCell(0);
                cell.setCellStyle(borderedStyle.get(2));
                cell.setCellValue(dataBean.type);
                Cell cell1 = row.createCell(1);
                cell1.setCellStyle(borderedStyle.get(2));
                cell1.setCellValue(dataBean.projectName);
                Cell cell2 = row.createCell(2);
                cell2.setCellStyle(borderedStyle.get(2));
                cell2.setCellValue(dataBean.standard);
                Cell cell3 = row.createCell(3);
                cell3.setCellStyle(borderedStyle.get(2));
                cell3.setCellValue(dataBean.unit);
                Cell cell4 = row.createCell(4);
                cell4.setCellStyle(borderedStyle.get(2));
                cell4.setCellValue(dataBean.planNum);
                Cell cell5 = row.createCell(5);
                cell5.setCellStyle(borderedStyle.get(2));
                cell5.setCellValue(dataBean.one);
                Cell cell6 = row.createCell(6);
                cell6.setCellStyle(borderedStyle.get(2));
                cell6.setCellValue(dataBean.two);
                Cell cell7 = row.createCell(7);
                cell7.setCellStyle(borderedStyle.get(2));
                cell7.setCellValue(dataBean.three);
            }


            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            Toast.makeText(context,"导出成功",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            LogUtills.e("ExcelUtilsOfPoi ---------" + e.toString());
            Toast.makeText(context,"导出失败",Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 方法描述：初始化Excel表头
     * 外检模式
     *
     * @param colNameP  点表字段集合
     * @param colNameL  线表字段集合
     * @param pointName 点表名字
     * @param lineName  线表名字
     * @Params : fileName excel文件名
     * @author :HaiRun
     * @date :2019/6/19  16:27
     */
    public static void initExcelOfOut(String fileName, List<String> colNameP, List<String> colNameL, List<String> colNamePoint
            , List<String> colNameLine, String pointName, String lineName, String point, String line) {
        FileOutputStream outputStream = null;
        Workbook workbook = null;
        try {
            workbook = createWorkbook();
            SparseArray<CellStyle> borderedStyle = createBorderedStyle(workbook);

            //建立新的point sheet对象 excel表单
            Sheet sheetPoint = workbook.createSheet(pointName);
            //在sheet里创建第一行，参数为行索引  0~65535直接
            Row row1 = sheetPoint.createRow(0);
            //创建单元格 0~255
            for (int i = 0; i < colNameP.size(); i++) {
                Cell cell = row1.createCell(i);
                cell.setCellStyle(borderedStyle.get(2));
                cell.setCellValue(colNameP.get(i));
            }
            //建立新的line sheet对象 excel表单
            Sheet sheetLine = workbook.createSheet(lineName);
            //在sheet里创建第一行，参数为行索引  0~65535直接
            Row row = sheetLine.createRow(0);
            //创建单元格 0~255
            for (int i = 0; i < colNameL.size(); i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(borderedStyle.get(2));
                cell.setCellValue(colNameL.get(i));
            }
            //创建第三张表
            Sheet point2 = workbook.createSheet(point);
            //在sheet里创建第一行，参数为行索引  0~65535直接
            Row row2 = point2.createRow(0);
            //创建单元格 0~255
            for (int i = 0; i < colNamePoint.size(); i++) {
                Cell cell = row2.createCell(i);
                cell.setCellStyle(borderedStyle.get(2));
                cell.setCellValue(colNamePoint.get(i));
            }

            //创建第四张表
            Sheet line2 = workbook.createSheet(line);
            //在sheet里创建第一行，参数为行索引  0~65535直接
            Row row3 = line2.createRow(0);
            //创建单元格 0~255
            for (int i = 0; i < colNameLine.size(); i++) {
                Cell cell = row3.createCell(i);
                cell.setCellStyle(borderedStyle.get(2));
                cell.setCellValue(colNameLine.get(i));
            }

            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (Exception e) {
            LogUtills.e("ExcelUtilsOfPoi ---------" + e.toString());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @param :sheetIndex 表下标
     * @param :           fileName 文件名称
     * @params : list  数据集合
     * @author :HaiRun
     * @date :2019/6/19  20:15
     */
    public static <T> void writeObjListToExcel(int sheetIndex, List<T> list, String fileName) {
        FileInputStream is = null;
        FileOutputStream os = null;
        Workbook wb = null;
        String extString = fileName.substring(fileName.lastIndexOf("."));
        try {
            is = new FileInputStream(new File(fileName));
            if (".xls".equals(extString)) {
                wb = new HSSFWorkbook(is);
            } else if (".xlsx".equals(extString)) {

            } else {
                wb = null;
            }
            if (wb != null) {
                SparseArray<CellStyle> borderedStyle = createBorderedStyle(wb);
                Sheet sheet = wb.getSheetAt(sheetIndex);
                ArrayList<String> list1 = null;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    list1 = (ArrayList<String>) list.get(i);
                    int size2 = list1.size();
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < size2; j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(borderedStyle.get(3));
                        cell.setCellValue(list1.get(j));
                    }
                }
            }
            os = new FileOutputStream(fileName);
            wb.write(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    /**
     * 设置表格的内容到四边的距离,表格四边的颜色
     * <p>
     * 对齐方式：
     * 水平： setAlignment();
     * 竖直：setVerticalAlignment()
     * 四边颜色：
     * 底边：  cellStyle.setBottomBorderColor()
     * <p>
     * 四边边距：
     * <p>
     * 填充：
     * <p>
     * 缩进一个字符：
     * setIndention()
     * <p>
     * 内容类型：
     * setDataFormat()
     *
     * @param workbook
     * @return
     */
    private static SparseArray<CellStyle> createBorderedStyle(Workbook workbook) {
        SparseArray<CellStyle> array = new SparseArray<>();
        //第一 二 三行标题
        CellStyle cellStyle0 = workbook.createCellStyle();
        Font font0 = creatFont(workbook);
        font0.setFontHeightInPoints((short) 14);
        font0.setFontName("黑体");
        font0.setBoldweight((short) 5);
        font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle0.setFont(font0);
        cellStyle0.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle0.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        array.put(0, cellStyle0);

        //第 五 六 七 八 十行
        CellStyle cellStyle1 = workbook.createCellStyle();
        Font font1 = creatFont(workbook);
        font1.setFontName("宋体");
        font1.setFontHeightInPoints((short) 10);
        font1.setBoldweight((short) 5);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle1.setFont(font1);
        cellStyle1.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle1.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        setCellStyle(cellStyle1);
        array.put(1, cellStyle1);

        //第九行
        CellStyle cellStyle2 = workbook.createCellStyle();
        Font font2 = creatFont(workbook);
        font2.setFontName("宋体");
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font2.setFontHeightInPoints((short) 10);
        font2.setBoldweight((short) 1);
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle2.setFont(font2);
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        setCellStyle(cellStyle2);
        array.put(2, cellStyle2);

        //第十一行
        CellStyle cellStyle3 = workbook.createCellStyle();
        //对齐
        cellStyle3.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle3.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font font3 = workbook.createFont();
        font3.setFontName("宋体");
        font3.setColor(Font.COLOR_NORMAL);
        cellStyle3.setFont(font3);
        setCellStyle(cellStyle3);
        array.put(3, cellStyle3);

        //第 五 行
        CellStyle cellStyle4 = workbook.createCellStyle();
        Font font4 = creatFont(workbook);
        font4.setFontHeightInPoints((short) 10);
        font4.setBoldweight((short) 5);
        font4.setFontName("宋体");
        font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellStyle4.setFont(font1);
        cellStyle4.setAlignment(CellStyle.ALIGN_LEFT);
        cellStyle4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        array.put(4, cellStyle4);

        //第 五 行
        CellStyle cellStyle5 = workbook.createCellStyle();
        cellStyle5.setBottomBorderColor((short) 13);
        cellStyle5.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        array.put(5, cellStyle5);

        return array;
    }

    /**
     * 设置单元格格式
     *
     * @Params :
     * @author :HaiRun
     * @date :2019/7/2  17:34
     */
    private static void setCellStyle(CellStyle cellStyle3) {
        //重新设置单元格的四边颜色
        BorderStyle thin = BorderStyle.THIN;
        short blackColor_Index = IndexedColors.BLACK.getIndex();
        cellStyle3.setBottomBorderColor(blackColor_Index);
        cellStyle3.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle3.setTopBorderColor(blackColor_Index);
        cellStyle3.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle3.setRightBorderColor(blackColor_Index);
        cellStyle3.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle3.setRightBorderColor(blackColor_Index);
        cellStyle3.setBorderLeft(CellStyle.BORDER_THIN);
    }


    /**
     * 创建Font
     * <p>
     * 注意点：excle工作簿中字体最大限制为32767，应该重用字体，而不是为每个单元格都创建字体。
     * <p>
     * 其API:
     * setBold():设置粗体
     * setFontHeightInPoints():设置字体的点数
     * setColor():设置字体颜色
     * setItalic():设置斜体
     *
     * @param workbook
     * @return
     */
    private static Font creatFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_NORMAL);
        return font;
    }

    /**
     * 设置Sheet表
     *
     * @param sheet
     */
    private static void setSheet(Sheet sheet) {
        // turn off gridlines（关闭网络线）
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

    }

    /**
     * 获取点线表数据量
     *
     * @params :file excel文件名
     * @author :HaiRun
     * @date :2019/6/19  20:24
     */
    public static int getExcelRows(File file, int sheet) {
        int rows = 0;
        FileInputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(file);
            workbook = new HSSFWorkbook(is);
            Sheet sheetAt = workbook.getSheetAt(sheet);
            //获取最大行
            rows = sheetAt.getPhysicalNumberOfRows() - 1;
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rows;
    }

    /**
     * @param : sheet excel表下标
     * @params :file  文件名
     * @author :HaiRun
     * @date :2019/6/19  20:27
     */
    public static List<Map<String, Object>> readExcelDataToBean(File file, int sheet) {
        ArrayList<Map<String, Object>> pInfos = new ArrayList<>();
        FileInputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(file);
            workbook = new HSSFWorkbook(is);
            //点表
            Sheet sheetPoint = workbook.getSheetAt(sheet);
//            BaseFieldPInfos infos = null;
            //获取表的行数
            int rowsP = sheetPoint.getPhysicalNumberOfRows();
            //获取第一行
            Row row = sheetPoint.getRow(0);
            //获取第一行列数
            int columnCount = row.getPhysicalNumberOfCells();
            LogUtills.i("行数", rowsP + "------------" + columnCount);
            //遍历行数 第二行开始
            for (int i = 1; i < rowsP; i++) {
                Map<String, Object> map = new HashMap<>();
                Row row1 = sheetPoint.getRow(i);
                LogUtills.i("长度", columnCount + "======" + row1.getPhysicalNumberOfCells());
                //遍历列数 第一列开始
                    try {
                        //导入数据库
                        DaoSession daoSession = MyApplication.getINSTANT().getDaoSession();
                        //插入数据数据库 工程表 单一
                        ProjectBean bean = new ProjectBean();
                        bean.setType(row1.getCell(0).toString());
                        bean.setName(row1.getCell(1).toString());
                        bean.setBitName(MainActivity.bitProject);
                        bean.setDate(DateTimeUtil.getCurrentDateFromFormat(DateTimeUtil.DATE_FORMAT_YYYYMMDD_HHMMSS));
                        List<ProjectBean> list = daoSession.getProjectBeanDao().queryBuilder()
                                .where(ProjectBeanDao.Properties.BitName.eq(bean.getBitName()))
                                .where(ProjectBeanDao.Properties.Name.eq(bean.getName()))
                                .build()
                                .list();
                        if (list.size() == 0){
                            daoSession.getProjectBeanDao().insert(bean);
                        }

                        //插入数据库 信息表
                        CheckDataBean dataBean = new CheckDataBean();
                        dataBean.setBitName(MainActivity.bitProject);
                        dataBean.setType(row1.getCell(0).toString()+"");
                        dataBean.setProjectName(row1.getCell(1).toString()+"");
                        dataBean.setStandard(row1.getCell(2).toString()+"");
                        dataBean.setUnit(row1.getCell(3).toString()+"");
                        dataBean.setPlanNum(row1.getCell(4).toString()+"");
                        daoSession.getCheckDataBeanDao().insert(dataBean);

                    } catch (Exception e) {
                        LogUtills.e("字段值",e.toString());
                    }

                pInfos.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtills.e("FileNotFoundException" + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtills.e("IOException" + e.toString());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return pInfos;
    }

    /**
     * 合并cell单元格
     * <p>
     * CellRangeAddress构造器中参数：
     * 参数1：first row(0-based)
     * 参数2：last row(0-based)
     * 参数3：first column(0-based)
     * 参数4：last column(0-based)
     *
     * @param sheet
     * @param cellRangeAddress
     */
    private static void mergingCells(Sheet sheet, CellRangeAddress cellRangeAddress) {
        sheet.addMergedRegion(cellRangeAddress);
    }


}
