const BACKEND_URL = "http://localhost:8080";

// Sayfa açıldığında otomatik film listesi getir
window.addEventListener("DOMContentLoaded", fetchMoviesFromBackend);

function fetchMoviesFromBackend() {
    fetch(`${BACKEND_URL}/movies`)
        .then(res => res.json())
        .then(displayMovies)
        .catch(err => console.error("Film listesi alınamadı:", err));
}

function displayMovies(movies) {
    const movieList = document.getElementById("movie-list");
    movieList.innerHTML = "";
    movies.forEach(movie => {
        const card = document.createElement("div");
        card.classList.add("movie-card");
        card.innerHTML = `
      <h3>${movie.title}</h3>
      <p>Çıkış Tarihi: ${movie.releaseDate}</p>
      <p>Puan: ⭐ ${movie.rating}</p>
    `;
        movieList.appendChild(card);
    });
}

function login() {
    console.log("✅ login() çağrıldı");
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${BACKEND_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`
    })
        .then(res => res.text())
        .then(msg => {
            document.getElementById("login-message").innerText = msg;
            document.getElementById("login-message").style.color = msg.includes("başarılı") ? "#8f8" : "#f88";
            if (msg.includes("başarılı")) fetchMoviesFromBackend();
        });
}

function register() {
    console.log("✅ register() çağrıldı");
    const username = document.getElementById("reg-username").value;
    const email = document.getElementById("reg-email").value;
    const password = document.getElementById("reg-password").value;

    fetch(`${BACKEND_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `username=${encodeURIComponent(username)}&email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`
    })
        .then(res => res.text())
        .then(msg => {
            document.getElementById("register-message").innerText = msg;
            document.getElementById("register-message").style.color = msg.includes("başarılı") ? "#8f8" : "#f88";
        });
}
