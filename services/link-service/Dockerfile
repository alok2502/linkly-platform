FROM node:20-alpine
WORKDIR /app
COPY package.* .
RUN npm install 
COPY . .
USER node
EXPOSE 3000
CMD ["node", "server.js"]
