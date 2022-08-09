/root/maven/bin/mvn clean package -DskipTests --settings settings.xml -U
docker build -t server:2 .


npm run build

docker build -t web:2 .