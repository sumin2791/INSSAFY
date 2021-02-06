<template>
  <div id="container-box">
    <!-- 왼쪽 메뉴 부분 -->
    <div id="left-info" class="left-info">
      <!-- Curation -->
      <div id="type" class="b-title">
        <h4>Curation</h4>
      </div>
      <!-- 해당 큐레이션 description -->
      <div id="description" class="rounded-bg real-shadow-box">
        <h4 class="b-desc">채용일정</h4>
        <p class="l-desc">
          채용일정 너만아니<br />
          채용일정 나도알자
        </p>
      </div>
      <!-- 임박한 채용보기 -->
      <div id="due-date" class="rounded-bg real-shadow-box">
        임박한 채용일정 보여줄 리스트
        <DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem /><DueDateItem />
        <DueDateItem />
        <DueDateItem />
        <DueDateItem />
      </div>
      <hr class="blank" />
    </div>
    <div id="center-post">
      <!-- 캘린더 들어가는 부분 -->
      <div id="study-calendar">
        캘린더가 들어갈 부분
      </div>

      <!-- 검색 돋보기 아이콘 -->
      <div class="search-bar">
        🔍검색창 들어갈 부분
      </div>
      <!-- 게시글 작성 -->
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <div class="create-post">
        <button>게시글 작성 버튼</button>
      </div>
      <!-- 각각의 게시글 들어갈 부분 -->
      <Post class="post-list" />
    </div>
    <hr class="blank" />
  </div>
</template>

<script>
// 스터디 홍보글 게시물
import Post from '@/components/board/Post.vue';
import DueDateItem from '@/views/curation/recruitment/DueDateItem.vue';

export default {
  name: 'Recruitment',
  components: {
    Post,
    DueDateItem,
  },
};
</script>

<style scoped>
.rounded-bg {
  border-radius: 15px;
  background-color: var(--basic-color-bg2);
}
.blank {
  visibility: hidden;
  margin: 15px;
}

#container-box {
  position: absolute;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  max-width: 1100px;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  z-index: -1;
}
#left-info {
  display: flex;
  flex-direction: column;
  flex: 1;
  margin-right: 20px;
  padding: 0 15px;
  height: 100%;
  overflow-y: scroll;
}
/* 왼쪽 메뉴바 부분 */
#type {
  margin-top: 80px;
}
/* 스터디 보드 설명 */
#description {
  width: 100%;
  margin: 10px 0 20px;
  padding: 15px;
}

/* 가운데 게시글 부분 */
#center-post {
  display: flex;
  flex-direction: column;
  align-items: center;
  flex: 2;
  height: 100%;
  overflow-y: scroll;
}
#study-calendar {
  margin-top: 123px;
  background-color: azure;
  width: 100%;
  height: 70%;
}

/* 검색창 */
.center-post .search-bar {
  align-self: flex-end;
}

/* 각각의 게시글들 */
.center-post .post-list {
  border: 2px dotted;
  margin: 2% 2%;
}

/* 모바일 웹화면 */
@media (max-width: 426px) {
  #container-box {
    flex-direction: column;
  }
  #left-info {
    width: 100%;
    height: auto;
  }
  #center-post {
    width: 100%;
    height: auto;
    overflow: visible;
  }
}
</style>
