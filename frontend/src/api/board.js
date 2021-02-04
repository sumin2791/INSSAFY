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
export function login(email, password) {
  return http.post('/account/confirm/login', {
    user_email: email,
    user_password: password,
  });
}

//member 객체로 매개변수 받음
export function join(member) {
  return http.post('/account/confirm/join', member);
}

// export function duplicate(email) {
//   return http.post('/account/confirm/emailCheck/',{
//     user_email: email,
//   });
// }

export function duplicate(email) {
  return http.post(`/account/confirm/emailCheck/${email}`);
}

export function modify(email, password) {
  return http.post('/account/modify', {
    email: email,
    password: password,
  });
}

