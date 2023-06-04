FROM mysql/mysql-server:8.0.32

MAINTAINER wordtalk(wangiegie@gmail.com)

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY ./1schema.sql /docker-entrypoint-initdb.d

COPY ./2wordtalkx.sql /docker-entrypoint-initdb.d

COPY ./3wordtalkx_ac.sql /docker-entrypoint-initdb.d

COPY ./4wordtalkx_job.sql /docker-entrypoint-initdb.d

COPY ./5wordtalkx_mp.sql /docker-entrypoint-initdb.d

COPY ./6wordtalkx_config.sql /docker-entrypoint-initdb.d

COPY ./7wordtalkx_pay.sql /docker-entrypoint-initdb.d

COPY ./8wordtalkx_codegen.sql /docker-entrypoint-initdb.d

COPY ./99wordtalkx_bi.sql /docker-entrypoint-initdb.d

COPY ./999wordtalkx_app.sql /docker-entrypoint-initdb.d
