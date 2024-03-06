import request from '@/utils/request'

export function getArticleList(params){
  return request({
    url: '/article/list',
    method: 'get',
    params:params
  })
}

export function getArticleListByTitle(params){
  return request({
    url: '/article/getArticleListByTitle',
    method: 'get',
    params:params
  })
}
export function updateTop(id, toppingStat){
  return request({
		url: '/article/updateTop',
		method: 'PUT',
		params: {
			id,
			toppingStat
		}
	})
}
export function updateRecommend(id, creamStat){
  return request({
		url: '/article/updateRecommend',
		method: 'PUT',
		params: {
			id,
			creamStat
		}
	})
}

export function updateVisibility(id, form){
  return request({
		url: '/article/updateRecommend',
		method: 'PUT',
		data: {
			...form
		}
	})
}
export function deleteArticleById(id){
  return request({
		url: '/article/deleteArticleById',
		method: 'delete',
		params: {
			id
		}
	})
}

export function operate(data){
  return request({
    url: '/article/operate',
    method: 'post',
    data:data
  })
}
export function getAllArticleTitle(){
  return request({
    url: '/article/getAllArticleTitle',
    method: 'get',
  })
}
