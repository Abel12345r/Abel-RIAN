# Abel & Rian — Our Little World

A small Spring Boot site for sharing love notes and sending a shared heartbeat.

## Run locally

Requires Java 17 and Maven.

```sh
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080).

## Deploy on Render

1. Push this repository to GitHub.
2. In Render, choose **New → Blueprint**, connect the repository, and let Render read `render.yaml`.
3. Deploy the `our-love-story` web service. Render builds the executable JAR and starts it on Render's assigned port.

The page and API share one service, so no localhost URL or separate frontend setup is needed. Set the anniversary date in `index.html` (and keep `PageStructure.html` in sync if you use that copy).

## Shared data

Love notes and heartbeat counts are held in the running app's memory. Both visitors see updates while the service is running, but the data resets when Render restarts or redeploys the service. Use a database and persistent storage if you want the messages to last through restarts.
