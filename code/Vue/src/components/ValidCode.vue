<template>
  <canvas ref="canvas" @click="draw" width="140" height="40" style="cursor: pointer;"></canvas>
</template>
<script>
export default {
  data() {
    return {
      codes: [],
      ctx: "",
      colors: ["red", "yellow", "blue", "green", "pink", "black"],
      code_Len: 4
    };
  },
  mounted() {
    this.draw();
  },
  computed: {
    codeString() {
      let result = "";
      for (let i = 0; i < this.codes.length; i++) {
        result += this.codes[i];
      }
      return result.toUpperCase();
    }
  },
  watch: {
    codeString: function(newValue) {
      this.$emit("change", newValue);
    }
  },
  methods: {
    generateRandom() {
      this.codes = [];
      const chars = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789";
      const charsArr = chars.split("");

      for (let i = 0; i < this.code_Len; i++) {
        const num = Math.floor(Math.random() * charsArr.length);
        this.codes.push(charsArr[num]);
      }
    },
    draw() {
      this.generateRandom();
      this.drawText();
    },
    drawLine() {
      const lineNumber = 3; // 线条条数
      const lineX = 140;
      const lineY = 30; // 最大线条坐标
      for (let i = 0; i < lineNumber; i++) {
        this.ctx.strokeStyle = this.colors[Math.floor(Math.random() * 5)];
        this.ctx.beginPath();
        this.ctx.moveTo(
            Math.floor(Math.random() * lineX),
            Math.floor(Math.random() * lineY)
        );
        this.ctx.lineTo(
            Math.floor(Math.random() * lineX),
            Math.floor(Math.random() * lineY)
        );
        this.ctx.stroke();
      }
    },
    drawText() {
      const canvas = this.$refs["canvas"];
      this.ctx = canvas.getContext("2d");

      this.ctx.fillStyle = "#BFEFFF";
      this.ctx.fillRect(0, 0, 140, 40);
      this.ctx.font = "20px Verdana";

      let x = 15;

      for (let i = 0; i < this.code_Len; i++) {
        this.ctx.fillStyle = this.colors[Math.floor(Math.random() * 5)];
        this.ctx.fillText(this.codes[i], x, 25);
        x = x + 30;
      }

      this.drawLine();
    }
  }
};
</script>