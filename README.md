# FeedHub

Feed subscription services.

```bash
mvn verify spring-boot:build-image

docker run -e SPRING_PROFILES_INCLUDE=image -p 8080:8080 -v  C:\Users\robin\test:/tmp/app devhub:0.0.1-SNAPSHOT
```
