package donghe.donghestatistics.service;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import donghe.donghestatistics.dao.*;
import donghe.donghestatistics.domain.*;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


@Service
@Transactional
public class TeaServiceImpl implements TeaService {
    @Autowired
    private TeaDAO teaDAO;
    @Autowired
    private TeaPriceDAO teaPriceDAO;

    @Autowired
    private TeaPriceMonthDAO teaPriceMonthDAO;
    @Autowired
    private TeaInterestedDAO teaInterestedDAO;
    @Autowired
    private TeaInterestedPriceMonthCutDAO teaInterestedPriceMonthCutDAO;
    @Autowired
    private ParamByMonthDAO paramByMonthDAO;
    @Autowired
    private PriceMonthAvgDAO priceMonthAvgDAO;
    @Autowired
    private TeaDetailDAO teaDetailDAO;

    public void getTeaDetail() throws Exception {
        List<Integer> goodsIdList = getGoodsIdList();
        for (Integer i : goodsIdList) {
            getTeaDetailByGoodsId(i);

        }
    }

    public void getTeaDetailByGoodsId(Integer goodsId) throws Exception {
        String url = "http://www.donghetea.com/goods.php";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;

        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList();

        params.add(new BasicNameValuePair("id", Integer.toString(goodsId)));
        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
        String name = null;
        String year = null;
        String batch = null;
        String productionTechnology = null;
        String specification = null;
        String netContent = null;
        String referencePricePerKg = null;
        String referencePrice = null;
        Integer quality = null;
        Integer costPerformance = null;
        Integer collectionValue = null;
        Integer score = null;
        try {
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                String content = "";
                String NEW_LINE = System.getProperty("line.separator");
                while ((body = br.readLine()) != null) {
                    content = content + body + NEW_LINE;
                }

//                System.out.println(content);

                Document document = Jsoup.parse(content);
                Elements elementsGoodsDetails1 = document.getElementsByClass("buyli clearfix");
                for (Element e1 : elementsGoodsDetails1) {
                    Elements details1 = e1.getElementsByClass("pro");
                    for (Element e2 : details1) {
                        if (e2.toString().contains("年份")) {
                            String[] s1 = e2.toString().split("<span>");
                            String[] s2 = s1[1].split("</span>");
                            year = s2[0];
                            System.out.println(year);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        } else if (e2.toString().contains("批次")) {
                            String[] s1 = e2.toString().split("<span>");
                            String[] s2 = s1[1].split("</span>");
                            batch = s2[0];
                            System.out.println(batch);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        } else if (e2.toString().contains("生产工艺")) {
                            String[] s1 = e2.toString().split("<span>");
                            String[] s2 = s1[1].split("</span>");
                            productionTechnology = s2[0];
                            System.out.println(productionTechnology);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        } else if (e2.toString().contains("规格")) {
                            String[] s1 = e2.toString().split("<span>");
                            String[] s2 = s1[1].split("</span>");
                            specification = s2[0];
                            System.out.println(specification);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        }


                    }

                    Elements details2 = e1.getElementsByClass("pro2");
                    for (Element e2 : details2) {
                        if (e2.toString().contains("净含量")) {
                            String s = e2.toString().replace(" ", "");
                            String[] s1 = s.split("净含量：");
                            String[] s2 = s1[1].split("</li>");
                            netContent = s2[0];
                            System.out.println(netContent);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        } else if (e2.toString().contains("公斤参考价")) {
                            String[] s1 = e2.toString().split("class=\"shop_s\">");
                            String[] s2 = s1[1].split("</font>");
                            referencePricePerKg = s2[0];
                            System.out.println(referencePricePerKg);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        } else if (e2.toString().contains("参考价")) {
                            String s = e2.toString().replace(" ", "");
                            String[] s1 = s.split("class=\"shop_sb\">");
                            String[] s2 = s1[1].split("</font>");

                            String referencePrice1 = s2[0];

                            String[] s3 = s2[1].split("<em>");
                            String[] s4 = s3[1].split("</em>");
                            String referencePrice2 = s4[0];
                            referencePrice = referencePrice1 + referencePrice2;
                            System.out.println(referencePrice);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        }
                    }

                }
                Elements elementsGoodsDetails2 = document.getElementsByClass("goods_pj");
                for (Element e1 : elementsGoodsDetails2) {
                    Elements details1 = e1.getElementsByClass("goods_pj_left");
                    for (Element e2 : details1) {
                        Elements goods_pj_list = e2.getElementsByClass("goods_pj_list");
                        for (Element e3 : goods_pj_list) {
                            if (e3.toString().contains("品")) {
                                String s = e3.toString().replace(" ", "");
                                String[] s1 = s.split("class=\"goods_pj_txt\">");
                                String[] s2 = s1[1].split("</div>");
                                String quality1 = s2[0].trim();
                                quality = new Integer(quality1.split("分")[0]);
                                System.out.println(quality);
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                            } else if (e3.toString().contains("性")) {
                                String s = e3.toString().replace(" ", "");
                                String[] s1 = s.split("class=\"goods_pj_txt\">");
                                String[] s2 = s1[1].split("</div>");
                                String costPerformance1 = s2[0].trim();
                                costPerformance = new Integer(costPerformance1.split("分")[0]);
                                System.out.println(costPerformance);
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            } else if (e3.toString().contains("收")) {
                                String s = e3.toString().replace(" ", "");
                                String[] s1 = s.split("class=\"goods_pj_txt\">");
                                String[] s2 = s1[1].split("</div>");
                                String collectionValue1 = s2[0].trim();
                                costPerformance = new Integer(collectionValue1.split("分")[0]);
                                System.out.println(costPerformance);
                                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                            }

                        }

                    }


                    Elements details2 = e1.getElementsByClass("goods_pj_right");
                    for (Element e2 : details2) {
                        Elements wp_txt = e2.getElementsByClass("wp_num");
                        for (Element e3 : wp_txt) {
                            String s = e3.toString().replace(" ", "");
                            String[] s1 = s.split("class=\"wp_num\">");
                            String[] s2 = s1[1].split("</div>");
                            score = new Integer(s2[0].trim());
                            System.out.println(score);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                        }
                    }

                }


                TeaDetail teaDetail=new TeaDetail();
                teaDetail.setName(teaDAO.getNameByGoodsId(goodsId));
                teaDetail.setGoodsId(goodsId);
                teaDetail.setYear(year);
                teaDetail.setBatch(batch);
                teaDetail.setProductionTechnology(productionTechnology);
                teaDetail.setSpecification(specification);
                teaDetail.setNetContent(netContent);
                teaDetail.setReferencePricePerKg(referencePricePerKg);
                teaDetail.setReferencePrice(referencePrice);
                teaDetail.setQuality(quality);
                teaDetail.setCostPerformance(costPerformance);
                teaDetail.setCollectionValue(collectionValue);
                teaDetail.setScore(score);
                teaDetailDAO.create(teaDetail);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输入流，释放资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    public void clearZeroPrice() {
        teaPriceDAO.clearZeroPrice();
    }

    public void getTeaPriceMonth() {
        List<Integer> goodsIdList = getGoodsIdList();
        for (Integer i : goodsIdList) {
            getTeaPriceMonthByGoodsId(i);

        }
    }

    public void getTeaPriceMonthByGoodsId(Integer goodsId) {
        List<TeaPriceMonth> teaPriceMonthList = teaPriceDAO.getTeaPriceMonthByGoodsId(goodsId);
        for (TeaPriceMonth teaPriceMonth : teaPriceMonthList) {
            teaPriceMonthDAO.create(teaPriceMonth);
        }
    }

    public List<Integer> getGoodsIdList() {
        return teaDAO.getGoodsIdList();
    }

    public void getGoodsPrice() throws Exception {
        List<Integer> goodsIdList = getGoodsIdList();
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Integer i : goodsIdList) {
            getGoodsPriceByGoodsId(i, "2005-01-01", sdf.format(now));

        }
    }

    public void getGoodsPriceByGoodsId(int goodsId, String beginDay, String endDay) throws Exception {

        String url = "http://www.donghetea.com/goods.php" + "?id=" + Integer.toString(goodsId);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;

        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> params = new ArrayList();

        params.add(new BasicNameValuePair("begin_day", beginDay));
        params.add(new BasicNameValuePair("end_day", endDay));
        params.add(new BasicNameValuePair("size", "10"));
        params.add(new BasicNameValuePair("sort_time", "0"));


        httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));

        String str = "";

        try {

            response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                String content = "";
                String NEW_LINE = System.getProperty("line.separator");
                while ((body = br.readLine()) != null) {
                    content = content + body + NEW_LINE;
                }

//                System.out.println(content);


                Document document = Jsoup.parse(content);
                Elements scripts = document.getElementsByTag("script");
                for (Element element : scripts) {
                    if (element.toString().contains("hqData")) {
                        String s = element.toString().replace(" ", "");
                        String[] s1 = s.split("hqData=");
                        String[] s2 = s1[1].split(";");
                        String priceList = s2[0];
                        System.out.println(priceList);
                        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                        JSONArray jsonArray = JSON.parseArray(priceList);
                        for (Object obj : jsonArray) {
                            TeaPrice teaPrice = new TeaPrice();
                            teaPrice.setGoodsId(goodsId);
                            teaPrice.setName(teaDAO.getNameByGoodsId(goodsId));
                            String date = (String) (JSON.parseArray(obj.toString()).get(0));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            java.util.Date dateUtil = sdf.parse(date);
                            java.sql.Date dateSql = new java.sql.Date(dateUtil.getTime());
                            teaPrice.setDate(dateSql);
                            final Object o = JSON.parseArray(obj.toString()).get(1);
                            if (o instanceof Integer) {
                                teaPrice.setPrice((double) ((Integer) o).intValue());
                            } else if (o instanceof BigDecimal) {
                                teaPrice.setPrice(((BigDecimal) o).doubleValue());
                            } else if (o instanceof Double) {
                                teaPrice.setPrice((Double) o);
                            }

                            teaPriceDAO.create(teaPrice);

                        }
                    }
                }

            }
            teaDAO.mark(goodsId);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输入流，释放资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void getGoodsId() throws IOException {

        int page = getGoodsIdByPage(1);
        for (int i = 1; i <= page; i++) {
            getGoodsIdByPage(i);
        }
    }

    public int getGoodsIdByPage(int page) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream is = null;
        String url = "http://www.donghetea.com/category.php";
        //封装请求参数
        List<NameValuePair> params = new ArrayList();

        params.add(new BasicNameValuePair("id", "36"));
        params.add(new BasicNameValuePair("price_min", "0"));
        params.add(new BasicNameValuePair("price_max", "0"));
        params.add(new BasicNameValuePair("sort", "sort_order"));
        params.add(new BasicNameValuePair("order", "desc"));
        params.add(new BasicNameValuePair("page", Integer.toString(page)));
        String str = "";
        int pageCount = 0;
        try {
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            System.out.println(str);
            //创建Get请求
            HttpGet httpGet = new HttpGet(url + "?" + str);
            //执行Get请求，
            response = httpClient.execute(httpGet);
            //得到响应体
            HttpEntity entity = response.getEntity();


            if (entity != null) {
                is = entity.getContent();
                //转换为字节输入流
                BufferedReader br = new BufferedReader(new InputStreamReader(is, Consts.UTF_8));
                String body = null;
                String content = "";
                String NEW_LINE = System.getProperty("line.separator");
                while ((body = br.readLine()) != null) {
                    content = content + body + NEW_LINE;
                }


                Document document = Jsoup.parse(content);
                Elements elementsGoodsItem = document.getElementsByClass("goodsItem");
                for (Element element : elementsGoodsItem) {

                    Elements elementsGoodsItem_title = element.getElementsByClass("goodsItem_title");
                    for (Element element1 : elementsGoodsItem_title) {
                        Elements links = element1.getElementsByTag("a");
                        for (Element link : links) {
                            String linkHref = link.attr("href");
                            String[] array = linkHref.split("id=");
                            int linkId = Integer.parseInt(array[1]);
                            String linkText = link.text();
                            Tea tea = new Tea();
                            tea.setName(linkText);
                            tea.setGoodsId(linkId);
                            teaDAO.createOrUpdate(tea.getName(), tea.getGoodsId());
                            System.out.println(linkHref + "  " + linkId + "  " + linkText);
                        }
                    }
                }
                Elements elementsGoodsItemLast = document.getElementsByClass("goodsItem last");
                for (Element element : elementsGoodsItemLast) {
                    Elements elementsGoodsItem_title = element.getElementsByClass("goodsItem_title");
                    for (Element element1 : elementsGoodsItem_title) {
                        Elements links = element1.getElementsByTag("a");
                        for (Element link : links) {
                            String linkHref = link.attr("href");
                            String[] array = linkHref.split("id=");
                            int linkId = Integer.parseInt(array[1]);
                            String linkText = link.text();

                            System.out.println(linkHref + "  " + linkId + "  " + linkText);
                        }

                    }


                }
                Elements es = document.getElementsByClass("pager_count");
                for (Element element : es) {
                    pageCount = Integer.parseInt(element.text().substring(1, element.text().length() - 1));
                    System.out.println(pageCount);
                }

            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭输入流，释放资源
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pageCount;
    }

    public TeaInterested postTeaInterested(Integer goodsId, Double reputation, Double year, Double brand,
                                           Double area, Double scarcity, Double seasoning, Double flavor) {
        TeaInterested teaInterested = new TeaInterested();
        teaInterested.setName(teaDAO.getNameByGoodsId(goodsId));
        teaInterested.setGoodsId(goodsId);
        teaInterested.setReputation(reputation);
        teaInterested.setYear(year);
        teaInterested.setBrand(brand);
        teaInterested.setArea(area);
        teaInterested.setScarcity(scarcity);
        teaInterested.setSeasoning(seasoning);
        teaInterested.setFlavor(flavor);
        teaInterestedDAO.create(teaInterested);
        return teaInterested;
    }

    public void getTeaInterestedPriceMonthUnCut() {
        List<Integer> goodsIdsInterested = teaInterestedDAO.getGoodsIdInterested();
        for (Integer goodsId : goodsIdsInterested) {
            List<TeaPriceMonth> teaPriceMonthList = teaPriceMonthDAO.getTeaPriceMonthByGoodsId(goodsId);
            for (TeaPriceMonth teaPriceMonth : teaPriceMonthList) {
                TeaInterestedPriceMonthCut teaInterestedPriceMonthCut = new TeaInterestedPriceMonthCut();
                teaInterestedPriceMonthCut.setName(teaPriceMonth.getName());
                teaInterestedPriceMonthCut.setGoodsId(teaPriceMonth.getGoodsId());
                teaInterestedPriceMonthCut.setAvgPrice(teaPriceMonth.getAvgPrice());
                teaInterestedPriceMonthCut.setYearMonth(teaPriceMonth.getYearMonth());
                teaInterestedPriceMonthCutDAO.create(teaInterestedPriceMonthCut);
            }
        }

    }

    public String getPivotYearMonth(String yearMonth) {
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(5, 7);
        if (StringUtils.equals(month, "01")) {
            return String.valueOf(Integer.parseInt(year) - 1) + "-12";
        } else if (StringUtils.equals(month, "02")) {
            return year + "-01";
        } else if (StringUtils.equals(month, "03")) {
            return year + "-02";
        } else if (StringUtils.equals(month, "04")) {
            return year + "-03";
        } else if (StringUtils.equals(month, "05")) {
            return year + "-04";
        } else if (StringUtils.equals(month, "06")) {
            return year + "-05";
        } else if (StringUtils.equals(month, "07")) {
            return year + "-06";
        } else if (StringUtils.equals(month, "08")) {
            return year + "-07";
        } else if (StringUtils.equals(month, "09")) {
            return year + "-08";
        } else if (StringUtils.equals(month, "10")) {
            return year + "-09";
        } else if (StringUtils.equals(month, "11")) {
            return year + "-10";
        } else {
            return year + "-11";
        }
    }

    public void getTeaInterestedPriceMonthCut() {
        List<Integer> goodsIdsInterested = teaInterestedDAO.getGoodsIdInterested();
        for (Integer goodsId : goodsIdsInterested) {
            List<TeaPriceMonth> teaPriceMonthList = teaPriceMonthDAO.getTeaPriceMonthByGoodsId(goodsId);
            for (TeaPriceMonth teaPriceMonth : teaPriceMonthList) {
                String yearMonth = teaPriceMonth.getYearMonth();
                String pivotYearMonth = getPivotYearMonth(yearMonth);
                if (teaPriceMonthDAO.existOrNotByGoodsIdAndYearMonth(goodsId, pivotYearMonth)) {
                    Double d1 = teaPriceMonthDAO.getAvgPriceByGoodsIdAndYearMonth(goodsId, yearMonth);
                    Double d2 = teaPriceMonthDAO.getAvgPriceByGoodsIdAndYearMonth(goodsId, pivotYearMonth);
                    Double prop = d1 / d2;

                    System.out.println(prop);
                    teaInterestedPriceMonthCutDAO.updateProp(goodsId, yearMonth, prop);
                } else {
                    teaInterestedPriceMonthCutDAO.deleteByGoodsIdAndYearMonth(goodsId, yearMonth);
                }
            }
        }
    }

    public void getEstimatedAvgPrice(Double regFactor) {
        List<TeaInterestedPriceMonthCut> teaInterestedPriceMonthCutList = teaInterestedPriceMonthCutDAO.getTeaInterestedPriceMonthCutList();
        for (TeaInterestedPriceMonthCut teaInterestedPriceMonthCut : teaInterestedPriceMonthCutList) {
            String yearMonth = teaInterestedPriceMonthCut.getYearMonth();
            ParamByMonth paramByMonth = paramByMonthDAO.getByYearMonth(yearMonth);
            TeaInterested teaInterested = teaInterestedDAO.getByGoodsId(teaInterestedPriceMonthCut.getGoodsId());
            Double estimatedAvgPrice = 1 + paramByMonth.getIntercept() + paramByMonth.getOuterParam() * teaInterested.getByOrder(1) + paramByMonth.getReputationParam() * teaInterested.getByOrder(2) +
                    paramByMonth.getYearParam() * teaInterested.getByOrder(3) + paramByMonth.getScarcityParam() * teaInterested.getByOrder(4) + paramByMonth.getBrand_areaParam() * teaInterested.getByOrder(5) +
                    paramByMonth.getSeasoning_flavorParam() * teaInterested.getByOrder(6) +

                    +regFactor * (Math.pow(paramByMonth.getIntercept(), 2) + Math.pow(paramByMonth.getOuterParam(), 2) + Math.pow(paramByMonth.getReputationParam(), 2) +
                            Math.pow(paramByMonth.getYearParam(), 2) + Math.pow(paramByMonth.getScarcityParam(), 2) + Math.pow(paramByMonth.getBrand_areaParam(), 2) +
                            Math.pow(paramByMonth.getSeasoning_flavorParam(), 2));
            teaInterestedPriceMonthCutDAO.updateEstimatedAvgPrice(teaInterestedPriceMonthCut.getGoodsId(), yearMonth, estimatedAvgPrice);

        }
    }

    public void getAverage() {
        List<PriceMonthAvg> priceMonthAvgList = teaInterestedPriceMonthCutDAO.getAverage();
        for (PriceMonthAvg priceMonthAvg : priceMonthAvgList) {
            priceMonthAvgDAO.create(priceMonthAvg);
        }
    }

    public Double getError() {
        return teaInterestedPriceMonthCutDAO.getError();
    }

    public void merge() {
        teaInterestedDAO.merge();
    }

    public List<TeaPrice> fillIn(List<TeaPrice> teaPriceList) throws Exception {
        List<TeaPrice> res = new ArrayList<>();
        TeaPrice first = teaPriceList.get(0);
        Date firstDate = first.getDate();
        Integer goodsId = first.getGoodsId();
        String name = first.getName();

        TeaPrice last = teaPriceList.get(teaPriceList.size() - 1);
        Date lastDate = last.getDate();
        res.add(first);
        System.out.println(nextDate(first.getDate()));
        while (!(nextDate(first.getDate())).after(lastDate)) {
            if (!teaPriceDAO.existOrNotByGoodsIdAndDate(goodsId, nextDate(first.getDate()))) {
                TeaPrice tmp = new TeaPrice();
                tmp.setGoodsId(goodsId);
                tmp.setName(name);
                tmp.setPrice(first.getPrice());
                tmp.setDate(nextDate(first.getDate()));
                res.add(tmp);
                first = tmp;
            } else {
                res.add(teaPriceDAO.findByGoodsIdAndDate(goodsId, nextDate(first.getDate())));
                first = teaPriceDAO.findByGoodsIdAndDate(goodsId, nextDate(first.getDate()));

            }

        }

        return res;
    }

    public List<TeaPrice> getTeaPriceListByGoodsId(Integer goodsId) {
        return teaPriceDAO.getTeaPriceListByGoodsId(goodsId);
    }

    public Date nextDate(Date before) throws Exception {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        System.out.println(before);
        c.setTime(before);
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天
        Date after = new Date(c.getTime().getTime());
        String afterString = f.format(after);
        return new Date((f.parse(afterString)).getTime());
    }
}
