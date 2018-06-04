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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
                            Date dateUtil = sdf.parse(date);
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

        if (StringUtils.equals(month, "01") || StringUtils.equals(month, "02") || StringUtils.equals(month, "03")
                || StringUtils.equals(month, "04") || StringUtils.equals(month, "05") || StringUtils.equals(month, "06")) {
            return year + "-01";
        } else {
            return year + "-07";
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
            Double estimatedAvgPrice = 1+paramByMonth.getIntercept() + paramByMonth.getPolicyParam() * teaInterested.getByOrder(1) + paramByMonth.getHotMoneyParam() * teaInterested.getByOrder(2) +
                    paramByMonth.getHotMoneyParam() * teaInterested.getByOrder(3) + paramByMonth.getReputationParam() * teaInterested.getByOrder(4) + paramByMonth.getYearParam() * teaInterested.getByOrder(5) +
                    paramByMonth.getBrandParam() * teaInterested.getByOrder(6) + paramByMonth.getAreaParam() * teaInterested.getByOrder(7) + paramByMonth.getScarcityParam() * teaInterested.getByOrder(8) +
                    paramByMonth.getSeasoningParam() * teaInterested.getByOrder(9) + paramByMonth.getFlavorParam() * teaInterested.getByOrder(10) +
                    +regFactor * (Math.pow(paramByMonth.getIntercept(), 2) + Math.pow(paramByMonth.getPolicyParam(), 2) + Math.pow(paramByMonth.getHotMoneyParam(), 2) +
                            Math.pow(paramByMonth.getHypeParam(), 2) + Math.pow(paramByMonth.getReputationParam(), 2) + Math.pow(paramByMonth.getYearParam(), 2) +
                            Math.pow(paramByMonth.getBrandParam(), 2) + Math.pow(paramByMonth.getAreaParam(), 2) + Math.pow(paramByMonth.getScarcityParam(), 2) +
                            Math.pow(paramByMonth.getScarcityParam(), 2) + Math.pow(paramByMonth.getFlavorParam(), 2));
            teaInterestedPriceMonthCutDAO.updateEstimatedAvgPrice(teaInterestedPriceMonthCut.getGoodsId(), yearMonth, estimatedAvgPrice);

        }
    }

}
