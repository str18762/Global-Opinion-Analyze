<template>
  <div>
    <!-- 节点列表 -->
    <div class="node" v-for="(node, index) in nodes" :key="index" @click="showMenu(node, $event)">
      {{ node.name }}
    </div>

    <!-- 上下文菜单 -->
    <div v-if="selectedNode && menuVisible" class="context-menu" :style="{ top: menuTop + 'px', left: menuLeft + 'px' }">
      <ul>
        <li @click="editNode">编辑</li>
        <li @click="deleteNode">删除</li>
        <!-- 更多操作项... -->
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nodes: [
        {name: 'Node 1'},
        {name: 'Node 2'},
        // 其他节点...
      ],
      selectedNode: null,
      menuVisible: false,
      menuTop: 0,
      menuLeft: 0,
    };
  },
  methods: {
    showMenu(node, event) {
      this.selectedNode = node;
      this.menuVisible = true;
      this.menuTop = event.clientY;
      this.menuLeft = event.clientX;

      // 阻止浏览器默认右键菜单
      event.preventDefault();
    },
    editNode() {
      console.log('Editing node:', this.selectedNode);
      this.menuVisible = false;
      // 编辑节点逻辑...
    },
    deleteNode() {
      console.log('Deleting node:', this.selectedNode);
      this.menuVisible = false;
      // 删除节点逻辑...
    },
  }
};
</script>

<style scoped>
.node {
  cursor: pointer;
}

.context-menu {
  position: absolute;
  background-color: white;
  border: 1px solid #ccc;
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.2);
}

.context-menu ul {
  list-style-type: none;
  padding: 0;
}

.context-menu li {
  padding: 5px 10px;
  cursor: pointer;
}

.context-menu li:hover {
  background-color: #f0f0f0;
}
</style>