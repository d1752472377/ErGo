import request from '@/utils/request'
export function list(data) {
    return request({
        url: '/article/web/list',
        method: 'post',
        data
    })
}