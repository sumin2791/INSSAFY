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

// 나의 스터디 그룹 리스트 가져오기
export function getMyGroupList(login_id){
  return http.get('/study/getStudyList',{
    params:{
      login_id
    }
  })
}

// 학습 공유 내 wordcloud 가져오기
export function getWordCloud(){
  return http.get('/study/wordCloud')
}