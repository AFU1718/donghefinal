package donghe.donghestatistics.service;
import donghe.donghestatistics.domain.TeaPrice;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ExcelServiceImpl implements ExcelService{

        public void createExcel(List<TeaPrice> teaPriceList){
            //第一步，创建一个workbook对应一个excel文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            //第二部，在workbook中创建一个sheet对应excel中的sheet
            HSSFSheet sheet = workbook.createSheet("macd");
            //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
            HSSFRow row = sheet.createRow(0);
            //第四步，创建单元格，设置表头
            HSSFCell cell = row.createCell(0);
            cell.setCellValue("date");
            cell = row.createCell(1);
            cell.setCellValue("name");
            cell = row.createCell(2);
            cell.setCellValue("goodsId");
            cell = row.createCell(3);
            cell.setCellValue("price");
            Integer goodsId=teaPriceList.get(0).getGoodsId();


            for (int i = 0; i < teaPriceList.size(); i++) {
                HSSFRow row1 = sheet.createRow(i + 1);
                TeaPrice teaPrice = teaPriceList.get(i);
                //创建单元格设值
                row1.createCell(0).setCellValue(teaPrice.getDate());
                System.out.println("乘火车从"+teaPrice.getDate());
                row1.createCell(1).setCellValue(teaPrice.getName());
                row1.createCell(2).setCellValue(teaPrice.getGoodsId());
                row1.createCell(3).setCellValue(teaPrice.getPrice());
            }

            //将文件保存到指定的位置
            try {
                FileOutputStream fos = new FileOutputStream("D:\\donghe\\"+goodsId+".xls");
                workbook.write(fos);
                System.out.println("写入成功");
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

