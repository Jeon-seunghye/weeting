# 1단계: 빌드 단계 (최신 버전 사용)
FROM node:20.12.2 as build

# 작업 디렉토리 설정
WORKDIR /app

# 의존성 파일 복사
COPY package*.json ./

# 의존성 설치 전에 package-lock.json과 node_modules 제거
RUN rm -rf node_modules package-lock.json && npm cache clean --force

# 의존성 설치
RUN npm install

# 앱 소스 코드 추가
COPY . .

# # 앱 빌드
# RUN npm run build

# 2단계: 실행 단계
# FROM nginx:alpine

# Nginx
# COPY --from=build /app/dist /usr/share/nginx/html
# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]

# 포트 노출
EXPOSE 3000

# 컨테이너 시작 시 실행할 명령
# CMD ["npm", "run", "dev"]
CMD ["npm", "run", "dev"]

