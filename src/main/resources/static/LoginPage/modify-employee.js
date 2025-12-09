const apiUrl = "http://localhost:8080/api/employee";
const toast = document.getElementById("toast");

function showToast(message, isError = false) {
  toast.textContent = message;
  toast.className = `toast show ${isError ? "error" : ""}`;
  setTimeout(() => (toast.className = "toast"), 3000);
}

// 🔍 Search Employee by ID
document.getElementById("searchBtn").addEventListener("click", async () => {
  const id = document.getElementById("searchId").value.trim();
  if (!id) {
    showToast("Please enter an Employee ID", true);
    return;
  }

  try {
    const response = await fetch(`${apiUrl}/get/${id}`);
    if (response.ok) {
      const emp = await response.json();
      document.getElementById("employeeDetails").classList.remove("hidden");

      document.getElementById("searchIdDisplay").value = emp.id || "";
      document.getElementById("empName").value = emp.name || "";
      document.getElementById("empDept").value = emp.department || "";
      document.getElementById("empEmail").value = emp.email || "";
      document.getElementById("empPhone").value = emp.phone_number || "";
      document.getElementById("empCountry").value = emp.country || "";
      document.getElementById("empRegion").value = emp.region || "";
      document.getElementById("empState").value = emp.state || "";
      document.getElementById("empStatus").value = emp.status || "";
      document.getElementById("empProject").value = emp.project_name || "";
      document.getElementById("empJoining").value = emp.joining_date || "";
      document.getElementById("empLastUpdated").value = emp.last_updated ? new Date(emp.last_updated).toLocaleString() : "";

      document.getElementById("updateBtn").onclick = async () => {
        const updated = {
          name: document.getElementById("empName").value,
          department: document.getElementById("empDept").value,
          email: document.getElementById("empEmail").value,
          phone_number: document.getElementById("empPhone").value,
          country: document.getElementById("empCountry").value,
          region: document.getElementById("empRegion").value,
          state: document.getElementById("empState").value,
          status: document.getElementById("empStatus").value,
          project_name: document.getElementById("empProject").value,
          joining_date: document.getElementById("empJoining").value
        };

        const updateResponse = await fetch(`${apiUrl}/update/${id}`, {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(updated)
        });

        if (updateResponse.ok) {
          showToast("✅ Employee updated successfully!");
          const fresh = await (await fetch(`${apiUrl}/get/${id}`)).json();
          document.getElementById("empLastUpdated").value = fresh.last_updated ? new Date(fresh.last_updated).toLocaleString() : "";
        } else showToast("❌ Failed to update employee.", true);
      };

      document.getElementById("deleteBtn").onclick = async () => {
        if (confirm("Are you sure you want to delete this employee?")) {
          const delResponse = await fetch(`${apiUrl}/delete/${id}`, { method: "DELETE" });
          if (delResponse.ok) {
            showToast("🗑️ Employee deleted successfully!");
            document.getElementById("employeeDetails").classList.add("hidden");
          } else showToast("❌ Failed to delete employee.", true);
        }
      };

    } else showToast("Employee not found!", true);
  } catch (err) {
    console.error(err);
    showToast("Server error while fetching employee", true);
  }
});

// 🧩 Modal Functionality
const modal = document.getElementById("addModal");
const closeModal = document.getElementById("closeModal");
const cancelBtn = document.getElementById("cancelBtn");

document.getElementById("addEmployeeBtn").addEventListener("click", () => modal.classList.remove("hidden"));
closeModal.addEventListener("click", () => { modal.classList.add("hidden"); document.getElementById("addEmployeeForm").reset(); });
cancelBtn.addEventListener("click", () => { modal.classList.add("hidden"); document.getElementById("addEmployeeForm").reset(); });
modal.addEventListener("click", (e) => { if (e.target === modal) { modal.classList.add("hidden"); document.getElementById("addEmployeeForm").reset(); }});

// ✅ Add New Employee
document.getElementById("addEmployeeForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const newEmployee = {
    name: document.getElementById("newName").value,
    department: document.getElementById("newDept").value,
    email: document.getElementById("newEmail").value,
    phone_number: document.getElementById("newPhone").value,
    country: document.getElementById("newCountry").value,
    region: document.getElementById("newRegion").value,
    state: document.getElementById("newState").value,
    status: document.getElementById("newStatus").value,
    project_name: document.getElementById("newProject").value,
    joining_date: document.getElementById("newJoining").value
  };

  try {
    const response = await fetch(`${apiUrl}/add`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(newEmployee)
    });

    if (response.ok) {
      showToast("✅ Employee added successfully!");
      modal.classList.add("hidden");
      e.target.reset();
    } else showToast("❌ Error adding employee.", true);
  } catch (err) {
    console.error(err);
    showToast("Server error while adding employee", true);
  }
});
