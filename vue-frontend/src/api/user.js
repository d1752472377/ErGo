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
//点击关注用户/取消关注用户
export function followUserOrUnfollowUser(params) {
    return request({
        url: '/relation/followUserOrUnfollowUser',
        method: 'get',
        params
    })
}
//查询登录用户与文章用户之间是否关注
export function queryFollowBetweenLoggedUserAndArticleUser(params) {
    return request({
        url: '/relation/queryFollowBetweenLoggedUserAndArticleUser',
        method: 'get',
        params
    })
}


export function getDateForUser(params) {
    return request({
        url: '/getDateForUser',
        method: 'get',
        params
    })
}
export function updateUserXinxi(data) {
    return request({
        url: '/user/updateUserXinxi',
        method: 'PUT',
        data
    })
}
