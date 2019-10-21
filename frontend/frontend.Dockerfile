FROM node:12-alpine

WORKDIR /opt/challenge_frontend
COPY ./ /opt/challenge_frontend
RUN npm install

EXPOSE 3000
ENTRYPOINT ["npm", "start"]