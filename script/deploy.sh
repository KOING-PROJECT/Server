#!/bin/bash

IS_GREEN=$(docker ps | grep green) # 현재 실행중인 App이 blue인지 확인합니다.
DEFAULT_CONF=" /etc/nginx/nginx.conf"

if [ -z $IS_GREEN  ];then # blue라면

  echo "### BLUE => GREEN ###"

#  echo "1. get green image"
#  docker-compose pull green # green으로 이미지를 내려받습니다.
#
#  echo "2. green container up"
#  docker-compose up -d green # green 컨테이너 실행

  echo "1. make green container and run"
  sudo docker run -d --name koing-green -p 8080:8080 winners192/koing

  while [ 1 = 1 ]; do
  echo "2. green health check..."
  sleep 3

  REQUEST=$(curl http://13.209.161.166:8080) # green으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
            echo "health check success"
            break ;
            fi
  done;

  echo "3. reload nginx"
  sudo cp /etc/nginx/nginx.green.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "4. stop blue container"
#  docker-compose stop blue
  sudo docker stop koing-blue

  echo "5. remove blue container"
  sudo docker rm koing-blue

  echo

else
  echo "### GREEN => BLUE ###"

#  echo "1. get blue image"
#  docker-compose pull blue
#
#  echo "2. blue container up"
#  docker-compose up -d blue

  echo "1. make blue container and run"
  sudo docker run -d --name koing-blue -p 8081:8080 winners192/koing

  while [ 1 = 1 ]; do
    echo "2. blue health check..."
    sleep 3
    REQUEST=$(curl http://13.209.161.166:8081) # blue로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break ;
    fi
  done;

  echo "3. reload nginx"
  sudo cp /etc/nginx/nginx.blue.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "4. stop green container"
#  docker-compose stop blue
  sudo docker stop koing-green

  echo "5. remove green container"
  sudo docker rm koing-green
fi