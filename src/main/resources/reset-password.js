const token = new URLSearchParams(window.location.search).get("token");
const msg = document.getElementById("message");

if (!token) {
  msg.textContent = "Invalid reset link!";
  msg.style.color = "red";
}

document.getElementById("resetForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const newPassword = document.getElementById("newPassword").value.trim();
  const confirmPassword = document.getElementById("confirmPassword").value.trim();

  if (newPassword !== confirmPassword) {
    msg.style.color = "red";
    msg.textContent = "Passwords do not match!";
    return;
  }

  const response = await fetch("http://localhost:8080/api/password-reset/confirm", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify({ token, newPassword })
  });

  const text = await response.text();

  if (response.ok) {
    msg.style.color = "green";
    msg.textContent = "Password updated! Redirecting to login...";

    setTimeout(() => {
      window.location.href = "login.html";
    }, 2000);
  } else {
    msg.style.color = "red";
    msg.textContent = text || "Reset link expired or invalid.";
  }
});
