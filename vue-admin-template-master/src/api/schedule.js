import request from '@/utils/request'

export function getJobList(queryInfo) {
  return request({
    url: '/schedule/jobs',
    method: 'GET',
    params: {
      ...queryInfo
    }
  })
}

export function updateJobStatus(jobId, status) {
  return request({
    url: '/schedule/job/status',
    method: 'PUT',
    params: {
      jobId,
      status
    }
  })
}

export function runJobOnce(jobId) {
  return request({
    url: '/schedule/job/run',
    method: 'POST',
    params: {
      jobId
    }
  })
}

export function deleteJobById(jobId) {
  return request({
    url: '/schedule/job',
    method: 'DELETE',
    params: {
      jobId
    }
  })
}

export function addJob(job) {
  return request({
    url: '/schedule/job',
    method: 'POST',
    data: {
      ...job
    }
  })
}

export function editJob(job) {
  return request({
    url: '/schedule/job',
    method: 'PUT',
    data: {
      ...job
    }
  })
}

export function getJobLogList(queryInfo) {
  return request({
    url: '/schedule/job/logs',
    method: 'GET',
    params: {
      ...queryInfo
    }
  })
}

export function deleteJobLogByLogId(logId) {
  return request({
    url: '/schedule/job/log',
    method: 'DELETE',
    params: {
      logId
    }
  })
}
