# “领袖意见分析”——基于高影响力人物追踪的国际舆论分析系统
本系统为“基于高影响力人物追踪的国际舆论分析系统”，旨在通过收集和分析高影响力用户在社交媒体上的言论，来研究和监测国际舆论动态。系统采用多种先进技术，包括自然语言处理、情感分析和知识图谱，能够对高影响力用户的情感倾向、社交网络关系及其对华情感进行深入分析。其核心功能模块包括高影响力用户数据收集、舆论热点分析、多模态舆情分析和用户画像构建。系统设计旨在提升舆情应对能力，为国家的国际传播战略提供数据支持和决策依据。

# 1. 产品设计
## 1.1 产品使命、口号和策略
### 1.1.1 使命
积极响应国家战略，为决策机构提供人物针对性舆情分析

### 1.1.2 使命口号
“智联全球观点，洞见舆论先机”

### 1.1.3 目标用户
国家决策机关、有决策需求的相关企业。

### 1.1.4 如何获得用户
1. 需求调研
2. 建立信任（试用）
3. 营销推广

### 1.1.5 团队优势
### 1.1.6 如何做到差异化
- 丰富的数据来源
- 精准的情感分析算法
- 高效的热点、动态识别
- 详细的社交网构建
- 直观的分析结果展示

## 1.2 产品定义
### 1.2.1 解决的问题
- 舆论动态监测缺乏
- 情感分析不足
- 多模态数据整合困难
- 决策支持信息不足
- 舆情应对能力不足

### 1.2.2 对客户的价值
- 实时舆论监测
- 深入情感分析
- 数据整合与可视化
- 数据驱动的决策支持
- 增强舆情应对能力
- 优化国际传播效果

### 1.2.3 解决方案
Web应用

# 2. 产品设计
- 需求驱动设计原则
- 信息准确性原则
- 收敛性原则
- 用户体验性原则
- 创新性设计原则

# 3. 产品实现
## 3.1 用户体验设计考虑的因素
## 3.2 产品所用编程语言及IDE、平台、框架等
| 类型 | 内容 | 版本号 |
| ---- | ---- | ---- |
| 语言 | Java | Temurin-17 |
| 语言 | JavaScript | |
| 语言 | Python | Python 3 |
| 平台 | PyCharm | |
| 平台 | IntelliJ IDEA | |

## 3.3 代码仓库链接及代码提交历史截图
包括每个人提交的次数截图 注意：理解git和github代码仓库原理
## 3.4 安装、设计、开发中遇到的主要问题及解决方法汇总
| 问题类别 | 问题名称 | 发生原因 | 解决办法 |
| ---- | ---- | ---- | ---- |

## 3.5 软件概要设计
### 3.5.1 系统架构图
如Web应用的三层架构
注意：通常架构图从底向上绘制较普遍，从左到右较少，可根据实际情况选择
### 3.5.2 基础设施图
如只有一台服务器，可不画。
### 3.5.3 技术
如果使用数据分析或者大数据/人工智能算法模型设计方法/区块链技术/数字孪生（虚拟现实XR）应用，请说明。
主要技术：
- OneKe: OneKE 主要聚焦基于 Schema 的可泛化信息抽取,采用了基于 Schema 的轮询指令构造技术。 [OneKE GitHub](https://github.com/zjunlp/DeepKE/blob/main/example/llm/InstructKGC/README_CN.md#612ie%E4%B8%93%E7%94%A8%E6%A8%A1%E5%9E%8B)

- 多模态情感分析模型：通过Bert提取推文文本特征，ResNet提取图像特征，并通过Self-Attention融合不同模态的特征，最终输出情感分析结果。 [多模态情感分析模型 GitHub](https://github.com/liyunfan1223/multimodal-sentiment-analysis)

- 图数据库：图数据库是一种基于图论为数据基础的数据管理系统，它将数据表示为点和边，其中点代表实体或实例，边代表实体之间的关系。 [图数据库 GitHub](https://github.com/iansrobinson/graph-databases-use-cases) [另一个图数据库 GitHub](https://github.com/datastax/graph-book)

其他技术：
- 贝叶斯分类器：贝叶斯分类器是一种基于概率论和贝叶斯定理的分类算法，它利用已知的数据来对未知数据进行分类。

- Selenium自动化测试：Selenium自动化测试爬虫是一种使用Selenium库来实现的网页自动化爬取技术。

### 3.5.4 数据库设计
包括E-R图即可，可使用数据库工具基于已有的数据库表逆向生成
### 3.5.5 接口设计
重要的业务功能（可与用户故事对应）和工具类设计
| 接口名称 | 接口功能 | 输入变量或对象 | 输出对象 | 备注 |
| ---- | ---- | ---- | ---- | ---- |
| payAli | 调用Alipay接口 | inputObj1，inputObj2，。。。 | outputobj1，outputobj2，。。。 | 需向支付宝申请付款账号 |

列出方法描述，包括方法名，输入变量（如果有），返回值（如果有），可参考 [微信支付API统一下单](https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1)

工具 [Swagger](https://swagger.io/)
Java服务器端设计，例如Spring MVC，可使用JavaDoc自动生成，也可使用APIFox自动生成
### 3.5.6 前端设计
例如 使用jQuery、BootStrap或者React、Vue.js框架等
## 3.6 软件质量
### 3.6.1 质量控制和保证方法
- 代码需要增加必要的日志
- 代码需要增加异常和错误处理
### 3.6.2 质量工具使用
展示遵循Java代码规范的p3c扫描记录。 [p3c GitHub](https://github.com/alibaba/p3c) 在IDE中可安装插件Alibaba Java Coding Guidelines，扫描记录中不能有blocker。
展示遵循Python代码规范PEP8的结果。 对代码 Pycharm IDE，参考 [PyCharm代码质量辅助](https://www.jetbrains.com/help/pycharm/tutorial-code-quality-assistance-tips-and-tricks.html)。对VSCode IDE，使用autopep8自动格式化Python代码使其符合PEP8风格。参考 [PEP8风格指南](https://henryeleonu.com/how-to-encourage-best-practices-in-python-programming-by-complying-with-pep8-style-guide)
Python代码质量：工具与最佳实践 [Real Python](https://realpython.com/python-code-quality/)
展示Pylint的Python代码语义分析结果。 参考 [VSCode Pylint](https://code.visualstudio.com/docs/python/linting), [Pylint插件](https://pylint.pycqa.org/en/latest/user_guide/run.html)

Java代码复杂度，展示插件metricsreloaded的运行结果，也可安装插件CodeMetrics查看复杂度
## 3.7 代码统计
Jetbrain系列IDE可以使用Statistic插件
VS Code可用VS Code Counter插件
模块数
例如代码的文件目录、包等。

行数
可以分不同程序语言说明。通常插件会完成此功能。
