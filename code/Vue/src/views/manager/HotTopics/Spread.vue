<template>
  <div>
    <button @click="openNewWindow">打开新页面</button>
  </div>
</template>

<script>
export default {
  name: "HotTopicsSpread",
  methods: {
    openNewWindow() {
      // 打开新的窗口
      const newWindow = window.open("/index3.html", "_blank", "width=800,height=600");

      // 检测新窗口是否被阻止
      if (!newWindow || newWindow.closed || typeof newWindow.closed === "undefined") {
        alert("弹出窗口被浏览器阻止，请允许弹窗功能！");
      }
    },
  },
};
</script>

<style scoped>
button {
  padding: 10px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
button:hover {
  background-color: #45a049;
}
</style>
