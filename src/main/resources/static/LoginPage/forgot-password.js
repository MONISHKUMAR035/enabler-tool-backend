document.getElementById("forgotForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const email = document.getElementById("email").value.trim();
  const msg = document.getElementById("msg");
  msg.textContent = "Sending...";
  try {
    const res = await fetch("http://localhost:8080/api/auth/forgot-password", {
      method: "POST",
      headers: {"Content-Type":"application/json"},
      body: JSON.stringify({ email })
    });
    if (res.ok) {
      msg.style.color = "green";
      msg.textContent = "If an account exists, a reset link was sent to the email.";
    } else {
      msg.style.color = "red";
      msg.textContent = "An error occurred. Try later.";
    }
  } catch (err) {
    msg.style.color = "red";
    msg.textContent = "Server not reachable.";
  }
});
