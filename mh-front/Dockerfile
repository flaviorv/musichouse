FROM node:18-alpine
WORKDIR /app
COPY package.json /app/
RUN npm install
COPY public/ /app/public
COPY src/ /app/src
CMD ["npm", "start"]