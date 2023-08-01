#!/bin/bash

REPOSITORY=/home/ec2-user/koing_server
PROJECT_NAME=KOING_server-0.0.1-SNAPSHOT-boot

echo "> Build 파일 복사"
cp $REPOSITORY/build/libs/$PROJECT_NAME.jar $REPOSITORY/

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

echo "> $JAR_NAME 실행"
nohup java -jar -DAWS_CREDENTIAL_SECRET_KEY=V3HIth8JOMCpL0viJ+ISBxQ4Rb0QXSx2YQPDo/tJ -DAWS_CREDENTIAL_ACCESS_KEY=AKIAQ4RPXCPK4H6FP5E2 -DEMAIL_PROPERTIES_EMAIL=kkoing.official@gmail.com -DEMAIL_PROPERTIES_PASSWORD=zcrhzahgttuoibld -DIMPORT_IMP_KEY=0428352841086548 -DIMPORT_IMP_SECRET=1R6P0fX7wTUcTDavkxor6ISgvKTpsd3eUc98TWhdaxdUxTs7JbUhZrM7YfpdC5tW9iEAmXYLt7Ugp5pn -DNAVER_CLOUD_PLATFORM_SERVICE_ID=ncp:sms:kr:302133616007:koing -DNAVER_CLOUD_PLATFORM_SECRET_KEY=l3D5SYkoLoz5b6YAcC7kPPuQs6qNxWqxxXkCQnhb -DNAVER_CLOUD_PLATFORM_ACCESS_KEY=Ta3qBszLp3v3OKJCsN9c -DNAVER_CLOUD_PLATFORM_FROM_PHONE_NUMBER=01071931623 -DJWT_SECRET_KEY=se#ko*INg@cREtKEYTKOhIIIINiNNGsVERRRRSER! $JAR_NAME > /home/ec2-user/koing_server/nohup.out 2>&1 &