// ✅ Fetch employees
document.getElementById("findEmployeeForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const department = document.getElementById("department").value;
  const country = document.getElementById("country").value;
  const state = document.getElementById("state").value;
  const status = document.getElementById("status").value;

  const response = await fetch(
    `http://localhost:8080/api/employee/filter?department=${department}&country=${country}&state=${state}&status=${status}`
  );

  const data = await response.json();
  const resultsDiv = document.getElementById("employeeResults");
  const total = document.getElementById("totalEmployees");
  const active = document.getElementById("activeEmployees");
  const inactive = document.getElementById("inactiveEmployees");

  if (data.length > 0) {
    total.textContent = data.length;
    active.textContent = data.filter(emp => emp.status === "Active").length;
    inactive.textContent = data.filter(emp => emp.status === "Inactive").length;

    let tableHTML = `
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Department</th>
            <th>Country</th>
            <th>State</th>
            <th>Status</th>
            <th>Project</th>
          </tr>
        </thead>
        <tbody>
    `;

    data.forEach(emp => {
      tableHTML += `
        <tr>
          <td>${emp.id}</td>
          <td>${emp.name}</td>
          <td>${emp.department}</td>
          <td>${emp.country}</td>
          <td>${emp.state}</td>
          <td>${emp.status}</td>
          <td>${emp.project_name}</td>
        </tr>`;
    });

    tableHTML += `</tbody></table>`;
    resultsDiv.innerHTML = tableHTML;
  } else {
    resultsDiv.innerHTML = `<p>No employees found.</p>`;
    total.textContent = active.textContent = inactive.textContent = 0;
  }
});

// ✅ Clear filters
document.getElementById("clearFilters").addEventListener("click", () => {
  document.getElementById("findEmployeeForm").reset();
  document.getElementById("employeeResults").innerHTML = `<p class="placeholder">Results will appear here...</p>`;
});

// ✅ Logout
document.getElementById("logoutBtn").addEventListener("click", () => {
  if (confirm("Are you sure you want to logout?")) {
    window.location.href = "login.html";
  }
});

// ✅ Export to Excel
document.getElementById("exportBtn").addEventListener("click", () => {
  const table = document.querySelector("table");
  if (!table) {
    alert("No data to export!");
    return;
  }

  let csv = [];
  for (let row of table.rows) {
    let cells = Array.from(row.cells).map(cell => cell.innerText);
    csv.push(cells.join(","));
  }

  const blob = new Blob([csv.join("\n")], { type: "text/csv" });
  const link = document.createElement("a");
  link.href = URL.createObjectURL(blob);
  link.download = "EmployeeData.csv";
  link.click();
});
