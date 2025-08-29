# MovieApp

A simple Java servlet-based web application for browsing movies, powered by The Movie Database (TMDb) API and a MySQL backend for user data (auth + lists).



## ✨ Features

- Browse popular movies via TMDb
- Basic auth: register / login (session-based)
- Personal lists:
  - Favorites
  - Watchlist
  - Watched
- Simple responsive frontend (HTML + Tailwind from CDN)
- REST-ish endpoints served by Java Servlets

## 🧱 Tech Stack

- **Backend:** Java 11, Servlets (Jakarta/Javax), Maven
- **DB:** MySQL
- **HTTP Client/JSON:** `HttpURLConnection`, Gson
- **Logging:** SLF4J / Logback (jars under `WEB-INF/lib`)
- **Frontend:** HTML + CSS + JS (vanilla, Tailwind via CDN)
- **App Server:** Tomcat (or any Servlet 4 container)

## 📂 Project Structure

```
MovieApp/
├─ pom.xml
├─ src/main/java/com/movieapp/
│  ├─ DatabaseConnection.java
│  ├─ TmdbApiClient.java
│  ├─ Movie.java
│  ├─ MovieDatabase.java
│  ├─ LoginServlet.java
│  ├─ RegisterServlet.java
│  ├─ GetUserInfoServlet.java
│  ├─ UpdateUserInfoServlet.java
│  ├─ MovieServlet.java
│  ├─ SearchServlet.java
│  ├─ FavoritesServlet.java
│  ├─ WatchlistServlet.java
│  ├─ WatchedServlet.java
│  ├─ AddToFavoritesServlet.java
│  ├─ AddToWatchlistServlet.java
│  ├─ AddToWatchedServlet.java
│  ├─ RemoveFromFavoritesServlet.java
│  ├─ RemoveFromWatchlistServlet.java
│  └─ RemoveFromWatchedServlet.java
├─ src/main/webapp/
│  ├─ index.html
│  ├─ search.html
│  ├─ styles.css
│  ├─ script.js
│  └─ WEB-INF/
│     ├─ web.xml
│     ├─ lib/ (jdbc + gson + logging jars)
│     └─ logback.xml
└─ .idea/, .mvn/, out/ (IDE/build artifacts)
```

## 🔧 Configuration

### 1) TMDb API key

Replace the in-code placeholder in `TmdbApiClient.java` with your key, or (recommended) read from an environment variable:

```java
// TmdbApiClient.java
private static final String API_KEY = System.getenv("TMDB_API_KEY");
```

Then export your key before running the server:

```bash
export TMDB_API_KEY=your_tmdb_key
```

### 2) Database

`DatabaseConnection.java` currently uses constants for connection info. Replace these with environment variables to avoid committing secrets:

```java
// DatabaseConnection.java
private static final String URL = System.getenv("DB_URL");        // e.g. jdbc:mysql://localhost:3306/movies_db
private static final String USER = System.getenv("DB_USER");      // e.g. root
private static final String PASSWORD = System.getenv("DB_PASS");  // your password
```

Export them locally:

```bash
export DB_URL="jdbc:mysql://localhost:3306/movies_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
export DB_USER="root"
export DB_PASS="your_password"
```

### 3) Example schema (minimal)

This app expects tables for users and movie lists. Here's a minimal starting point you can adjust as needed:

```sql
CREATE DATABASE IF NOT EXISTS movies_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE movies_db;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS movies (
  id INT PRIMARY KEY,          -- TMDb movie id
  title VARCHAR(255) NOT NULL,
  releaseDate VARCHAR(20),
  rating DOUBLE,
  posterPath VARCHAR(255)
);

-- normalize list entries by type, or keep separate tables; example uses separate tables:
CREATE TABLE IF NOT EXISTS favorites (
  user_id INT NOT NULL,
  movie_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_id, movie_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS watchlist LIKE favorites;
CREATE TABLE IF NOT EXISTS watched LIKE favorites;
```

> If your code uses different names/columns, align the DDL accordingly.

## ▶️ Running Locally

### Prerequisites

- Java 11+
- Maven 3.8+
- MySQL 8+
- Apache Tomcat 9/10 (or any Servlet 4 compatible container)

### Build

```bash
mvn clean package
```

This typically produces a WAR you can deploy to Tomcat (e.g., `target/MovieApp-1.0-SNAPSHOT.war`). If your IDE config outputs to `out/artifacts/...`, you can also deploy that exploded directory.

### Deploy to Tomcat

1. Copy the WAR to `{TOMCAT_HOME}/webapps/` or configure a context in Tomcat.
2. Start Tomcat and open:  
   `http://localhost:8080/MovieApp/` (your context path may differ).

### Frontend during development

- Open `src/main/webapp/index.html` directly for quick UI iteration, but note that API calls in `script.js` point to `http://localhost:8080`. Make sure the backend is running at that origin and the context path matches or adjust `BACKEND_URL` in `script.js` accordingly.

## 🔌 API Endpoints (selected)

The servlets expose routes (exact names depend on annotations and mapping):

- `GET /movies` — list movies (JSON/HTML depending on implementation)
- `GET /search?query=...` — search placeholder
- `POST /register` — create account
- `POST /login` — login (sets session)
- `GET /get-favorites` — current user favorites
- `POST /add-to-favorites` / `POST /remove-from-favorites`
- `GET /watchlist` / `POST /add-to-watchlist` / `POST /remove-from-watchlist`
- `GET /watched` / `POST /add-to-watched` / `POST /remove-from-watched`
- `GET /user` (e.g., `GetUserInfoServlet`) / `POST /user` (update)

> Check each `@WebServlet` in `src/main/java/com/movieapp` for the exact paths and payloads.

## 🛡️ Security Notes

- **Never hardcode secrets** (DB password, TMDb API key). Use env vars or a secrets manager.
- Hash passwords server-side (e.g., BCrypt). If you currently store plain text, migrate ASAP.
- Consider enabling CORS rules if serving frontend and backend from different origins.
- Validate/escape user input to avoid SQL injection and XSS.
- Use connection pooling (e.g., HikariCP) for production.

## 🗺️ Roadmap / TODO

- Replace raw JDBC with a DAO layer or JPA
- Proper JSON APIs (consistent request/response models)
- Unit/integration tests
- Pagination, filtering, genres
- Docker Compose for MySQL + Tomcat
- CI workflow (GitHub Actions)

## 📜 License

Add a license (e.g., MIT) or state “All rights reserved” if you prefer.

---

### Developer Notes

- The repo currently includes IDE and build artifacts (`.idea/`, `out/`). Consider adding or refining `.gitignore` to keep the VCS clean.
- If you deploy to GitHub, remove any committed secrets from history and rotate the credentials.
