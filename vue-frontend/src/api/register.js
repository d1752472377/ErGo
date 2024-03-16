import request from '@/utils/request'

export function register(data) {
    return request({
        url: '/user/register',
        method: 'post',
        data
    })
}

export function getEmailCode(params) {
    return request({
        url: '/getEmailCode',
        method: 'post',
        params
    })
}