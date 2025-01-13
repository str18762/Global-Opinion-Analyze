<template>
  <div style="margin:20px 100px">
    <el-card style="border-radius: 20px;padding: 50px 60px;">
      <div style="text-align: center">
        <strong style="font-size: 35px">《高影响力人物国际舆情分析与预测系统》流程概述</strong>
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">1. 数据采集</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        系统通过爬虫技术从全球范围内的新闻网站、社交媒体、论坛等平台实时抓取与目标人物相关的舆情信息。爬虫技术能够高效地收集大量数据，确保信息的实时性和全面性。
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">2. 数据预处理</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        采集到的数据经过清洗、去重、格式化等预处理步骤，确保数据的质量和一致性。清洗步骤包括去除噪声数据和无关信息；去重步骤确保数据的唯一性；格式化步骤将数据转换为统一的格式，便于后续分析。
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">3. 情感分析</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        利用自然语言处理技术，分析舆情信息的情感倾向（正面、负面、中立），并识别出关键情感词。自然语言处理技术能够自动识别文本中的情感信息，帮助系统理解公众对目标人物的态度。
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">4. 话题分析</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        对舆情信息进行主题聚类，识别出与目标人物相关的热点话题和舆论焦点。主题聚类方法能够将相似的舆情信息归类，帮助系统发现公众关注的核心议题。
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">5. 传播分析</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        分析舆情信息的传播路径、传播速度和传播范围，识别出关键传播节点和影响力较大的媒体平台。传播分析能够帮助系统了解舆情信息的扩散情况，识别出重要的传播渠道。
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">6. 趋势预测</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        基于历史数据和当前舆情态势，利用机器学习算法预测未来一段时间内舆情的发展趋势。机器学习算法能够从大量数据中学习模式，提高预测的准确性和可靠性。
      </div>

      <div style="margin-top: 30px">
        <strong style="font-size: 25px">7. 预警机制</strong>
      </div>
      <div>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        当出现负面舆情或舆情热度超过预设阈值时，系统会自动发出预警，提醒相关人员及时关注和应对。预警机制能够帮助系统及时发现潜在风险，确保相关人员能够迅速采取行动。
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'SystemProcess',
};
</script>
