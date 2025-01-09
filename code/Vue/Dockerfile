# This Dockerfile is used to build a Docker image for the Vue.js frontend application.
# 从nginx镜像构建
FROM nginx

# 将前端代码复制到nginx的html目录下
COPY dist/ /usr/share/nginx/html/
# 将nginx的配置文件复制到nginx的配置目录下
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 暴露80端口
EXPOSE 80