FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms512m -Xmx1024m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /wordtalk-upms

WORKDIR /wordtalk-upms

EXPOSE 4000

ADD ./target/wordtalk-upms-biz.jar ./

CMD sleep 60;java $JAVA_OPTS -jar wordtalk-upms-biz.jar
