#!/bin/bash

IS_GREEN=$(docker ps | grep koing-green) # 현재 실행중인 App이 blue인지 확인합니다.
DEFAULT_CONF=" /etc/nginx/nginx.conf"
DOCKER_APP_NAME=koing

if [ -z $IS_GREEN  ];then # blue라면

  echo "### BLUE => GREEN ###"

#  echo "1. get green image"
#  docker-compose pull green # green으로 이미지를 내려받습니다.
#
#  echo "2. green container up"
#  docker-compose up -d green # green 컨테이너 실행

#  echo "1. get green image"
#  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml pull green
#
#  echo "2. green container up"
#  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d green

  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml up -d --build

  while [ 1 = 1 ]; do
  echo "3. green health check..."
  sleep 3

  REQUEST=$(curl http://3.34.182.215:8080) # green으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
            echo "health check success"
            break ;
            fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.green.conf /etc/nginx/nginx.conf
  sudo nginx -s rel

  echo "5. blue container down"
#  docker-compose stop blue
  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml down
  sudo docker image prune -af

else
  echo "### GREEN => BLUE ###"

#  echo "1. get blue image"
#  docker-compose pull blue
#
#  echo "2. blue container up"
#  docker-compose up -d blue

#  echo "1. get green image"
#  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.blue.yml pull blue
#
#  echo "2. green container up"
#  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.blue.yml up -d blue

  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose.blue.yml up -d --build

  while [ 1 = 1 ]; do
    echo "3. blue health check..."
    sleep 3
    REQUEST=$(curl http://3.34.182.215:8081) # blue로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break ;
    fi
  done;

  echo "4. reload nginx"
  sudo cp /etc/nginx/nginx.blue.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. green container down"
#  docker-compose stop green
  sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose.green.yml down
  sudo docker image prune -af
fi