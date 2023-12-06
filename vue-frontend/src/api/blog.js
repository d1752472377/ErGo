import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/article/web/list',
        method: 'post',
        data
    })
}
export function queryArticleSearchKey(query) {
    return request({
        url: '/article/queryArticleSearchKey',
        method: 'get',
        params: {
			query
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
