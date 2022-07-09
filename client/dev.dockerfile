FROM node:lts-alpine
WORKDIR /client
RUN npm install --location=global pnpm
# pnpm fetch does require only lockfile
COPY pnpm-lock.yaml .
RUN pnpm fetch 
COPY . /client
RUN pnpm install --offline
# EXPOSE 3000
CMD ["pnpm", "start"]