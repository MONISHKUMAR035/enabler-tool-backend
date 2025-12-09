document.getElementById("signupForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const fullname = document.getElementById("fullname").value.trim();
  const email = document.getElementById("email").value.trim();
  const username = document.getElementById("username").value.trim();
  const password = document.getElementById("password").value.trim();
  const confirmPassword = document.getElementById("confirmPassword").value.trim();
  const message = document.getElementById("message");

  if (password !== confirmPassword) {
    message.style.color = "red";
    message.textContent = "Passwords do not match!";
    return;
  }

  const userData = { fullname, email, username, password };

  try {
    const response = await fetch("http://localhost:8080/api/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(userData),
    });

    if (response.ok) {
      message.style.color = "green";
      message.textContent = "✅ Account created successfully!";
      setTimeout(() => (window.location.href = "login.html"), 2000);
    } else {
      message.style.color = "red";
      message.textContent = "❌ Failed to create account. Try again.";
    }
  } catch (error) {
    message.style.color = "red";
    message.textContent = "⚠️ Error connecting to server.";
  }
});
