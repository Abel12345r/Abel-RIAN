# Abel & Rian — Our Love Story

A Spring Boot website for a private shared space, with time of day greetings, relationship dates, private notes, surprises, and heartbeat activity.

## Local setup

Requires Java 17 and Maven. Configure your shared passkey in your shell before launching:

```powershell
$env:LOVE_PASSKEY = '<set-your-private-passkey-locally>'
$env:SESSION_COOKIE_SECURE = 'true'
mvn spring-boot:run
```

Open `http://localhost:8080`. H2 stores data in `./lovestory.mv.db` by default. Keep this file private and back it up if you need to retain local data.

The supplied couple photo is included as `couple.jpg` and packaged as `/couple.jpg` for the login and hero backgrounds.

## Render deployment

1. Push the project to GitHub and create a Render Blueprint from `render.yaml`.
2. In Render, set the secret environment variable `LOVE_PASSKEY` to the shared passkey `<set-your-private-passkey-locally>`. Never commit this value.
3. The blueprint configures a persistent disk mounted at `/var/data`, `LOVE_DB_PATH`, and secure session cookies. Persistent disks require a paid Render web service plan.
4. The supplied `couple.jpg` is already included in the Docker build and static resources.

Render provides HTTPS and sets the application port. The page and API are served by the same Spring Boot service.

## Security and feature notes

- Login asks the visitor to identify as Abel or Rian and uses the same environment-configured passkey for both. Sessions use HTTP-only, same-site cookies; write requests require a session CSRF token. Failed attempts are rate limited by client IP.
- Notes and surprises are stored in the H2 database. Users can view content associated with their account and edit only records they created. Surprise and note cards are visually sealed until opened.
- Heartbeats are stored server-side and the recipient's open page checks for new ones periodically. Browser notifications and vibration depend on permission/support. This implementation does **not** send Web Push to a closed browser or offline device; that requires VAPID keys and a push delivery service, which have not been configured.
- The default H2 database is appropriate for this small deployment. Back up the Render disk. For deployments that need managed database backups or multiple app instances, migrate to PostgreSQL.
