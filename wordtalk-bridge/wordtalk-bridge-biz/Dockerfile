FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /wordtalk-bridge

WORKDIR /wordtalk-bridge

EXPOSE 7060

ADD ./target/wordtalk-bridge-biz.jar ./

CMD sleep 60;java $JAVA_OPTS -jar wordtalk-bridge-biz.jar