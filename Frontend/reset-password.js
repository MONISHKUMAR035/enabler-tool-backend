// reset-password.js
(function () {
  const token = new URLSearchParams(window.location.search).get("token");
  const modal = document.getElementById("resetModal");
  const closeBtn = document.getElementById("closeBtn");

  const otpForm = document.getElementById("otpForm");
  const otpInput = document.getElementById("otpInput");
  const otpMessage = document.getElementById("otpMessage");

  const resetForm = document.getElementById("resetForm");
  const newPasswordInput = document.getElementById("newPassword");
  const confirmPasswordInput = document.getElementById("confirmPassword");
  const resetMessage = document.getElementById("resetMessage");

  function openModal() {
    modal.setAttribute("aria-hidden", "false");
  }
  function closeModal() {
    modal.setAttribute("aria-hidden", "true");
  }
  closeBtn.addEventListener("click", closeModal);
  window.addEventListener("keydown", (ev) => { if (ev.key === "Escape") closeModal(); });

  if (!token) {
    alert("Invalid reset link (missing token). Make sure you clicked the link from the same machine.");
    // optionally show modal with message
    return;
  }
  openModal();

  function showOtpMessage(text, ok) {
    otpMessage.style.color = ok ? "green" : "red";
    otpMessage.textContent = text;
  }
  function showResetMessage(text, ok) {
    resetMessage.style.color = ok ? "green" : "red";
    resetMessage.textContent = text;
  }

  // STEP 1: Verify OTP
  otpForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    otpMessage.textContent = "";
    const otp = otpInput.value.trim();
    if (!otp || otp.length < 4) {
      showOtpMessage("Enter the 6-digit OTP sent to your email.", false);
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/password-reset/verify", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token, otp })
      });
      const text = await res.text();
      if (res.ok) {
        showOtpMessage("OTP verified. Now set your new password.", true);
        // show password form
        otpForm.style.display = "none";
        resetForm.style.display = "";
        newPasswordInput.focus();
      } else {
        showOtpMessage(text || "Invalid or expired OTP.", false);
      }
    } catch (err) {
      console.error(err);
      showOtpMessage("Server error. Try again later.", false);
    }
  });

  // STEP 2: Submit new password
  resetForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    resetMessage.textContent = "";

    const newPassword = newPasswordInput.value.trim();
    const confirmPassword = confirmPasswordInput.value.trim();

    if (newPassword.length < 6) {
      showResetMessage("Password must be at least 6 characters.", false);
      return;
    }
    if (newPassword !== confirmPassword) {
      showResetMessage("Passwords do not match.", false);
      return;
    }

    try {
      const res = await fetch("http://localhost:8080/api/password-reset/confirm", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ token, otp: otpInput.value.trim(), newPassword })
      });
      const text = await res.text();
      if (res.ok) {
        showResetMessage("Password updated. Redirecting to login...", true);
        setTimeout(() => { window.location.href = "login.html"; }, 1500);
      } else {
        showResetMessage(text || "Reset failed. Token/OTP invalid or expired.", false);
      }
    } catch (err) {
      console.error(err);
      showResetMessage("Server error. Try again later.", false);
    }
  });

})();
