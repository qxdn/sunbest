## 节能减排模型

根据经纬度，天气等约束，计算天窗最佳角度，每年节能效率。

## 模型来源
- 天窗模型由[孙文斌](https://github.com/Vincent726)和汪博文提供
- 天气模型来自和风天气 

## 构建之前
在```resources```文件夹里面更改```design.html```和```result.html```里面的你的密匙替换成百度key地图申请到的apikey
```html
 <script type="text/javascript" src="https://api.map.baidu.com/api?v=3.0&ak=你的密钥"></script>
```


在```resources```文件夹里面更改```application.yml```替换数据库
```yml
spring:
  datasource:
    url: url
    username: username
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
```

在```resources```文件夹里面更改```mqtt.properties```替换mqtt服务器
```properties
#mqtt
mqtt.username=javaserver
mqtt.password=123456

#mqtt client
mqtt.clientId=weatherServer


mqtt.subTopic[0]=getSunMsg
mqtt.pubTopic[0]=sunMsg

#mqtt tcp
mqtt.tcpHost=your host
```
在```resources```文件夹里面更改```weather.properties```替换和风天气的apikey
```properties
#预设纬度
weather.lat=30.58
#预设经度
weather.lon=114.27
#免费api
weather.freeUrl=https://free-api.heweather.net/s6
#付费api
weather.apiUrl=https://api.heweather.net/s6
#付费key
weather.apiKey=your key
#免费key
weather.freeKey=your key
```

## 使用说明

[使用说明](./Instruction.md)