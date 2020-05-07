import Axios from 'axios';
import isShowLoading from './loading';

const baseURL = "http://127.0.0.1";
/**
 * 请求初始化
 */
let instance = Axios.create({
  baseURL,
  timeout: 10000 //请求超时
});

/**
 * 请求拦截器
 */
instance.interceptors.request.use(
  config => {
    config.header['Content-Type'] = 'application/x-www-form-urlencoded';
    return config;
  },
  error => {
    console.error(error);
    return Promise.reject(error);
  }
)
/**
 * 相应拦截器
 */
instance.interceptors.response.use(
  res => {
    /* 统一处理状态码 */
    isShowLoading(false);
    return res;
  },
  error => {
    isShowLoading(false);
    return Promise.reject(error);
  }
)

class Ajax {
  static async get(url, params, isShow = false) {
    isShowLoading(isShow);
    return await instance.get(url, {params});
  }
  static async post(url, params,  isShow = false) {
    isShowLoading(isShow);
    return await instance.post(url, params);
  }
}

export default Ajax;