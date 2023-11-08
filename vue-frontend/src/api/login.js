import request from '@/utils/request'

export function login(data) {
    return request({
        url: '/auth/user/login',
        method: 'post',
        data
    })
}