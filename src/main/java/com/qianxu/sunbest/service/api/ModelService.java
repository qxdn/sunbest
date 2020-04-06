package com.qianxu.sunbest.service.api;

import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.Predict;
import com.qianxu.sunbest.model.UserDefine;

public interface ModelService {
    /**
     *
     * @param userDefine  用户定义 见model包
     * @return  返回需要显示的 见model包
     */
    public Answer getAnswer(UserDefine userDefine);

    /**
     *
     * @param predict 经纬度方位角 见model包
     * @return  最佳倾角
     */
    public Double getBestAngle(Predict predict);

    /**
     * 
     * @param area 天窗面积
     * @param number 开窗个数
     * @return 开窗成本
     */
    public Double getWindowsCos(Double area,int number);


    /**
     * 
     * @param houseArea 房屋成本
     * @param windowsArea 天窗成本
     * @return
     */
    public Double getBoardCos(Double houseArea,Double windowsArea);

    /**
     * 
     * @param number 舵机数量
     * @return 其他成本
     */
    public Double getOtherCos(Integer number); 
}
