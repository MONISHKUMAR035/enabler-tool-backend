// ✅ Navigate to pages
document.getElementById("findEmployeeBtn").addEventListener("click", () => {
  window.location.href = "find-employee.html";
});

document.getElementById("modifyEmployeeBtn").addEventListener("click", () => {
  window.location.href = "modify-employee.html";
});

// ✅ Logout Function
document.getElementById("logoutBtn").addEventListener("click", () => {
  if (confirm("Are you sure you want to logout?")) {
    window.location.href = "login.html";
  }
});
