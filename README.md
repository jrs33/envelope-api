# envelope-api

![Actions Status](https://github.com/jrs33/envelope-api/workflows/Java%20CI%20with%20Maven/badge.svg)

Save that $$$

## Run Locally

0) Make sure you have postgres running locally. Create a database called `envelope`. Create the tables using the sql files in the `/src/main/resources/` directory in this new database.
(sorry for the manual process, didn't set up Liquibase just yet).
1) Run `docker build --tag envelope-api:1.0 .` to build the container image
2) Run `docker image ls` and confirm you see the created image
3) Run `docker run --publish 8033:8080 --detach --env-file ./.env.development --name envelope-api envelope-api:1.0` to start the container
4) Confirm the container is running by pinging an endpoint. To debug any issues, run `docker ps` and make note of the container id. May be helpful to debug by running `docker logs CONTAINER_ID`. If all is good, congrats! You now have the dockerized budget API running on your machine.
5) If you want to stop the container, just execute `docker rm --force envelope-api`
