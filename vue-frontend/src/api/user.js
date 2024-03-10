import request from '@/utils/request'

//绑定
export function bind(data) {
    return request({
        url: '/auth/user/bind',
        method: 'put',
        data
    })
}
//找回密码
export function retrievePassword(data) {
    return request({
        url: '/auth/user/retrieve',
        method: 'put',
        data
    })
}

//发送验证码
export function send(data) {
    return request({
        url: '/auth/user/send',
        method: 'post',
        data
    })
}

//获取关注与粉丝数目
export function queryRelationCount(params) {
    return request({
        url: '/relation/queryRelationCount',
        method: 'get',
        params
    })
}
//获取关注与粉丝详情
export function queryRelation(params) {
    return request({
        url: '/relation/queryRelation',
        method: 'get',
        params
    })
}

//修改关注状态
export function updateRelation(params) {
    return request({
        url: '/relation/updateRelation',
        method: 'get',
        params
    })
}