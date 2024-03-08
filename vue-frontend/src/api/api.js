import request from '@/utils/request'


export function getcsdn() {
    return request({
        url: '/third/csdn',
        method: 'get',
    })
}
export function getZhihu() {
    return request({
        url: '/third/zhihu',
        method: 'get',
    })
}
export function getBaidu() {
    return request({
        url: '/third/baidu',
        method: 'get',
    })
}
export function getWeibo() {
    return request({
        url: '/third/weibo',
        method: 'get',
    })
}
export function getBili() {
    return request({
        url: '/third/bili',
        method: 'get',
    })
}
export function getToutiao() {
    return request({
        url: '/third/toutiao',
        method: 'get',
    })
}