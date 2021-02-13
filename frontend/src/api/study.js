import http from './http';

// 전체 그룹 리스트 가져오기
export function getAllGroupList(page,size){
  return http.get('/study/getAllList',{
    params:{
      page:page,
      size:size
    }
  })
}

export function getMyGroupList(login_id){
  return http.get('/study/getStudyList',{
    params:{
      login_id
    }
  })
}