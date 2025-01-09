<template>
  <div class="introduction-container">
    <div class="background-image"></div>
    <div class="content">
      <h1>背景介绍</h1>
      <p>在当今全球化和信息爆炸的时代，高影响力人物（包括政治领袖、企业家、明星、学者等）的一言一行都可能引发国际社会的广泛关注，甚至影响国际局势、市场走向和社会舆论。因此，对高影响力人物的国际舆情进行实时监测、分析和预测，具有重要的现实意义。</p>

      <h2>一、 系统建设的必要性</h2>
      <h3>维护国家利益和形象</h3>
      <p>高影响力人物的言论和行为可能被国际媒体放大解读，甚至被恶意炒作，损害国家利益和形象。及时掌握相关舆情，有利于主动应对，维护国家利益。</p>

      <h3>辅助决策制定</h3>
      <p>高影响力人物的动态往往与国际局势、经济发展、社会思潮等密切相关。对相关舆情进行分析和预测，可以为政府、企业等机构的决策提供参考依据。</p>

      <h3>防范潜在风险</h3>
      <p>高影响力人物的负面舆情可能引发社会动荡、经济波动等风险。通过舆情监测和分析，可以及时发现潜在风险，并采取有效措施进行防范。</p>

      <h2>二、 系统功能概述</h2>
      <p>高影响力人物国际舆情分析与预测系统旨在利用大数据、人工智能等技术，实现对高影响力人物国际舆情的全方位、多维度、智能化分析。系统主要功能包括：</p>
      <ul>
        <li><strong>舆情监测:</strong> 实时抓取全球范围内新闻网站、社交媒体、论坛等平台中与目标人物相关的舆情信息。</li>
        <li><strong>情感分析:</strong> 利用自然语言处理技术，分析舆情信息的情感倾向（正面、负面、中立），并识别出关键情感词。</li>
        <li><strong>话题分析:</strong> 对舆情信息进行主题聚类，识别出与目标人物相关的热点话题和舆论焦点。</li>
        <li><strong>传播分析:</strong> 分析舆情信息的传播路径、传播速度和传播范围，识别出关键传播节点和影响力较大的媒体平台。</li>
        <li><strong>趋势预测:</strong> 基于历史数据和当前舆情态势，利用机器学习算法预测未来一段时间内舆情的发展趋势。</li>
        <li><strong>预警机制:</strong> 当出现负面舆情或舆情热度超过预设阈值时，系统会自动发出预警，提醒相关人员及时关注和应对。</li>
      </ul>

      <h2>三、 系统应用场景</h2>
      <p>高影响力人物国际舆情分析与预测系统可应用于以下场景：</p>
      <ul>
        <li><strong>政府部门:</strong> 用于监测和分析外国政要、国际组织负责人等对华言论，评估其对国家利益的影响，并制定相应的外交策略。</li>
        <li><strong>企业机构:</strong> 用于监测和分析行业领袖、竞争对手、合作伙伴等对企业形象和业务发展的影响，并及时调整企业战略。</li>
        <li><strong>媒体机构:</strong> 用于追踪热点人物和事件，挖掘新闻线索，并进行深度报道和分析。</li>
        <li><strong>学术研究:</strong> 用于研究高影响力人物与国际舆情之间的互动关系，以及舆情传播规律和影响因素。</li>
      </ul>

      <h2>四、 未来发展趋势</h2>
      <p>随着技术的不断进步和应用场景的不断拓展，高影响力人物国际舆情分析与预测系统将朝着更加智能化、精准化和个性化的方向发展：</p>
      <ul>
        <li><strong>多模态数据分析:</strong> 除了文本信息，系统还将整合图像、视频、音频等多模态数据，进行更加全面和精准的舆情分析。</li>
        <li><strong>深度学习模型应用:</strong> 利用深度学习模型，提高情感分析、话题分析、趋势预测等功能的准确性和效率。</li>
        <li><strong>个性化定制服务:</strong> 根据不同用户的需求，提供个性化的舆情监测、分析和预测服务。</li>
      </ul>

      <p>总而言之，高影响力人物国际舆情分析与预测系统是应对复杂国际形势、维护国家利益、辅助决策制定的重要工具。随着技术的不断发展和应用场景的不断拓展，该系统将在未来发挥更加重要的作用。</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Introduction'
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

h3 {
  font-size: 24px;
  margin-top: 20px;
  margin-bottom: 10px;
}

p {
  font-size: 18px;
  line-height: 1.6;
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
