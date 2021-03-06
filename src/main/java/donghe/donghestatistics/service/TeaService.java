package donghe.donghestatistics.service;

import donghe.donghestatistics.domain.TeaInterested;
import donghe.donghestatistics.domain.TeaPrice;
import donghe.donghestatistics.domain.TeaPriceMonth;
import donghe.donghestatistics.vo.TeaDetailVO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface TeaService {
    void getTeaDetail() throws Exception;
    void getTeaDetailByGoodsId(Integer goodsId) throws Exception;
    TeaDetailVO getTeaInfo(String name, String beginAt, String endAt) throws Exception;
    void getGoodsId() throws IOException;

    void getGoodsPriceByGoodsId(int goodsId, String beginDay, String endDay) throws Exception;

    List<Integer> getGoodsIdList();

    void getGoodsPrice() throws Exception;

    void getTeaPriceMonthByGoodsId(Integer goodsId);

    void clearZeroPrice();

    void getTeaPriceMonth();

    TeaInterested postTeaInterested(Integer goodsId, Double reputation, Double year, Double brand,
                                    Double area, Double scarcity, Double seasoning, Double flavor);

    void getTeaInterestedPriceMonthUnCut();
    void getTeaInterestedPriceMonthCut();
    String getPivotYearMonth(String yearMonth);
    void getEstimatedAvgPrice(Double regFactor);
    void getAverage();
    Double getError();
    void merge();
    List<TeaPrice> fillIn(List<TeaPrice> teaPriceList) throws Exception;
    List<TeaPrice> getTeaPriceListByGoodsId(Integer goodsId);
}
