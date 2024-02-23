import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/article/web/list',
        method: 'post',
        data
    })
}
export function getNewBlog (params) {
    return request({
      url: '/article/list',
      method: 'get',
      params
    })
  }
export function queryArticleSearchKey(key) {
    return request({
        url: '/article/queryArticleSearchKey',
        method: 'get',
        params: {
			key
		}
    })
}

export function getArticleById(id) {
    return request({
        url: '/web/article/by/' + id,
        method: 'get',
    })
}
export function add(data) {
    return request({
        url: '/web/article/add',
        method: 'post',
        data
    })
}
export function browse(id) {
    return request({
        url: '/web/article/browse/' + id,
        method: 'get',
    })
}

export function like(id) {
    return request({
        url: '/web/article/like/' + id,
        method: 'get',
    })
}

export function update(data, id) {
    return request({
        url: '/web/article/update/' + id,
        method: 'put',
        data
    })
}
export function getArticleInfo(params) {
    return request({
        url: '/article/getArticleInfo',
        method: 'get',
        params
    })
}
export function getUserWrite(params){
    return request({
        url: '/article/getUserWrite',
        method: 'get',
        params
    })
}
export function getUserFootForRead(params){
    return request({
        url: '/foot/getUserFootForRead',
        method: 'get',
        params
    })
}
export function getUserFootForCollection(params){
    return request({
        url: '/foot/collection',
        method: 'get',
        params
    })
}


