SQLite file-based db is attached to the project,
as an improvement - should be moved to the docker container togeather with sqlite3 utilite

in order to run -> ./mvnw spring-boot:run

default port: 8080
endpoint: /temperature/{city}


improvement plan:
1. docker
2. create DB view for faster selects
3. use another implementation with cloud map-reduce processing
4. migrate to cloud
