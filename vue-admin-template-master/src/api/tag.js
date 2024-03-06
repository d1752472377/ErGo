import request from '@/utils/request'

export function getTagList(params){
  return request({
    url:'/tag/getTagListByPage',
    method: 'get',
    params:params
  })
}

export function updateTag(form){
  return request({
    url:'/tag/updateTagByAdmin',
    method: 'PUT',
    data: {
			...form
		}
  })
}

export function deleteTagById(id){
  return request({
    url:'/tag/deleteTagByAdmin',
    method: 'Delete',
    params:{
      id
    }
  })
}

export function addTag(form){
  return request({
    url:'/tag/addTagByAdmin',
    method: 'post',
    data: {
			...form
		}
  })
}
