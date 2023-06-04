FROM pig4cloud/java:8-jre

MAINTAINER wangiegie@gmail.com

ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

RUN mkdir -p /wordtalk-oa-platform

WORKDIR /wordtalk-oa-platform

EXPOSE 5005

ADD ./target/wordtalk-oa-platform.jar ./

CMD sleep 180;java $JAVA_OPTS -jar wordtalk-oa-platform.jar
