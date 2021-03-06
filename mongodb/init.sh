#!/bin/bash
if test -z "$BLOG_MONGODB_PASSWORD"; then
    echo "BLOG_MONGODB_PASSWORD not defined"
    exit 1
fi

auth="-u user -p $BLOG_MONGODB_PASSWORD"

# MONGODB USER CREATION
(
echo "setup mongodb auth"
create_user="if (!db.getUser('user')) { db.createUser({ user: 'user', pwd: '$BLOG_MONGODB_PASSWORD', roles: [ {role:'readWrite', db:'blog'} ]}) }"
until mongo blog --eval "$create_user" || mongo blog $auth --eval "$create_user"; do sleep 5; done
killall mongod
sleep 1
killall -9 mongod
) &

# INIT DUMP EXECUTION
(
if test -n "$INIT_DUMP"; then
    echo "execute dump file"
	until mongo blog $auth $INIT_DUMP; do sleep 5; done
fi
) &

echo "start mongodb without auth"
chown -R mongodb /data/db
gosu mongodb mongod "$@"

echo "restarting with auth on"
sleep 5
exec gosu mongodb /usr/local/bin/docker-entrypoint.sh --auth "$@"