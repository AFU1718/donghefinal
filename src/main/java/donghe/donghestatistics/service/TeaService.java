package donghe.donghestatistics.service;

import donghe.donghestatistics.domain.TeaInterested;
import donghe.donghestatistics.domain.TeaPriceMonth;

import java.io.IOException;
import java.util.List;

public interface TeaService {
    void getGoodsId() throws IOException;

    void getGoodsPriceByGoodsId(int goodsId, String beginDay, String endDay) throws Exception;

    List<Integer> getGoodsIdList();

    void getGoodsPrice() throws Exception;

    void getTeaPriceMonthByGoodsId(Integer goodsId);

    void getTeaPriceMonth();

    TeaInterested postTeaInterested(Integer goodsId, Double reputation, Double year, Double brand,
                                    Double area, Double scarcity, Double seasoning, Double flavor);

    void getTeaInterestedPriceMonthUnCut();
}
