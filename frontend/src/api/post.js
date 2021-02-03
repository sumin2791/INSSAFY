import http from './http';

export function getPostList(board_id){
  return http.get('/post/getPostList',{
    params:{
      board_id:board_id
    }
  })
}

export function create(params){
  return http.post('/post/create',params)
}

export function getPost(params){
  return http.get('/post/getPostById', {
    params:params
  })
}
