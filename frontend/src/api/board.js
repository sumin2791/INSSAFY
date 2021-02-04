import http from './http';

export function board_create(board){
  return http.post('/board/create',board)
}

export function get_boards(sort){
  return http.get('/board/getBoards',{
    params:{
      sort:sort
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
// ==============