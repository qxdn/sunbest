## 节能减排模型

根据经纬度，天气等约束，计算天窗最佳角度，每年节能效率。

## 模型来源
- 天窗模型由[孙文斌](https://github.com/Vincent726)和汪博文提供
- 天气模型来自和风天气 

## 构建之前
在```resources```文件夹里面更改```design.html```和```result.html```里面的
```html
 <script type="text/javascript" src="https://api.map.baidu.com/api?v=3.0&ak=你的密钥"></script>
```
将你的密钥替换为百度地图API申请到的密钥
在```resources```文件夹里面更改```application.yml```
```yml
spring:
  datasource:
    url: url
    username: username
    password: password
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
```
替换数据库

## 使用说明

[使用说明](./Instruction.md)