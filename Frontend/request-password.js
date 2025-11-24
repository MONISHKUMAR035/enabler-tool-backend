// request-password.js
const form = document.getElementById("requestForm");
const msg = document.getElementById("msg");

form.addEventListener("submit", async (e) => {
  e.preventDefault();
  msg.textContent = "";
  const email = document.getElementById("email").value.trim();

  if (!email) {
    msg.style.color = "red";
    msg.textContent = "Please enter an email.";
    return;
  }

  try {
    const res = await fetch("http://localhost:8080/api/password-reset/request", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email })
    });

    const text = await res.text();
    if (res.ok) {
      msg.style.color = "green";
      msg.textContent = "If an account exists for that email, a reset link has been sent.";
    } else {
      msg.style.color = "red";
      msg.textContent = text || "Email not found.";
    }
  } catch (err) {
    msg.style.color = "red";
    msg.textContent = "Server error. Try again later.";
    console.error(err);
  }
});
