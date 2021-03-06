package donghe.donghestatistics.controller;

import donghe.donghestatistics.service.ExcelService;
import donghe.donghestatistics.service.TeaService;
import donghe.donghestatistics.service.TrainingService;
import donghe.donghestatistics.service.TrainingServiceImpl;
import donghe.donghestatistics.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/donghe")

public class TeaController {
    @Autowired
    private TeaService teaService;
    @Autowired
    private TrainingService trainingService;
    @Autowired
    private ExcelService excelService;

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
    public Result getGoodsPrice() throws Exception {
        teaService.getGoodsPrice();
        return Result.success();
    }

    @RequestMapping(value = "/teaPriceMonthByGoodsId", method = RequestMethod.GET)
    public Result getTeaPriceMonthByGoodsId() throws Exception {
        teaService.getTeaPriceMonthByGoodsId(1703);
        return Result.success();
    }

    @RequestMapping(value = "/teaPriceMonth", method = RequestMethod.GET)
    public Result getTeaPriceMonth() throws Exception {
        teaService.clearZeroPrice();
        teaService.getTeaPriceMonth();
        return Result.success();
    }

    @RequestMapping(value = "/teaInterested", method = RequestMethod.POST)
    public Result postTeaInterested(@RequestParam Integer goodsId, @RequestParam Double reputation, @RequestParam Double year, @RequestParam Double brand,
                                    @RequestParam Double area, @RequestParam Double scarcity, @RequestParam Double seasoning, @RequestParam Double flavor) {
        return Result.success(teaService.postTeaInterested(goodsId, reputation, year, brand, area, scarcity, seasoning, flavor));
    }

    @RequestMapping(value = "/getTeaInterestedPriceMonthUnCut", method = RequestMethod.GET)
    public Result getTeaInterestedPriceMonthUnCut() {
        teaService.getTeaInterestedPriceMonthUnCut();
        return Result.success();
    }

    @RequestMapping(value = "/getTeaInterestedPriceMonthCut", method = RequestMethod.GET)
    public Result getTeaInterestedPriceMonthCut() {
        teaService.getTeaInterestedPriceMonthCut();
        return Result.success();
    }

    @RequestMapping(value = "/getPivotYearMonth", method = RequestMethod.GET)
    public Result getPivotYearMonth() {
        return Result.success(teaService.getPivotYearMonth("2018-01"));

    }

//    @RequestMapping(value = "/train", method = RequestMethod.GET)
//    public Result train() {
//        trainingService.train();
//        return Result.success();
//    }

    @RequestMapping(value = "/estimatedAvgPrice", method = RequestMethod.GET)
    public Result getEstimatedAvgPrice() {
        teaService.getEstimatedAvgPrice(0.1);
        return Result.success();
    }

    @RequestMapping(value = "/average", method = RequestMethod.GET)
    public Result getAverage() {
        teaService.getAverage();
        return Result.success();
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public Result getError() {
        return Result.success(teaService.getError());
    }

    @RequestMapping(value = "/findSupParam", method = RequestMethod.GET)
    public Result findSupParam(@RequestParam Double regFactor) {
        teaService.getTeaInterestedPriceMonthUnCut();
        teaService.getTeaInterestedPriceMonthCut();
        trainingService.train(regFactor);
        teaService.getEstimatedAvgPrice(regFactor);
        Double err = teaService.getError();

        return Result.success(err);
    }

    @RequestMapping(value = "/merge", method = RequestMethod.GET)
    public Result merge() {
        teaService.merge();

        return Result.success();
    }
    @RequestMapping(value = "/fillIn", method = RequestMethod.GET)
    public Result fillIn(@RequestParam Integer goodsId)throws Exception {
        excelService.createExcel(teaService.fillIn(teaService.getTeaPriceListByGoodsId(goodsId)));
        return Result.success();
    }
    @RequestMapping(value = "/teaDetail1", method = RequestMethod.GET)
    public Result getTeaDetail1(@RequestParam Integer goodsId) throws Exception{
        teaService.getTeaDetailByGoodsId(goodsId);
        return Result.success();
    }
    @RequestMapping(value = "/teaDetail", method = RequestMethod.GET)
    public Result getTeaDetail() throws Exception{
        teaService.getTeaDetail();
        return Result.success();
    }
    @RequestMapping(value = "/teaInfo", method = RequestMethod.GET)
    public Result getTeaInfo(@RequestParam String name,@RequestParam String beginAt,@RequestParam String endAt) throws Exception{
        return Result.success(teaService.getTeaInfo(name,beginAt,endAt));
    }
}
