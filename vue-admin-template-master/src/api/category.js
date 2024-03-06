import request from '@/utils/request'

export function getCategoryList(params){
  return request({
    url:'/category/list',
    method: 'get',
    params:params
  })
}
export function deleteCategoryById(id){
  return request({
    url:'/category/delete',
    method: 'Delete',
    params:{
      id
    }
  })
}

export function addCategory(form){
  return request({
    url:'/category/add',
    method: 'post',
    data: {
			...form
		}
  })
}
export function updateCategory(form){
  return request({
    url:'/category/update',
    method: 'PUT',
    data: {
			...form
		}
  })
}
