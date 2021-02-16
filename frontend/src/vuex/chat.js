export default {
  namespaced: true,
  state: {
    // 현재 선택된 채팅방
    selectedChatRoom: '',
    // 현재 선택된 채팅방 아이디
    selectedId: '',
    // 현재 선택된 채팅방 상대 닉네임
    selectedNickname: '',
    // 가져올 채팅 메세지들
    selectedMessages: [],
    // 채팅방 출입 탐지 flag
    isInChatRoom: false,

  },
  getters: {

  },
  mutations: {
    // response에서 받아오는 부분
    IS_SELECTED(state, data) {
      state.selectedChatRoom = data.roomId
      state.selectedNickname = data.opp_nickName
      state.selectedId = data.opp_id
      state.isInChatRoom = true
    },
    // 채팅방 선택 안되면 초기화
    IS_NOT_SELECTED(state) {
      state.selectedChatRoom = ''
      state.selectedNickname = ''
      state.selectedId = ''
      state.isInChatRoom = false
      state.selectedMessages = []
    },
    // 메세지 가져와서 저장
    GET_MESSAGES(state, msgList) {
      state.selectedMessages = msgList
    },
  },
  actions: {
    isSelected({commit}, chatList) {
      commit('IS_SELECTED', chatList)
    },
    isNotSelected({commit}) {
      commit('IS_NOT_SELECTED')
    },
    // 채팅방 내 메세지들 가져오는 부분
    getMessages({commit}, msgList) {
      commit('GET_MESSAGES', msgList)
    },
  },
}