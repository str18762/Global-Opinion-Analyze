const baseURL = 'api'
const register='/api/user/register'
const login='/api/user/login'
const select_CharaEmotion_Last12Month='/api/emotion/selectAllMonth'
const select_CharaByName='/api/character/select/'
const get_Photo='/api/file/download/'
const select_AllChara='/api/character/selectAllChara'
const collect_Chara='/api/character/collectChara'
const cancelCollect_Chara='/api/character/cancelCollect'
const change_UserImage=baseURL+'/api/file/upload?dirName=users'
const change_UserInfo='/api/user/update'
const get_CollecNum='/api/user/getCollectNum'
const select_CollectChara='/api/character/selectCollectChara'
const select_Chara_Emotion='/api/emotion/selectCharaEmotion'
const select_NewsById='/api/api/news/selectById'
const select_bbcNewsById='/api/api/news/bbcNewsById'
const select_BBCNewsByRelativity='/api/api/news/selectBBCNewsByRelativity'
const api_explain_relation='/api/qianfan/relation'
const select_CharaByUsername='/api/character/selectByUsername/'

export default {
    baseURL,select_CharaByName,get_Photo,select_CharaEmotion_Last12Month,select_AllChara,
    collect_Chara,cancelCollect_Chara,register,login,change_UserImage,change_UserInfo,
    get_CollecNum,select_CollectChara,select_Chara_Emotion,select_NewsById,select_bbcNewsById,
    select_BBCNewsByRelativity,api_explain_relation,select_CharaByUsername

}
