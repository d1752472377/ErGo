
import request from '@/utils/request'
export function getHotTag(params) {
    return request({
        url: '/tag/index/getTagList',
        method: 'get',
        params
    })
}