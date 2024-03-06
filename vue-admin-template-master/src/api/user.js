import request from '@/utils/request'

export function login(params) {
  return request({
    url: '/login',
    method: 'post',
    params:params
  })
}

export function getInfo(token) {
  return request({
    url: '/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}
export function getUserList(params) {
  return request({
    url: '/user/list',
    method: 'get',
    params:params
  })
}

export function updateUserForStatus(data){
  return request({
		url: '/user/updateUserInfoForStatus',
		method: 'PUT',
		data
	})
}
export function updatePassword(data){
  return request({
		url: '/user/updatePassword',
		method: 'PUT',
		data
	})
}
