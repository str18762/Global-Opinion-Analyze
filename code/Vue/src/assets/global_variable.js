const baseURL = 'http://localhost:7001'
const register='/user/register'
const login='/user/login'
const select_CharaEmotion_Last12Month='/emotion/selectAllMonth'
const select_CharaByName='/character/select/'
const get_Photo='/file/download/'
const select_AllChara='/character/selectAllChara'
const collect_Chara='/character/collectChara'
const cancelCollect_Chara='/character/cancelCollect'
const change_UserImage=baseURL+'/file/upload?dirName=users'
const change_UserInfo='/user/update'
const get_CollecNum='/user/getCollectNum'
const select_CollectChara='/character/selectCollectChara'
const select_Chara_Emotion='/emotion/selectCharaEmotion'
const select_NewsById='/api/news/selectById'
const select_bbcNewsById='/api/news/bbcNewsById'
const select_allBBCNews='/api/news/getAllBBCNews'

export default {
    baseURL,select_CharaByName,get_Photo,select_CharaEmotion_Last12Month,select_AllChara,
    collect_Chara,cancelCollect_Chara,register,login,change_UserImage,change_UserInfo,
    get_CollecNum,select_CollectChara,select_Chara_Emotion,select_NewsById,select_bbcNewsById,
    select_allBBCNews,

}
