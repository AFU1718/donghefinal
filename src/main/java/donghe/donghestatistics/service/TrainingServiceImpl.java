package donghe.donghestatistics.service;

import donghe.donghestatistics.dao.ParamByMonthDAO;
import donghe.donghestatistics.dao.TeaInterestedDAO;
import donghe.donghestatistics.dao.TeaInterestedPriceMonthCutDAO;
import donghe.donghestatistics.dao.TeaPriceMonthDAO;
import donghe.donghestatistics.domain.ParamByMonth;
import donghe.donghestatistics.domain.TeaInterested;

import donghe.donghestatistics.domain.TeaInterestedPriceMonthCut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ujmp.core.Matrix;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService{
    @Autowired
    private TeaInterestedPriceMonthCutDAO teaInterestedPriceMonthCutDAO;
    @Autowired
    private TeaInterestedDAO teaInterestedDAO;
    @Autowired
    private ParamByMonthDAO paramByMonthDAO;
    @Autowired
    private TeaPriceMonthDAO teaPriceMonthDAO;

    private Matrix getX(List<TeaInterested> teaList) {
        int m = teaList.size();
        Matrix matrix = Matrix.Factory.zeros(m, 7);
        for (int i = 0; i < matrix.getSize()[0]; ++i) {
            for (int j = 0; j < matrix.getSize()[1]; ++j) {
                matrix.setAsDouble(teaList.get(i).getByOrder(j + 1), i, j);
            }
        }
        return matrix;
    }

    private Matrix getY(List<TeaInterested> teaList, String yearMonth) {
        int m = teaList.size();
        Matrix matrix = Matrix.Factory.zeros(m, 1);
        for (int i = 0; i < matrix.getSize()[0]; ++i) {
            for (int j = 0; j < matrix.getSize()[1]; ++j) {
                matrix.setAsDouble(teaInterestedPriceMonthCutDAO.getAvgPriceByGoodsIdAndYearMonth(teaList.get(i).getGoodsId(), yearMonth)-1.0, i, j);
            }
        }
        return matrix;
    }

    public Matrix getParamByMonthMatrix(List<TeaInterested> teaList, Double regFactor, String yearMonth) {
        Matrix identity = Matrix.Factory.zeros(7, 7);
        identity.setAsDouble(1.0, 0, 0);
        identity.setAsDouble(1.0, 1, 1);
        identity.setAsDouble(1.0, 2, 2);
        identity.setAsDouble(1.0, 3, 3);
        identity.setAsDouble(1.0, 4, 4);
        identity.setAsDouble(1.0, 5, 5);
        identity.setAsDouble(1.0, 6, 6);
        return (((getX(teaList).transpose().mtimes(getX(teaList))).plus(identity.times(regFactor)).inv()).mtimes(getX(teaList).transpose())).mtimes(getY(teaList, yearMonth));
    }

    public ParamByMonth matrixToParamMonth(String yearMonth, Matrix matrix) {
            ParamByMonth paramByMonth = new ParamByMonth();
        paramByMonth.setYearMonth(yearMonth);
        paramByMonth.setOuterParam(matrix.getAsDouble(0, 0));
        paramByMonth.setReputationParam(matrix.getAsDouble(1, 0));
        paramByMonth.setYearParam(matrix.getAsDouble(2, 0));
        paramByMonth.setScarcityParam(matrix.getAsDouble(3, 0));
        paramByMonth.setBrand_areaParam(matrix.getAsDouble(4, 0));
        paramByMonth.setSeasoning_flavorParam(matrix.getAsDouble(5, 0));
        paramByMonth.setIntercept(matrix.getAsDouble(6, 0));
        return paramByMonth;
    }

    public Matrix getParamByMonthByGradient(List<TeaInterested> teaList, Double regFactor, Double step, int iteration, String yearMonth) {
        int m = teaList.size();
        Double theta0 = Math.random();
        Double theta1 = Math.random();
        Double theta2 = Math.random();
        Double theta3 = Math.random();
        Double theta4 = Math.random();
        Double theta5 = Math.random();
        Double theta6 = Math.random();

        for (int i = 0; i < iteration; i++) {
            theta0 = theta0 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 0, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);
            theta1 = theta1 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 1, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);
            theta2 = theta2 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 2, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);
            theta3 = theta3 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 3, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);
            theta4 = theta4 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 4, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);
            theta5 = theta5 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 5, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);
            theta6 = theta6 * (1 - step * regFactor / m) - step / m * getGradient(teaList, 6, theta0, theta1, theta2, theta3,
                    theta4, theta5, theta6, yearMonth);

        }

        Matrix matrix = Matrix.Factory.zeros(7, 1);
        for (int i = 0; i < matrix.getSize()[0]; ++i) {
            for (int j = 0; j < matrix.getSize()[1]; ++j) {
                switch (i) {
                    case 0:
                        matrix.setAsDouble(theta1, i, j);
                        break;
                    case 1:
                        matrix.setAsDouble(theta2, i, j);
                        break;
                    case 2:
                        matrix.setAsDouble(theta3, i, j);
                        break;
                    case 3:
                        matrix.setAsDouble(theta4, i, j);
                        break;
                    case 4:
                        matrix.setAsDouble(theta5, i, j);
                        break;
                    case 5:
                        matrix.setAsDouble(theta6, i, j);
                        break;
                    case 6:
                        matrix.setAsDouble(theta0, i, j);
                        break;
                }
            }
        }
        return matrix;
    }

    public Double getGradient(List<TeaInterested> teaList, int index, Double theta0, Double theta1, Double theta2, Double theta3,
                              Double theta4, Double theta5, Double theta6, String yearMonth) {
        Double result = 0.0;
        if (index == 0) {
            for (TeaInterested tea : teaList) {
                result = result + theta0 + theta1 * tea.getByOrder(1) + theta2 * tea.getByOrder(2) + theta3 * tea.getByOrder(3) +
                        theta4 * tea.getByOrder(4) + theta5 * tea.getByOrder(5) + theta6 * tea.getByOrder(6) - teaInterestedPriceMonthCutDAO.getAvgPriceByGoodsIdAndYearMonth(tea.getGoodsId(), yearMonth);
            }
        } else {
            for (TeaInterested tea : teaList) {
                result = result + (theta0 + theta1 * tea.getByOrder(1) + theta2 * tea.getByOrder(2) + theta3 * tea.getByOrder(3) +
                        theta4 * tea.getByOrder(4) + theta5 * tea.getByOrder(5) + theta6 * tea.getByOrder(6)  - teaInterestedPriceMonthCutDAO.getAvgPriceByGoodsIdAndYearMonth(tea.getGoodsId(), yearMonth)) * tea.getByOrder(index);
            }
        }
        return result;
    }

    public Double getErrorRate(List<TeaInterested> teaListTrain, List<TeaInterested> teaListTest, Double RegFactor) {
        // TODO: 2018/6/4
        return 0.0;
    }

//    public void train() {
//        for (int i = 2008; i <= 2018; i++) {
//            for (int j = 1; j <= 12; j++) {
//                String yearMonth = null;
//                List<TeaInterested> teaList = new ArrayList<>();
//                if (j <= 9) {
//                    yearMonth = String.valueOf(i) + "-0" + String.valueOf(j);
//                } else {
//                    yearMonth = String.valueOf(i) + "-" + String.valueOf(j);
//                }
//                if (teaInterestedPriceMonthCutDAO.existOrNotByYearMonth(yearMonth)) {
//                    List<Integer> goodsIdList = teaInterestedPriceMonthCutDAO.getGoodsIdByYearMonth(yearMonth);
//                    for (Integer goodsId : goodsIdList) {
//                        teaList.add(teaInterestedDAO.getByGoodsId(goodsId));
//                    }
//                    Matrix matrix = getParamByMonthMatrix(teaList, 0.1, yearMonth);
//                    ParamByMonth paramByMonth=matrixToParamMonth(yearMonth, matrix);
//                    paramByMonthDAO.create(paramByMonth);
//                }
//
//            }
//        }
//    }
public void train(Double regFactor) {
    for (int i = 2008; i <= 2018; i++) {
        for (int j = 1; j <= 12; j++) {
            String yearMonth = null;
            List<TeaInterested> teaList = new ArrayList<>();
            if (j <= 9) {
                yearMonth = String.valueOf(i) + "-0" + String.valueOf(j);
            } else {
                yearMonth = String.valueOf(i) + "-" + String.valueOf(j);
            }
            if (teaInterestedPriceMonthCutDAO.existOrNotByYearMonth(yearMonth)) {
                List<Integer> goodsIdList = teaInterestedPriceMonthCutDAO.getGoodsIdByYearMonth(yearMonth);
                for (Integer goodsId : goodsIdList) {
                    teaList.add(teaInterestedDAO.getByGoodsId(goodsId));
                }
                Matrix matrix = getParamByMonthMatrix(teaList, regFactor, yearMonth);
                ParamByMonth paramByMonth=matrixToParamMonth(yearMonth, matrix);
                paramByMonthDAO.create(paramByMonth);
            }

        }
    }
}
    public void paint(){

    }

    }
