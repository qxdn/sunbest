## 节能减排模型
![AppVeyor](https://img.shields.io/appveyor/build/qxdn/sunbest)

根据经纬度，天气等约束，计算天窗最佳角度，每年节能效率。

## 模型来源
- 天窗模型由[孙文斌](https://github.com/Vincent726)和汪博文提供
- 天气模型来自和风天气 

## 构建之前
在```resources```目录下创建```weather.properties```文件
```properties
#预设纬度
weather.lat=
#预设经度
weather.lon=
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