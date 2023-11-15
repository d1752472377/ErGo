const TokenKey = 'loginToken'

const UserInfo = 'userInfo'
const aesKey = 'key'
import VueCookie from 'js-cookie'

//调整为设置token 24小时后过期
const inFifteenMinutes = new Date(new Date().getTime() + 24 * 60 * 60 * 1000)
    //获取token
export function getToken() {
    return VueCookie.get(TokenKey)
}

//设置token
export function setToken(token) {
    return VueCookie.set(TokenKey, token, { expires: inFifteenMinutes })
}

//删除token
export function removeToken() {
    return VueCookie.remove(TokenKey)
}

//获取当前登录用户信息
export function getUserInfo() {
    return VueCookie.get(UserInfo)
}
//设置当前登录用户信息
export function setUserInfo(userInfo) {
    const str = JSON.stringify (userInfo)
    //const a = "{\"id\":8,\"userName\":\"爆裂的橘子\",\"email\":null,\"phone\":\"13154101341\",\"status\":false,\"photo\":\"https://cdn.tobebetterjavaer.com/paicoding/avatar/0042.png\",\"userRole\":1}"
    console.log('是字符串吗'+str)
    return VueCookie.set(UserInfo, str, { expires: inFifteenMinutes })
}

//删除当前登录用户信息
export function removeUserInfo() {
    return VueCookie.remove(UserInfo)
}
//设置aeskey
export function setAesKey(value) {
    return VueCookie.set(aesKey, value)
}
//获取aeskey
export function getAes() {
    return VueCookie.get(aesKey)
}