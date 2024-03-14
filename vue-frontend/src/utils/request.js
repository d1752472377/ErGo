import axios from "axios";
import store from '../store'
import {getToken,getAes,} from '@/utils/auth'

const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 60*5*1000,
})
// 添加请求拦截器
instance.interceptors.request.use(function (config) {
  if (store.getters.token) {
    //如果有toekn才携带请求头中的token到后端
    config.headers['x-access-token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
}
// config.headers['Content-type'] = "application/x-www-form-urlencoded"
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response;
  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  });

  export default instance