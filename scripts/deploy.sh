#!/bin/bash

REPOSITORY=/home/ec2-user/koing_server
PROJECT_NAME=KOING_server-0.0.1-SNAPSHOT-boot.jar

echo "> Build 파일 복사"
cp $REPOSITORY/build/libs/$PROJECT_NAME $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"
CURRENT_PID=$(pgrep -fl $PROJECT_NAME | awk '{print $1}')

echo "> 현재 구동중인 애플리케이션 pid: $CURRENT_PID"
if [ -z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar|grep jar|tail -n 1)

echo "> Jar Name: $JAR_NAME"
echo "> $JAR_NAME에 실행권한 추가"
chmod +x $JAR_NAME

cd /home/ec2-user/koing_server
echo "> $PROJECT_NAME 실행"
nohup java -jar $PROJECT_NAME.jar /dev/null 2> /dev/null < /dev/null &