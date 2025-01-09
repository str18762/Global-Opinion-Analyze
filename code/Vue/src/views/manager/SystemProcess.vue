<template>
  <div class="introduction-container">
    <div class="background-image"></div>
    <div class="content">
      <h1>系统流程</h1>
      <p>高影响力人物国际舆情分析与预测系统的整体流程如下：</p>

      <h2>一、 数据采集</h2>
      <p>系统通过爬虫技术从全球范围内的新闻网站、社交媒体、论坛等平台实时抓取与目标人物相关的舆情信息。爬虫技术能够高效地收集大量数据，确保信息的实时性和全面性。</p>

      <h2>二、 数据预处理</h2>
      <p>采集到的数据经过清洗、去重、格式化等预处理步骤，确保数据的质量和一致性。清洗步骤包括去除噪声数据和无关信息；去重步骤确保数据的唯一性；格式化步骤将数据转换为统一的格式，便于后续分析。</p>

      <h2>三、 情感分析</h2>
      <p>利用自然语言处理技术，分析舆情信息的情感倾向（正面、负面、中立），并识别出关键情感词。自然语言处理技术能够自动识别文本中的情感信息，帮助系统理解公众对目标人物的态度。</p>

      <h2>四、 话题分析</h2>
      <p>对舆情信息进行主题聚类，识别出与目标人物相关的热点话题和舆论焦点。主题聚类方法能够将相似的舆情信息归类，帮助系统发现公众关注的核心议题。</p>

      <h2>五、 传播分析</h2>
      <p>分析舆情信息的传播路径、传播速度和传播范围，识别出关键传播节点和影响力较大的媒体平台。传播分析能够帮助系统了解舆情信息的扩散情况，识别出重要的传播渠道。</p>

      <h2>六、 趋势预测</h2>
      <p>基于历史数据和当前舆情态势，利用机器学习算法预测未来一段时间内舆情的发展趋势。机器学习算法能够从大量数据中学习模式，提高预测的准确性和可靠性。</p>

      <h2>七、 预警机制</h2>
      <p>当出现负面舆情或舆情热度超过预设阈值时，系统会自动发出预警，提醒相关人员及时关注和应对。预警机制能够帮助系统及时发现潜在风险，确保相关人员能够迅速采取行动。</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'SystemProcess'
}
</script>

<style scoped>
.introduction-container {
  position: relative;
  width: 100%;
  height: auto;
  overflow: hidden;
  padding: 50px;
  background-color: #f0f5f9; /* 修改: 背景颜色改为 #f0f5f9 */
}

.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  /*background-image: url('https://via.placeholder.com/1920x1080'); /* 替换为你的背景图片URL */
  background-size: cover;
  background-position: center;
  opacity: 0.7; /* 调整背景图片的透明度 */
}

.content {
  position: relative;
  z-index: 1;
  color: black; /* 修改: 文本颜色改为 black */
  text-align: left;
  padding: 50px;
  background: #eceeef; /* 修改: 卡片颜色改为 #eceeef */
  border-radius: 10px;
}

h1 {
  font-size: 48px;
  margin-bottom: 20px;
}

h2 {
  font-size: 36px;
  margin-top: 40px;
  margin-bottom: 20px;
}

p {
  font-size: 18px;
  line-height: 1.6;
}

img {
  display: block;
  margin: 0 auto;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

ul li {
  margin-bottom: 10px;
}

ul li strong {
  font-size: 20px;
}
</style>
