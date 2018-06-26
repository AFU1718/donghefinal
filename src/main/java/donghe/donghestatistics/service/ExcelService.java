package donghe.donghestatistics.service;

import donghe.donghestatistics.domain.TeaPrice;

import java.util.List;

public interface ExcelService {
    void createExcel(List<TeaPrice> teaPriceList);
}
