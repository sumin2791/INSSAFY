import http from './http';

export function board_create(board){
  return http.post('/board/create',board)
}

export function get_boards(params){
  const sort = params.sort
  const page = params.page
  const size = params.size
  return http.get('/board/getBoards',{
    params:{
      sort:sort,
      page:page,
      size:size
    }
  })
}

export function board_detail(board_id){
  return http.get('/board/detail', {
    params:{
      board_id:board_id
    }
  })
}

export function subscribe(params) {
  return http.post('/board/subscribe',params)
}

export function board_modify(body,login_id) {
  return http.put('/board/modify',body,{params:{login_id:login_id}})
}
// ==============