0. Optional: remove existing tag
docker rm cwsfe_cms;

0. Optional: enter folder with Dockerfile
cd 9.4

1. Build postgres image in docker
docker build -t cwsfe_cms .

2. Run image with published port 55432
docker run -p 127.0.0.1:55432:5432 cwsfe_cms

3. Connect over JDBC
jdbc:postgresql://localhost:55432/postgres
with login "postgres" and password "postgres"