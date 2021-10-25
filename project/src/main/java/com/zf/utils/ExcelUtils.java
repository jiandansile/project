package com.zf.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具类
 * @author lijianbin
 * @date 2021-02-20 11:03
 **/
@Slf4j
public class ExcelUtils {
    private ExcelUtils(){}

    /**
     * 导入
     */
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null){
            return new ArrayList<>();
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = new ArrayList<>();
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (Exception e){
            log.error("excel格式化异常");
        }
        return list;
    }

    /**
     * Excel表格导出
     * @param response  HttpServletResponse对象
     * @param pojoClass 实体类
     * @param dataList  Excel表格的数据
     * @param fileName  导出Excel的文件名
     * @param title     标题
     * @param sheetName 工作表名称
     */
    public static void exportExcel(HttpServletResponse response, Class<?> pojoClass, List<?> dataList, String fileName,
                                   String title, String sheetName) {
        try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title, sheetName), pojoClass, dataList)) {
            //准备将Excel的输出流通过response输出到页面下载,八进制输出流
            response.setContentType("application/octet-stream");
            if (StringUtils.isNotBlank(fileName)) {
                fileName = fileName + ".xls";
            } else {
                fileName = System.currentTimeMillis() + ".xls";
            }
            //设置导出Excel的名称
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            //刷新缓冲
            response.flushBuffer();
            //workbook将Excel写入到response的输出流中，供页面下载该Excel文件
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("Excel导出失败");
        }
    }
}