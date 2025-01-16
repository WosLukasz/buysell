### Deploy

1. Add to /etc/hosts (on Windows)
```
127.0.0.1 localhost mongodb-primary mongodb-secondary mongodb-arbiter
```
Known bug mongodb replicaset on Windows

2. Run docker containers

```
docker-compose up
```
3. Open Minio client and create API key
Open `http://localhost:9000` -> Access Keys -> Create access key -> Create -> copy keys and paste to `application.properties`  

4. Start the app