FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /wordtalk-pay-platform

WORKDIR /wordtalk-pay-platform

EXPOSE 5010

ADD ./target/wordtalk-pay-platform.jar ./

CMD sleep 180;java $JAVA_OPTS -jar wordtalk-pay-platform.jar
