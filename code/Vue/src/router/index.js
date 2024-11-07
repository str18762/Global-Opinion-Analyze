import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err);
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component:() => import("../views/Manager.vue"),
    redirect:'/homePage',
    children:[
      {path: 'homePage',name:'HomePage',component:()=>import('../views/manager/HomePage.vue')},
      {path: 'influenceAnalysis',name:'InfluenceAnalysis',component:()=>import('../views/manager/InfluenceAnalysis.vue')},
      {path: 'personTracking',name:'PersonTracking',component:()=>import('../views/manager/PersonTracking.vue')},
      {path: 'correlationAnalysis',name:'CorrelationAnalysis',component:()=>import('../views/manager/CorrelationAnalysis.vue')},
      {path: 'hotPerson',name:'hotPerson',component:()=>import('../views/manager/HotPerson.vue')},
      {path: 'reportGeneration',name:'ReportGeneration',component:()=>import('../views/manager/ReportGeneration.vue')},
      {path: 'helpCenter',name:'HelpCenter',component:()=>import('../views/manager/HelpCenter.vue')},
      {path: '/personTracking/personDetail',name:'PersonDetail',component:()=>import('../views/manager/PersonDetail.vue')},
      {path: 'userInfo',name:'UserInfo',component:()=>import('../views/manager/UserInfo.vue')},
      {path: 'news',name:'News',component:()=>import('../views/manager/News.vue')},
      {path: 'news_bbc',name:'News_bbc',component:()=>import('../views/manager/News_BBC.vue')},
      {path: 'snapshot', name: 'SnapshotDetail', component: () => import('../views/manager/Snapshot.vue') },
    ]
  },
  {
    path: '/admin',name:'Admin',component:()=>import('@/views/Admin/admin.vue'),
    children: [
      {path: 'index',name:'Index',component:()=>import('../views/Admin/index.vue')},
      {path: 'adminManage',name:'AdminManage',component:()=>import('../views/Admin/AdminManager.vue')},
      {path: 'userManage',name:'UserManage',component:()=>import('../views/Admin/UserManager.vue')},
      {path: 'influencialPersonManage',name:'InfluencialPersonManage',component:()=>import('../views/Admin/InfluencialPersonManager.vue')},
      {path: 'newsManage',name:'NewsManage',component:()=>import('../views/Admin/NewsManager.vue')},
      {path: 'emotionAnalysisManage',name:'EmotionAnalysisManage',component:()=>import('../views/Admin/EmotionAnalysisManager.vue')},
      {path: 'emotionExtraAnalysisManage',name:'EmotionExtraAnalysisManage',component:()=>import('../views/Admin/EmotionExtraAnalysisManager.vue')},

    ]
  },
  {path: '/test', name: 'Test', component: () => import('../views/404.vue') },
  {path: '/hotTopics', name: 'HotTopics', component:()=>import('../views/manager/HotTopics.vue')},
  {path: '/*', name:'404',
    component:()=>import("../views/404.vue")},
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  let adminPaths=['/userControl','/adminControl']
  let user = JSON.parse(localStorage.getItem("honey-user")||'{}')

  if(user.role!="管理员"&&adminPaths.includes(to.path)){
    next('/403')
  }else{
    next()
  }
})

export default router
