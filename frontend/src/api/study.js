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

// 스터디 가입 요청
export function studyRequest(user_id,board_id){
  return http.post('/study/request',{user_id,board_id})
}

// 스터디 가입 요청 리스트
export function getRequestList(board_id){
  return http.get('/study/requestList',{
    params:{
      board_id
    }
  })
}

// 스터디 가입 수락/거절
export function studyRequestProcess(user_id,board_id,option){
  return http.post('/study/requestProcess',{
    user_id,board_id,option
  })
}

// 학습 공유 내 wordcloud 가져오기
export function getWordCloud(){
  return http.get('/study/wordCloud')
}