import request from '@/utils/request'
export function list(params) {
    return request({
        url: '/comment/getList',
        method: 'get',
        params
    })
}


export function add(data) {
    return request({
        url: '/web/comment/add',
        method: 'post',
        data
    })
}