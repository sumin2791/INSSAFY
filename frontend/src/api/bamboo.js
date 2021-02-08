import http from './http';

// page,size
export function getAllList(params){
  return http.get('/bamboo/getAllList',{params:params})
}

export function create(params){
  return http.post('/bamboo/create',params)
}


export function modify(params){
  return http.put('/bamboo/modfiy',{params:params})
}

export function bamboo_delete(params){
  return http.delete('/bamboo/delete',{params:params})
}