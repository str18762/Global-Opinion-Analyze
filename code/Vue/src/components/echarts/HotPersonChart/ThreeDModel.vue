<template>
  <div class="scene-container">
    <div ref="3dContainer" class="container3d"></div>
  </div>
</template>

<script>
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";

export default {
  name: "ThreeDModel",
  props: {
    modelUrl: {
      type: String,
      required: true,
      default: "",
    }
  },
  data() {
    return {
      scene: null,
      camera: null,
      renderer: null,
      model: null,
      isDragging: false,
      previousMousePosition: {
        x: 0,
        y: 0,
      },
    }
  },
  methods: {
    initThree() {
      // 创建场景
      this.scene = new THREE.Scene();

      // 创建摄像机
      this.camera = new THREE.PerspectiveCamera(
          75,
          this.$refs["3dContainer"].clientWidth / this.$refs["3dContainer"].clientHeight,
          0.1,
          1000
      );
      this.camera.position.set(5, 5, 10);

      // 创建渲染器
      this.renderer = new THREE.WebGLRenderer();
      this.renderer.setSize(this.$refs["3dContainer"].clientWidth, this.$refs["3dContainer"].clientHeight);
      this.$refs["3dContainer"].appendChild(this.renderer.domElement);

      // 添加环境光
      const ambientLight = new THREE.AmbientLight(0xffffff, 1);
      this.scene.add(ambientLight);

      // 添加定向光
      const directionalLight = new THREE.DirectionalLight(0xffffff, 0.5);
      directionalLight.position.set(5, 10, 7.5);
      this.scene.add(directionalLight);

      // 创建 GLTFLoader 实例
      const loader = new GLTFLoader();

      // 加载第一个人物模型
      let scale = new THREE.Vector3(10, 10, 10);
      let rotation = new THREE.Vector3(0, 0, 0);
      let position = new THREE.Vector3(5, 5, -5);
      if (this.modelUrl === "elon_musk") {
        scale = new THREE.Vector3(20, 20, 20);
        rotation = new THREE.Vector3(0, -Math.PI/2, 0);
      } else if (this.modelUrl === "modi_ji_face_3d_model") {
        scale = new THREE.Vector3(60, 60, 60);
        rotation = new THREE.Vector3(0, 0, 0);
      } else if (this.modelUrl === "barack_obama") {
        position = new THREE.Vector3(5, -2, -5);
        scale = new THREE.Vector3(40, 40, 40);
        rotation = new THREE.Vector3(0, 0, 0);
      }

      loader.load(
          "/"+this.modelUrl+"/scene.gltf",
          (gltf) => {
            this.model = gltf.scene;
            this.model.position.set(position.x, position.y, position.z); // 设置第一个人物的位置
            this.model.scale.set(scale.x, scale.y, scale.z); // 设置缩放
            this.model.rotation.set(rotation.x, rotation.y, rotation.z); // 设置旋转
            this.scene.add(this.model);
            this.animate();
          },
          undefined,
          (error) => {
            console.error("Error loading person1:", error);
          }
      );
    },
    animate() {
      requestAnimationFrame(this.animate);
      if (this.model) {
        this.renderer.render(this.scene, this.camera);
      }
    },
    onMouseDown(event) {
      this.isDragging = true;
      this.previousMousePosition = {
        x: event.clientX,
        y: event.clientY,
      };
    },
    onMouseMove(event) {
      if (!this.isDragging) return;

      const deltaMove = {
        x: event.clientX - this.previousMousePosition.x,
        y: event.clientY - this.previousMousePosition.y,
      };

      if (this.model) {
        // 只修改 Y 轴旋转（左右旋转）
        this.model.rotation.y += deltaMove.x * 0.01;
        // 注释掉 X 轴旋转（上下旋转）
        // this.model.rotation.x += deltaMove.y * 0.01;
      }

      this.previousMousePosition = {
        x: event.clientX,
        y: event.clientY,
      };
    },
    onMouseUp() {
      this.isDragging = false;
    },
  },
  mounted() {
    this.initThree();

    // 监听鼠标事件
    this.$refs["3dContainer"].addEventListener("mousedown", this.onMouseDown);
    this.$refs["3dContainer"].addEventListener("mousemove", this.onMouseMove);
    this.$refs["3dContainer"].addEventListener("mouseup", this.onMouseUp);
    this.$refs["3dContainer"].addEventListener("mouseleave", this.onMouseUp);
  },
  beforeDestroy() {
    // 移除事件监听
    this.$refs["3dContainer"].removeEventListener("mousedown", this.onMouseDown);
    this.$refs["3dContainer"].removeEventListener("mousemove", this.onMouseMove);
    this.$refs["3dContainer"].removeEventListener("mouseup", this.onMouseUp);
    this.$refs["3dContainer"].removeEventListener("mouseleave", this.onMouseUp);

    // 清理 Three.js 资源
    this.renderer.dispose();
    this.scene = null;
    this.camera = null;
    this.renderer = null;
    this.model = null;
  },

};
</script>

<style scoped>
.scene-container {
  width: 100%;
  height: 100%;
}

.container3d {
  width: 100%;
  height: 100%;
}
</style>
