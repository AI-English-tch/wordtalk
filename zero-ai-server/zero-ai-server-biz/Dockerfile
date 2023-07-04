FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /zero-ai-server

WORKDIR /zero-ai-server

EXPOSE 6060

ADD ./target/zero-ai-server-biz.jar ./

CMD sleep 60;java $JAVA_OPTS -jar zero-ai-server-biz.jar
