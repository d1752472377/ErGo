import request from '@/utils/request'
export function list(params) {
    return request({
        url: '/comment/getList',
        method: 'get',
        params
    })
}


export function addComment(data) {
    return request({
        url: '/comment/addComment',
        method: 'post',
        data
    })
}