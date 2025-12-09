// login.js (updated to send loginValue)
document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();
  const errorMsg = document.getElementById("error-msg");

  if (!username || !password) {
    errorMsg.textContent = "Username and password are required.";
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      // match backend DTO: { loginValue, password }
      body: JSON.stringify({ loginValue: username, password }),
    });

    if (response.ok) {
      errorMsg.textContent = "";
      // optionally store token/session here — currently backend returns plain text
      window.location.href = "dashboard.html";
    } else {
      const errorText = await response.text();
      errorMsg.textContent = errorText;
    }
  } catch (err) {
    console.error("Login error:", err);
    errorMsg.textContent = "Server not reachable. Please try again.";
  }
});
