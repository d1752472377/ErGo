import request from '@/utils/request'

export function getListByPage(params){
  return request({
    url: '/comment/getListByPage',
    method: 'get',
    params:params
  })
}
export function deleteCommentById(id){
  return request({
    url: '/comment/deleteCommentById',
    method: 'get',
    params:{
      id
    }
  })
}
export function getCommentByArticleId(params){
  return request({
    url: '/comment/getList',
    method: 'get',
    params
  })
}


