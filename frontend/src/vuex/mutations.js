export default {
  //setting searchState
  setSearchKeyword(state, keyword) {
    state.searchState.keyword = keyword;
  },
  setSearchFilters(state, filters) {
    state.searchState.filters = filters;
  },
  // setSearchVisible(state, visible) {
  //   state.searchState.visible = visible;
  // },
  setSearchState(state, payload) {
    state.searchState = payload;
  },

  //spiner 토글
  setSpinnerTogle(state) {
    state.spinnerActive = !state.spinnerActive;
  },
  setSpinnerMessage(state, message) {
    state.spinnerMessage = message;
  },

  //Toast
  setToastTogle(state) {
    state.toastActive = !state.toastActive;
  },
  setToastType(state, typeName) {
    if (typeName == 'search') {
      state.toastType.search = true;
      state.toastType.myinfo = false;
      state.toastType.email = false;
    } else if (typeName == 'myinfo') {
      state.toastType.search = false;
      state.toastType.myinfo = true;
      state.toastType.email = false;
    } else if (typeName == 'email') {
      state.toastType.search = false;
      state.toastType.myinfo = false;
      state.toastType.email = true;
    }
  },

  //comment
  CREATE_COMMENT(state, commentItem) {
    state.comments.push(commentItem);
  },

  //post
  CREATE_POST(state, postItem) {
    state.posts.push(postItem);
  },
};