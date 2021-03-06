FROM redis:6.0.5
MAINTAINER Oleg Vostokov <amberlight303@gmail.com>

CMD ["sh", "-c", "exec redis-server --requirepass \"${BLOG_REDIS_PASSWORD}\""]

EXPOSE 6379