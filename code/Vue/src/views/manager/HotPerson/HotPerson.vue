<template>
  <div class="out_box">
    <dv-border-box-11 id="body_box" title="热评人物" style="padding: 60px 10px 10px;">
<!--      左-->
      <div style="flex: 1;height: 100%">
        <div style="height: 60%">
          <dv-border-box-9 class="dv_content">
            <div style="height: 50%;width: 100%">
              <h2>列表滚动人物相关新闻</h2>
            </div>
            <div style="height: 50%;width: 100%">
              <h2>公众对事件情感分析</h2>
            </div>
          </dv-border-box-9>
        </div>
        <div style="height: 40%">
          <dv-border-box-9 class="dv_content">
            <h2>词云图</h2>
            <div style="height: 80%;width: 100%">
              <WordCloud :chartData="wordCloudData"></WordCloud>
            </div>
          </dv-border-box-9>
        </div>
      </div>
<!--      中-->
      <div style="flex: 2;height: 100%">
        <div style="height: 20%">
          <dv-border-box-9 class="dv_content">
            <div style="width: 100%;height: 50%;display: flex;justify-content: center">
              <el-select v-model="selectedChara" filterable  placeholder="选择人物" @change="changeEvent" style="width: 50%">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
              </el-select>
              <div style="position: absolute;right: 5%">
                <div style="display: flex;justify-content: center;">
                  <el-link :underline="false" type="danger" style="font-size: 20px" @click="$router.push('/homePage')">
                    返回首页<i class="el-icon-d-arrow-right"/>
                  </el-link>
                </div>
              </div>
            </div>
            <div style="width: 100%;height: 50%;display: flex;">
              <el-button class="trans_button" style="flex: 1">人物影响力分析</el-button>
              <el-button class="trans_button" style="flex: 1" @click="$router.push('/hotPerson/correlation')">人物社交关系分析</el-button>
              <el-button class="trans_button" style="flex: 1">？？？</el-button>
            </div>
          </dv-border-box-9>
        </div>
        <div style="height: 80%">
          <dv-border-box-9 class="dv_content">
            <h2>人物影响力分析</h2>
          </dv-border-box-9>
        </div>
      </div>
<!--      右-->
      <div style="flex: 1;height: 100%">
        <div style="height: 60%">
          <dv-border-box-9 class="dv_content">
            <h2>人物近期推文</h2>
            <div style="height: 80%">
              <el-scrollbar style="height: 100%" wrap-style="overflow-x:hidden;">
                <ul class="tweet-list">
                  <li v-for="(tweet, index) in tweets" :key="index">{{ tweet }}</li>
                </ul>
              </el-scrollbar>
            </div>
          </dv-border-box-9>
        </div>
        <div style="height: 40%">
          <dv-border-box-9 class="dv_content">
            <h2>公众情感</h2>
            <div style="height: 80%;width: 100%">
              <CharaEmotionPie :chartData="charaEmotionPieData"></CharaEmotionPie>
            </div>
          </dv-border-box-9>
        </div>
      </div>

    </dv-border-box-11>
  </div>
</template>

<script>

import CharaEmotionPie from "@/components/echarts/HotPerson/CharaEmotionPie.vue";
import WordCloud from "@/components/echarts/HotPerson/WordCloud.vue";

export default {
  name: 'Screen2',
  components: {
    CharaEmotionPie,
    WordCloud,
  },
  data() {
    return {
      options: [
        {
          label:'马斯克',
          value:'@elonmusk',
        },
        {
          label:'奥巴马',
          value:'@BarackObama',
        },
        {
          label:'莫迪',
          value:'@narendramodi',
        },
        {
          label:'NASA',
          value:'@NASA',
        },
        {
          label:'比尔盖茨',
          value:'@BillGates',
        },
      ],
      selectedChara:'@elonmusk',

      dataset: [
        {
          id: '1',
          name: '@elonmusk',
          value:'@elonmusk',
          tweets:[
            "没有言语可以形容这场灾难。",
            "那些官员应该被关进监狱很长时间。",
            '因为答案基本上是零。尽管关于儿童性剥削的抗议声浪和要求对虐待进行新的国家支持调查的呼声不断，内政部仍拒绝将相关数据公开。链接内容部分似乎指向新闻报道，内容关于内政部拒绝公开与“性引诱犯罪者”相关的数据，尽管公众要求加强对儿童性虐待案件的关注和调查。',
            '@benhabib6 对现实情况的准确描述，谢谢你，@benhabib6！谢谢你说出真相：汤米·罗宾逊是一个政治犯。终于有人敢说出真相了——谢谢你，Ben Habib！在这个沉默和共谋占主导地位的时代，Habib 承认这一点。',
            '有趣的观点，奈杰尔·法拉奇（Nigel Farage）表示，改革党（Reform UK）必须拥抱伊斯兰教才能获胜。他可能患有斯德哥尔摩综合症。\n'
          ],
          emotion: [4,0, 0, 11, 1,6],
          wordCloudData: [
            {"name": "elonmusk", "value": 1},
            {"name": "英国", "value": 0.32},
            {"name": "一个", "value": 0.28},
            {"name": "埃隆 马斯克", "value": 0.28},
            {"name": "美国", "value": 0.24},
            {"name": "我们", "value": 0.24},
            {"name": "要么", "value": 0.24},
            {"name": "VivekGRame", "value": 0.2},
            {"name": "HIB", "value": 0.2},
            {"name": "签证", "value": 0.2},
            {"name": "埃隆", "value": 0.16},
            {"name": "CR", "value": 0.16},
            {"name": "这些", "value": 0.16},
            {"name": "看看", "value": 0.16},
            {"name": "那些", "value": 0.16},
            {"name": "他们", "value": 0.16},
            {"name": "莫斯科", "value": 0.12},
            {"name": "同意", "value": 0.12},
            {"name": "realDonalc", "value": 0.12},
            {"name": "支持", "value": 0.12},
            {"name": "无法", "value": 0.12},
            {"name": "这个", "value": 0.12},
            {"name": "没有", "value": 0.12},
            {"name": "如果", "value": 0.12},
            {"name": "不是", "value": 0.12},
            {"name": "希望", "value": 0.12},
            {"name": "女孩", "value": 0.12},
            {"name": "什么", "value": 0.12},
            {"name": "因为", "value": 0.12},
            {"name": "SpaceX", "value": 0.08},
            {"name": "正在", "value": 0.08},
            {"name": "所有", "value": 0.08},
            {"name": "观点", "value": 0.08},
            {"name": "昨晚", "value": 0.08},
            {"name": "未来", "value": 0.08},
            {"name": "应该", "value": 0.08},
            {"name": "特朗普", "value": 0.08},
            {"name": "总统", "value": 0.08},
            {"name": "团伙", "value": 0.08},
            {"name": "HouseGOP", "value": 0.08},
            {"name": "最终", "value": 0.08},
            {"name": "DOGE", "value": 0.08},
            {"name": "障碍", "value": 0.08},
            {"name": "Bannon", "value": 0.08},
            {"name": "爱因斯坦", "value": 0.08},
            {"name": "公司", "value": 0.08},
            {"name": "Elon", "value": 0.08},
            {"name": "第一", "value": 0.08},
            {"name": "反对", "value": 0.08}
          ],
        },
        {
          id: '2',
          name:'@BarackObama',
          value:'@BarackObama',
          tweets:[
            '卡特总统教会了我们什么是充满优雅、尊严、正义和服务的一生。米歇尔和我将我们的思念与祈祷送给卡特家族，以及所有曾爱过并从这位非凡人士身上学到东西的人。',
            '这是我今年最喜欢的歌曲！如果你想换换歌单，快来听听吧——如果你有推荐的歌曲或艺人，告诉我，我一定会去听！',
            '这是我推荐你今年看看的一些电影。',
            '提醒一下，这是我夏季阅读清单上的一些书籍。',
            '祝我的朋友 @JoeBiden 生日快乐！',
          ],
          emotion: [2, 0, 0, 11, 0, 1],
          wordCloudData: [
            {"name": "BarackOban", "value": 1},
            {"name": "总统", "value": 0.5},
            {"name": "我们", "value": 0.4375},
            {"name": "投票", "value": 0.3125},
            {"name": "黑人", "value": 0.3125},
            {"name": "不是", "value": 0.25},
            {"name": "来自", "value": 0.1875},
            {"name": "支持", "value": 0.1875},
            {"name": "奥巴马", "value": 0.1875},
            {"name": "这是", "value": 0.1875},
            {"name": "一个", "value": 0.1875},
            {"name": "生日快乐", "value": 0.1875},
            {"name": "朋友", "value": 0.1875},
            {"name": "得到", "value": 0.125},
            {"name": "谢谢", "value": 0.125},
            {"name": "所有", "value": 0.125},
            {"name": "美国", "value": 0.125},
            {"name": "这个", "value": 0.125},
            {"name": "一起", "value": 0.125},
            {"name": "卡玛", "value": 0.125},
            {"name": "特朗普", "value": 0.125},
            {"name": "真的", "value": 0.125},
            {"name": "喜欢", "value": 0.125},
            {"name": "法兰绒", "value": 0.125},
            {"name": "SmartLess", "value": 0.125},
            {"name": "一刻", "value": 0.0625},
            {"name": "MichelleOk", "value": 0.0625},
            {"name": "你们", "value": 0.0625},
            {"name": "意义", "value": 0.0625},
            {"name": "重大", "value": 0.0625},
            {"name": "开始", "value": 0.0625},
            {"name": "工作", "value": 0.0625},
            {"name": "提前", "value": 0.0625},
            {"name": "蓝色", "value": 0.0625},
            {"name": "自己", "value": 0.0625},
            {"name": "亲人", "value": 0.0625},
            {"name": "其他", "value": 0.0625},
            {"name": "尤其", "value": 0.0625},
            {"name": "女性", "value": 0.0625},
            {"name": "创造", "value": 0.0625},
            {"name": "美好", "value": 0.0625},
            {"name": "未来", "value": 0.0625},
            {"name": "情人节", "value": 0.0625},
            {"name": "快乐", "value": 0.0625},
            {"name": "一年", "value": 0.0625},
            {"name": "越来越", "value": 0.0625},
            {"name": "信息", "value": 0.0625},
            {"name": "发给", "value": 0.0625},
            {"name": "统治者", "value": 0.0625},
            {"name": "之王", "value": 0.0625}
          ],
        },
        {
          id: '3',
          name:'@narendramodi',
          value:'@narendramodi',
          tweets:[
            '我向斯里古鲁戈宾德辛格吉（Sri Guru Gobind Singh Ji）在他诞辰纪念日致敬。他的思想激励我们建立一个进步、繁荣和充满同情的社会。',
            '我在斯里古鲁戈宾德辛格吉的诞辰节日（Prakash Utsav）向他致敬。他的思想激励我们建设一个进步、繁荣和充满同情的社会。',
            '在‘??? ????’列车的萨希巴???-阿肖克纳格尔新走廊上旅行时，我的年轻伙伴们的非凡才华为我注入了新的活力。',
            '居住在德里贫民窟的人们将获得像‘自尊公寓’这样的住房，这是莫迪的保证。',
            '今天，关于你们党派的意图和忠诚，提出了最大的质疑……',
          ],
          emotion: [2, 0, 0, 21, 0, 0],
          wordCloudData: [
            {"name": "我们", "value": 1},
            {"name": "印度", "value": 0.9130434782608695},
            {"name": "总理 narer", "value": 0.8095652173913044},
            {"name": "narendramo", "value": 0.4782608695652174},
            {"name": "尊敬 总理", "value": 0.30434782608695654},
            {"name": "先生", "value": 0.2608695652173913},
            {"name": "荣幸", "value": 0.2173913043478261},
            {"name": "以及", "value": 0.2173913043478261},
            {"name": "高兴", "value": 0.2173913043478261},
            {"name": "非常", "value": 0.2173913043478261},
            {"name": "他们", "value": 0.2173913043478261},
            {"name": "PMOIndia", "value": 0.2173913043478261},
            {"name": "心脏", "value": 0.2173913043478261},
            {"name": "Shri nare", "value": 0.2173913043478261},
            {"name": "见到", "value": 0.17391304347826086},
            {"name": "narendramo", "value": 0.17391304347826086},
            {"name": "真的", "value": 0.17391304347826086},
            {"name": "谢谢", "value": 0.17391304347826086},
            {"name": "这个", "value": 0.17391304347826086},
            {"name": "时刻", "value": 0.17391304347826086},
            {"name": "进行", "value": 0.17391304347826086},
            {"name": "莫迪", "value": 0.17391304347826086},
            {"name": "伙伴关系", "value": 0.17391304347826086},
            {"name": "一个", "value": 0.17391304347826086},
            {"name": "人民", "value": 0.17391304347826086},
            {"name": "博士", "value": 0.17391304347826086},
            {"name": "国家", "value": 0.17391304347826086},
            {"name": "今天", "value": 0.17391304347826086},
            {"name": "感谢", "value": 0.17391304347826086},
            {"name": "感谢您", "value": 0.17391304347826086},
            {"name": "再生能源", "value": 0.17391304347826086},
            {"name": "祝贺", "value": 0.17391304347826086},
            {"name": "总理", "value": 0.13043478260869565},
            {"name": "这是", "value": 0.13043478260869565},
            {"name": "非凡", "value": 0.13043478260869565},
            {"name": "受到", "value": 0.13043478260869565},
            {"name": "问题", "value": 0.13043478260869565},
            {"name": "访问", "value": 0.13043478260869565},
            {"name": "全球", "value": 0.13043478260869565},
            {"name": "现在", "value": 0.13043478260869565},
            {"name": "最大", "value": 0.13043478260869565},
            {"name": "ncbn", "value": 0.13043478260869565},
            {"name": "宗教", "value": 0.13043478260869565},
            {"name": "教徒", "value": 0.13043478260869565},
            {"name": "看到", "value": 0.13043478260869565},
            {"name": "PM", "value": 0.13043478260869565},
            {"name": "正式", "value": 0.13043478260869565},
            {"name": "发展", "value": 0.13043478260869565},
            {"name": "领导", "value": 0.13043478260869565},
            {"name": "努力", "value": 0.13043478260869565}
          ],
        },
        {
          id: '4',
          name:'@NASA',
          value:'@NASA',
          tweets:[
            '我们有一支专家团队，致力于确保我们从其他星球带回的材料不会污染地球——反之亦然。',
            '那就是我们的 @NASAPersevere 探测车！它通过将机器人臂末端摄像头的图像结合在一起来拍摄‘自拍’。',
            '想了解我们火星样本返回计划的最新进展，该计划旨在将科学挑选的样本带回地球，请在1月7日星期二，东部时间下午1点（协调世界时1800）收看 @SenBillNelson 和 @NASAScienceAA 提供的详细信息',
            '我们都在这颗星球上一起开始绕太阳的新轨道。祝愿2025年充满新的机遇、发现和喜悦时刻！来自NASA全体成员的新年祝福！',
            '两名太空行走者正在安装一个实验包，该实验包旨在监测天体X射线源和新的电连接面板。他们还在移除空间站外部的几个实验，并重新安置@ESA 机器人臂的控制面板。',
          ],
          emotion: [0, 0, 0, 16, 0, 4],
          wordCloudData: [
            {"name": "NASA", "value": 1},
            {"name": "任务", "value": 0.4418604651162791},
            {"name": "发射", "value": 0.3720930232558139},
            {"name": "我们", "value": 0.3023255813953488},
            {"name": "宇航员", "value": 0.2790697674418605},
            {"name": "美国国家航空航天局", "value": 0.2790697674418605},
            {"name": "SpaceX", "value": 0.23255813953488372},
            {"name": "火星", "value": 0.186046511627907},
            {"name": "太空", "value": 0.1627906976744186},
            {"name": "今天", "value": 0.1627906976744186},
            {"name": "视频", "value": 0.13953488372093023},
            {"name": "Space Station", "value": 0.13953488372093023},
            {"name": "前往", "value": 0.11627906976744186},
            {"name": "月球", "value": 0.11627906976744186},
            {"name": "这是", "value": 0.11627906976744186},
            {"name": "阿尔及利亚", "value": 0.11627906976744186},
            {"name": "这些", "value": 0.11627906976744186},
            {"name": "表面", "value": 0.11627906976744186},
            {"name": "太阳", "value": 0.11627906976744186},
            {"name": "佛罗里达州", "value": 0.11627906976744186},
            {"name": "猎鹰 重型", "value": 0.11627906976744186},
            {"name": "重型 火箭", "value": 0.11627906976744186},
            {"name": "nasa gov", "value": 0.11627906976744186},
            {"name": "正在", "value": 0.09302325581395348},
            {"name": "成功", "value": 0.09302325581395348},
            {"name": "一个", "value": 0.09302325581395348},
            {"name": "探索", "value": 0.09302325581395348},
            {"name": "猎鹰", "value": 0.09302325581395348},
            {"name": "重要", "value": 0.09302325581395348},
            {"name": "欧罗巴", "value": 0.09302325581395348},
            {"name": "团队", "value": 0.09302325581395348},
            {"name": "之前", "value": 0.09302325581395348},
            {"name": "HARRIS", "value": 0.09302325581395348},
            {"name": "看到", "value": 0.09302325581395348},
            {"name": "中国共产党", "value": 0.09302325581395348},
            {"name": "凯利", "value": 0.09302325581395348},
            {"name": "Europa Clipper", "value": 0.09302325581395348},
            {"name": "39A 发射台", "value": 0.09302325581395348},
            {"name": "Crew", "value": 0.06976744186046512},
            {"name": "完成", "value": 0.06976744186046512},
            {"name": "合作", "value": 0.06976744186046512},
            {"name": "网络", "value": 0.06976744186046512},
            {"name": "开始", "value": 0.06976744186046512},
            {"name": "期待", "value": 0.06976744186046512},
            {"name": "发现", "value": 0.06976744186046512},
            {"name": "他们", "value": 0.06976744186046512},
            {"name": "美国", "value": 0.06976744186046512},
            {"name": "佛罗里达州", "value": 0.06976744186046512},
            {"name": "首次", "value": 0.06976744186046512}
          ],
        },
        {
          id: '5',
          name:'@BillGates',
          value:'@BillGates',
          tweets:[
            '祝贺特朗普总统和副总统当选人范斯。美国在通过创新和创造力改善美国及全球人民的生活时最强大。我希望我们现在能够共同努力，为每个人创造一个更加光明的未来。',
            '感谢 @AbiyAhmedAli 在我访问埃塞俄比亚期间的热情欢迎。我们的深入讨论让我对埃塞俄比亚的发展进展深受启发，我也很高兴有机会继续支持我们的合作伙伴，跟踪并加速在卫生、农业方面的进展。很高兴再次欢迎 @BillGates 来到 #埃塞俄比亚 #Ethiopia。始终感谢比尔与梅琳达·盖茨基金会 @gatesfoundation 在农业、健康和金融包容性方面对我们工作的巨大支持。今天我们访问了小麦集群生产力和家禽项目。',
            '艾丹致力于消除脊髓灰质炎的工作挽救了无数生命，并影响了更多人。他的乐观精神和致力于帮助他人的承诺使他成为这一领域的领袖，也成为了每一个认识他的人伟大的朋友。在这个困难时刻，我向他的家人和亲人表示最深切的哀悼。我为失去这位非常亲爱的朋友和同事——世界卫生组织脊髓灰质炎根除计划主任艾丹·奥莱里（Aidan O\'Leary）感到心碎和悲痛。',
            '感谢印度专家的卓越工作，一项关于即时袋鼠母亲护理（IKMC）方法的研究揭示了关于拯救新生儿生命的一些惊人发现。这种创新有潜力在全球范围内拯救无数生命。@narendramodi @JPNadda感谢这些杰出专家的努力，一项新的研究证明，袋鼠母亲护理现在可以在出生后立即用于不稳定、早产和低出生体重的婴儿，并有可能每年拯救额外的15万名新生儿。',
            '我本不打算在我的夏季书单中深入探讨服务这个话题——但它今天依然像以往一样具有相关性。希望这些推荐能激励你阅读或观看其中之一。',
          ],
          emotion: [3, 0, 0, 12, 1, 4],
          wordCloudData: [
            {"name": "BillGates", "value": 1},
            {"name": "我们", "value": 0.4090909090909091},
            {"name": "比尔 盖茨", "value": 0.2272727272727273},
            {"name": "一个", "value": 0.18181818181818182},
            {"name": "他们", "value": 0.18181818181818182},
            {"name": "世界卫生组织", "value": 0.15909090909090908},
            {"name": "讨论", "value": 0.13636363636363635},
            {"name": "没有", "value": 0.13636363636363635},
            {"name": "技术", "value": 0.13636363636363635},
            {"name": "进行", "value": 0.11363636363636363},
            {"name": "印度", "value": 0.11363636363636363},
            {"name": "问题", "value": 0.11363636363636363},
            {"name": "需要", "value": 0.11363636363636363},
            {"name": "人工智能", "value": 0.11363636363636363},
            {"name": "人们", "value": 0.11363636363636363},
            {"name": "爱泼斯坦", "value": 0.11363636363636363},
            {"name": "知道", "value": 0.11363636363636363},
            {"name": "为什么", "value": 0.11363636363636363},
            {"name": "开始", "value": 0.11363636363636363},
            {"name": "一次", "value": 0.09090909090909091},
            {"name": "如何", "value": 0.09090909090909091},
            {"name": "因为", "value": 0.09090909090909091},
            {"name": "现在", "value": 0.09090909090909091},
            {"name": "公司", "value": 0.09090909090909091},
            {"name": "视频", "value": 0.09090909090909091},
            {"name": "科学家", "value": 0.09090909090909091},
            {"name": "树木", "value": 0.09090909090909091},
            {"name": "这个", "value": 0.09090909090909091},
            {"name": "特朗普", "value": 0.09090909090909091},
            {"name": "退出", "value": 0.09090909090909091},
            {"name": "计划", "value": 0.09090909090909091},
            {"name": "方式", "value": 0.09090909090909091},
            {"name": "这样", "value": 0.09090909090909091},
            {"name": "愿景", "value": 0.06818181818181818},
            {"name": "通过", "value": 0.06818181818181818},
            {"name": "疫苗", "value": 0.06818181818181818},
            {"name": "气候", "value": 0.06818181818181818},
            {"name": "绿色", "value": 0.06818181818181818},
            {"name": "只是", "value": 0.06818181818181818},
            {"name": "应该", "value": 0.06818181818181818},
            {"name": "为了", "value": 0.06818181818181818},
            {"name": "一种", "value": 0.06818181818181818},
            {"name": "孩子", "value": 0.06818181818181818},
            {"name": "自己", "value": 0.06818181818181818},
            {"name": "完全", "value": 0.06818181818181818},
            {"name": "谈论", "value": 0.06818181818181818},
            {"name": "证明", "value": 0.06818181818181818},
            {"name": "结束", "value": 0.06818181818181818},
            {"name": "植物", "value": 0.06818181818181818},
            {"name": "所以", "value": 0.06818181818181818}
          ],
        },
      ],
      wordCloudData:[],
      //anger、disgust、fear、happiness、sadness、surprise
      charaEmotionPieData:[],
      tweets:[],
    }
  },
  mounted() {
    this.getData();
    this.startScroll();
  },
  methods: {
    getData() {
      this.charaEmotionPieData = this.dataset.filter(item => item.value === this.selectedChara)[0].emotion;
      this.tweets = this.dataset.filter(item => item.value === this.selectedChara)[0].tweets; // 获取推文
      this.wordCloudData = this.dataset.filter(item => item.value === this.selectedChara)[0].wordCloudData; // 获取词云数据
    },
    changeEvent(){
      this.getData(this.selectedEvent)
    },
  }
}
</script>

<style scoped>
.out_box{
  background-image: url("@/assets/Images/bk.png");
  color: #00ffff;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;

  width: 100vw;
  height: 100vh;
}

h2 {
  margin: 0;
  font-size: 18px;
  text-align: center;
  padding: 10px;
}

.dv_content{
  padding: 15px;
}

/deep/.dv_content .border-box-content{
  display: flex;
  flex-wrap: wrap;

}
.trans_button{
  background-color: transparent;
  color: #00ffff;
  border: 1px solid #00ffff;
  border-radius: 5px;
  padding: 5px 10px;
  margin-right: 10px;
  cursor: pointer;
  font-size: 20px;
}
.trans_button:hover{
  background-color: #00ffff;
  color: #0d0d0d;
}

.tweet-list {
  animation: scroll 15s linear infinite; /* 设置动画效果 */
  list-style: none;
  padding: 0;
  margin: 0;
}

.tweet-list li {
  padding: 10px 0; /* 每条推文的内边距 */
}

/* 滚动动画 */
@keyframes scroll {
  0% {
    transform: translateY(30%);
  }
  100% {
    transform: translateY(-70%);
  }
}
</style>
