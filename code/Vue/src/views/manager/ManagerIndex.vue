<template>
  <div class="manager-index-container">
    <div class="iframe-container">
      <!-- 通过 iframe 加载 index2.html -->
      <iframe src="/index2.html" width="100%" height="100%" frameborder="0"></iframe>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ManagerIndex'
}
</script>

<style scoped>
.manager-index-container {
  display: flex;
  flex-direction: column;
  height: 100%;  /* 全屏显示 */
}

.iframe-container {
  flex-grow: 1;
  height: 100%;   /* 确保 iframe 填充剩余区域 */
}

iframe {
  width: 100%;
  height: 100%;
}
</style>
