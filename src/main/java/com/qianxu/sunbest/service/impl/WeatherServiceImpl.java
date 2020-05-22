package com.qianxu.sunbest.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qianxu.sunbest.config.WeatherConfig;
import com.qianxu.sunbest.model.SolarMsg;
import com.qianxu.sunbest.model.WeatherMsg;
import com.qianxu.sunbest.service.api.WeatherService;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    @Autowired
    WeatherConfig weatherConfig;

    @Autowired
    CloseableHttpClient httpClient;

    @Override
    public WeatherMsg getWeather(Double lat, Double lon) {
        String location=lon+","+lat;
        Map<String,Integer> aMap=getAir(location);
        Map<String,Double> pMap=getPcpn(location);
        if(pMap!=null&&aMap!=null){
            WeatherMsg weatherMsg=new WeatherMsg();

            weatherMsg.setPcpn(pMap.get("pcpn"));
            weatherMsg.setPm25(aMap.get("pm25"));
            weatherMsg.setPm10(aMap.get("pm10"));
            return weatherMsg;
        }
        return null;
    }

    @Override
    public SolarMsg getSolar(Double lat, Double lon, Integer alt) {
        StringBuffer params=solarParam(lat,lon,alt);
        HttpPost httpPost=new HttpPost(weatherConfig.getApiUrl()+"/solar/solar-elevation-angle?"+params);
        String Json=getJson(httpPost);
        return getSolarFromJson(Json);
    }


    private Map<String, Double> getPcpn(String location) {
        // 参数
        StringBuffer params =getParams(location);
        // 创建POST请求
        HttpPost httpPost = new HttpPost(weatherConfig.getApiUrl() + "/weather/now?" + params);
        String json= getJson(httpPost);
        if(json!=null){
            return getPcpnFromJson(json);
        }
        return null;
    }


    private StringBuffer solarParam(Double lat, Double lon, Integer alt){
        log.debug("设置url参数");
        Date now=new Date();
        SimpleDateFormat date=new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat time=new SimpleDateFormat("HHmm");
        // 参数
        StringBuffer params = new StringBuffer();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            params.append("lat=" + URLEncoder.encode(lat.toString(), "utf-8"));
            params.append("&");
            params.append("lon="+URLEncoder.encode(lon.toString(),"utf-8"));
            params.append("&");
            params.append("date="+URLEncoder.encode(date.format(now),"utf-8"));
            params.append("&");
            params.append("time="+URLEncoder.encode(time.format(now),"utf-8"));
            params.append("&");
            params.append("alt="+URLEncoder.encode(alt.toString(),"utf-8"));
            params.append("&");
            params.append("tz=8");
            params.append("&");
            params.append("key=" + URLEncoder.encode(weatherConfig.getApiKey(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        }
        return  params;
    }

    /**
     * 从httpPost中获取Json字符串
     * @param httpPost
     * @return
     */
    private String getJson(HttpPost httpPost){
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Post请求
            response = httpClient.execute(httpPost);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            return EntityUtils.toString(responseEntity);
        } catch (Exception e) {
            log.error(e.toString());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        return null;
    }

    private SolarMsg getSolarFromJson(String json){
        if(json!=null){
            JSONObject jsonObject=JSON.parseObject(json);
            JSONObject solar_elevation_angle=((JSONObject)jsonObject.getJSONArray("HeWeather6").get(0)).getJSONObject("solar_elevation_angle");
            SolarMsg solarMsg=(SolarMsg) JSON.parseObject(solar_elevation_angle.toJSONString(), SolarMsg.class);
            return solarMsg;
        }
        return null;
    }

    /**
     * 参数化
     * @param location
     * @return
     */
    private StringBuffer getParams(String location){
        log.debug("设置url参数");
        // 参数
        StringBuffer params = new StringBuffer();
        try {
            // 字符数据最好encoding以下;这样一来，某些特殊字符才能传过去(如:某人的名字就是“&”,不encoding的话,传不过去)
            params.append("location=" + URLEncoder.encode(location, "utf-8"));
            params.append("&");
            params.append("key=" + URLEncoder.encode(weatherConfig.getApiKey(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error(e.toString());
        }
        return  params;
    }

    /**
     * 从Json字符串中获取降雨量
     * @param json
     * @return
     */
    private Map<String, Double> getPcpnFromJson(String json) {
        log.debug("转换降雨量json");
        Map<String, Double> pcpnMap = new HashMap<String, Double>();
        JSONObject jsonObject= JSON.parseObject(json);
        double pcpn=((JSONObject)jsonObject.getJSONArray("HeWeather6").get(0)).getJSONObject("now").getDouble("pcpn");
        pcpnMap.put("pcpn",pcpn);
        return pcpnMap;
    }

    /**
     * 获取
     * @param location
     * @return
     */
    private Map<String, Integer> getAir(String location) {
        // 参数
        StringBuffer params =getParams(location);
        // 创建POST请求
        HttpPost httpPost = new HttpPost(weatherConfig.getApiUrl() + "/air/now?" + params);
        String json= getJson(httpPost);
        if(json!=null){
            return getAirFromJson(json);
        }
        return  null;
    }
    /**
     * 从Json字符串中获取PM25 PM10
     * @param json
     * @return
     */
    private Map<String,Integer> getAirFromJson(String json){
        log.debug("转换空气质量json");

        Map<String,Integer> airMap=new HashMap<String, Integer>();
        JSONObject jsonObject=JSON.parseObject(json);
        JSONObject air_now_city=((JSONObject)jsonObject.getJSONArray("HeWeather6").get(0)).getJSONObject("air_now_city");
        Integer pm25=air_now_city.getInteger("pm25");
        Integer pm10=air_now_city.getInteger("pm10");
        airMap.put("pm25",pm25);
        airMap.put("pm10",pm10);
        return airMap;
    }



}