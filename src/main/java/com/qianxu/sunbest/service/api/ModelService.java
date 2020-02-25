package com.qianxu.sunbest.service.api;

import com.qianxu.sunbest.model.Answer;
import com.qianxu.sunbest.model.UserDefine;

public interface ModelService {
    /**
     *
     * @param userDefine  用户定义 见model包
     * @return  返回需要显示的 见model包
     */
    public Answer getAnswer(UserDefine userDefine);
}
