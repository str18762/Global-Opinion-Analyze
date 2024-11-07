<template>
  <div style="margin:20px 100px">
    <el-card style="border-radius: 20px">
      <div style="text-align: center"><strong style="font-size: 35px">《基于高影响力的人物追踪系统》常见问题及解答</strong></div>
      <div style="margin-top: 30px"><strong style="font-size: 25px">1.为什么要开发这样一套系统？</strong></div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        在全球化背景下，国际舆论场日益复杂多变，任何一起负面舆情都可能对国家形象和国家利益造成严重影响。
        在此背景下，高影响力人群往往拥有广泛的社交网络和强大的影响力，他们的观点和行为能够影响大量受众，对高影响力用户构建人物画像，识别其情感倾向，对监管引导舆情与国家战略决策至关重要。
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        本系统积极响应国家战略要求，收集国际政治、经济、文化等领域高影响力人群的言论和行为，了解他们对中国的看法和态度并提供多维度的分析，及时发现可能对国家形象和国家利益造成负面影响的舆情事件，帮助中国更好地应对舆情。
        对情感倾向和态度变化深入分析，我们可以更好地理解国际社会的需求和期望，推动中国在国际舞台上发挥更大作用，为实现可持续的经济增长和解决战略问题创造有利条件
      </div>
      <div style="margin-top: 30px"><strong style="font-size: 25px">2.数据来源于哪里？</strong></div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        本系统使用基于 Python 语言的 web 自动化测试框架 selenium 和 Google 开 发者的 Youtube 接口，
        从 Twitter、Facebook、Youtube等国际知名社交平台爬取海量的网络人物数据。
        对爬取各大平台的数据、进行数据清洗、处理以及将处理过后的数据存入数据库中。
      </div>
      <div style="margin-top: 30px"><strong style="font-size: 25px">3.本系统是如何分析的？</strong></div>
      <div>
        <div>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          本系统使用基于Bert(BidirectionalEncoder Representations from Transformer) 模型的多模态细粒度舆情分析算法，
          其中Bert在预训练中使用“MLM”(MaskLanguage Model)和“NSP”(Next Sentence Prediction)来训练样本，提高下游句子关系判断任务的表现；
          采用多模态数据特征融合将来自不同来源或具有不同特征类型的数据（如视频、音频、文本等）整合在一起建立模型，它们各自提供了不同的信息视角；
          针对涉华事件，为识别高影响力人群更为确切的情感倾向，本系统对情感标签分类机制进行优化，提高模型细粒度，在一级粗粒度情感标签（如积极、消极、中立）分类机制基础上，还通过继续训练的方法扩展二级细粒度情感标签（如高兴、感激、内疚、伤心、恐惧、失望、
          焦虑等），以达到对高影响力人群舆情进行精准分析和预测的效果。

        </div>
      </div>
      <div style="margin-top: 30px"><strong style="font-size: 25px">4.系统运行流程是怎么样的？</strong></div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        《基于高影响力的人物追踪系统》基于实时更新的国际社交平台和新闻媒体数据，对多个国家的高影响力人物采用核心算法进行情感分析，
        实现人物画像、人物关联图谱、舆论热点词云挖掘等功能供用户使用
      </div>
      <div style="width: 100%;display: flex;justify-content: center">
        <el-image :src="require('@/assets/Images/系统流程图.png')"></el-image>
      </div>

    </el-card>
  </div>
</template>
<script>


export default {
  name: 'HelpCenter',
  components:{

  },
  data(){
    return{
    }
  }
};
</script>
