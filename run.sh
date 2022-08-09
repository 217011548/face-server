/root/maven/bin/mvn clean package -DskipTests --settings settings.xml -U
docker build -t server:2 .
docker-compose down && docker-compose up -d
