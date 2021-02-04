import http from './http';

//포스트 생성
export function create(params){
  return http.post('/comment/create',params)
}


export function get(params){
  return http.p('/comment/create',{params:params})
}

