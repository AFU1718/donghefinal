package donghe.donghestatistics.controller;

import donghe.donghestatistics.service.TeaService;
import donghe.donghestatistics.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/donghe")

public class TeaController {
    @Autowired
    private TeaService teaService;

    @RequestMapping(method = RequestMethod.GET)
    public Result getGoodsId() throws Exception {
        teaService.getGoodsId();
        return Result.success();
    }

    @RequestMapping(value = "/goodsIdList", method = RequestMethod.GET)
    public Result getGoodsIdList() {

        return Result.success(teaService.getGoodsIdList());
    }

    @RequestMapping(value = "/goodsPrice", method = RequestMethod.GET)
    public Result getGoodsPrice()throws Exception {
        teaService.getGoodsPrice();
        return Result.success();
    }
    @RequestMapping(value = "/teaPriceMonthByGoodsId", method = RequestMethod.GET)
    public Result getTeaPriceMonthByGoodsId()throws Exception {
        teaService.getTeaPriceMonthByGoodsId(1703);
        return Result.success();
    }

    @RequestMapping(value = "/teaPriceMonth", method = RequestMethod.GET)
    public Result getTeaPriceMonth()throws Exception {
        teaService.getTeaPriceMonth();
        return Result.success();
    }

}
