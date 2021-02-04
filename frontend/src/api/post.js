import http from './http';

export function getPostList(params){
  return http.get('/post/getPostList',{params:params})
}


export function create(params){
  return http.post('/post/create',params)
}


export function getPost(params){
  return http.get('/post/getPostById', {
    params:params
  })
}


export function likePost(params){
  return http.post('/post/like',params)
}


export function scrapPost(params){
  return http.post('/post/scrap',params)
}