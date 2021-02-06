import http from './http';

//댓글 생성
export function comment_create(params){
  return http.post('/comment/create',params)
}

//댓글 수정
export function comment_modify(params){
  return http.put('/comment/modify',params)
}

//댓글 삭제
export function comment_delete(params) {
  return http.delete('/comment/delete',{
    params:{
      comment_id:params.comment_id,
      login_id:params.login_id,
    }
  })
}


export function get(params){
  return http.p('/comment/create',{params:params})
}

