// 📦 Add Receipt
document.getElementById("receiptForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const productName = document.getElementById("productName").value;
  const storeName = document.getElementById("storeName").value;
  const purchaseDate = document.getElementById("purchaseDate").value;
  const warrantyMonths = parseInt(document.getElementById("warrantyMonths").value);
  const notes = document.getElementById("notes").value;
  const fileInput = document.getElementById("receiptImage");

  const userEmail = sessionStorage.getItem("loggedInEmail");
  if (!userEmail) {
    document.getElementById("responseMessage").textContent = "❌ Please log in to add a receipt.";
    return;
  }

  let fileUrl = "";

  // 🖼️ Upload Image
  if (fileInput.files.length > 0) {
    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    try {
      const uploadResponse = await fetch("http://localhost:8080/api/upload/image", {
        method: "POST",
        body: formData
      });

      if (uploadResponse.ok) {
        fileUrl = await uploadResponse.text();
      } else {
        document.getElementById("responseMessage").textContent = "❌ Image upload failed.";
        return;
      }
    } catch (error) {
      console.error("Image upload error:", error);
      document.getElementById("responseMessage").textContent = "❌ Image upload failed.";
      return;
    }
  }

  // 🧾 Submit Receipt
  const receipt = {
    productName,
    storeName,
    purchaseDate,
    warrantyMonths,
    notes,
    fileUrl,
    email: userEmail // ✅ Fix: should be "email", not "userEmail"
  };

  try {
    const response = await fetch("http://localhost:8080/api/receipts", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(receipt)
    });

    if (response.ok) {
      document.getElementById("responseMessage").textContent = "✅ Receipt added successfully!";
      document.getElementById("receiptForm").reset();
      document.getElementById("imagePreview").innerHTML = '';
      setTimeout(() => {
        document.getElementById("responseMessage").textContent = "";
      }, 2500);
      fetchReceipts();
    } else {
      throw new Error("Something went wrong");
    }
  } catch (error) {
    console.error("Add receipt error:", error);
    document.getElementById("responseMessage").textContent = "❌ Failed to add receipt.";
  }
});

// 📥 Fetch Receipts (Filtered by logged-in user)
async function fetchReceipts() {
  const userEmail = sessionStorage.getItem("loggedInEmail");
  if (!userEmail) {
    document.getElementById("responseMessage").textContent = "❌ Please log in to view receipts.";
    return;
  }

  try {
    const res = await fetch(`http://localhost:8080/api/receipts?email=${encodeURIComponent(userEmail)}`);
    const data = await res.json();

    console.log("📥 Receipts fetched for:", userEmail);
    console.log("🧾 Data received:", data); // ✅ Debug: see what's returned

    const tbody = document.querySelector("#receiptTable tbody");
    tbody.innerHTML = "";

    if (Array.isArray(data) && data.length === 0) {
      tbody.innerHTML = `<tr><td colspan="7">No receipts found.</td></tr>`;
      return;
    }

    data.forEach(receipt => {
      const row = document.createElement("tr");

      row.innerHTML = `
        <td>${receipt.productName}</td>
        <td>${receipt.storeName}</td>
        <td>${receipt.purchaseDate}</td>
        <td>${receipt.warrantyMonths} months</td>
        <td>${receipt.expiryDate || 'N/A'}</td>
        <td>
          ${receipt.fileUrl ? `<a href="http://localhost:8080${receipt.fileUrl}" target="_blank">View</a>` : ''}
        </td>
        <td>
          <button class="action-btn edit" onclick="editReceipt('${receipt.id}')">Edit</button>
          <button class="action-btn delete" onclick="deleteReceipt('${receipt.id}')">Delete</button>
        </td>
      `;

      tbody.appendChild(row);
    });

  } catch (err) {
    console.error("Failed to fetch receipts:", err);
    document.getElementById("responseMessage").textContent = "❌ Failed to fetch receipts.";
  }
}

// ✏️ Edit Stub
function editReceipt(id) {
  alert("Edit functionality coming soon! ID: " + id);
}

// ❌ Delete
async function deleteReceipt(id) {
  const confirmDelete = confirm("Are you sure you want to delete this receipt?");
  if (!confirmDelete) return;

  try {
    const response = await fetch(`http://localhost:8080/api/receipts/${id}`, {
      method: "DELETE"
    });

    if (response.ok) {
      fetchReceipts();
    } else {
      alert("Failed to delete receipt.");
    }
  } catch (err) {
    console.error("Error deleting receipt:", err);
  }
}

// 🖼️ Image Preview
const receiptImageInput = document.getElementById('receiptImage');
const imagePreview = document.getElementById('imagePreview');

receiptImageInput.addEventListener('change', function () {
  const file = this.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function (e) {
      imagePreview.innerHTML = `<img src="${e.target.result}" alt="Receipt Preview" />`;
    };
    reader.readAsDataURL(file);
  } else {
    imagePreview.innerHTML = '';
  }
});

// 🔒 Logout functionality
const logoutBtn = document.getElementById("logoutBtn");
if (logoutBtn) {
  logoutBtn.addEventListener("click", () => {
    sessionStorage.removeItem("loggedInEmail");
    window.location.href = "login.html";
  });
}
function toggleTheme() {
  document.body.classList.toggle("dark-mode");

  // Save theme preference
  if (document.body.classList.contains("dark-mode")) {
    localStorage.setItem("theme", "dark");
  } else {
    localStorage.setItem("theme", "light");
  }
}

// Apply saved theme on load
window.addEventListener("load", () => {
  const savedTheme = localStorage.getItem("theme");
  if (savedTheme === "dark") {
    document.body.classList.add("dark-mode");
  }

  fetchReceipts(); // keep your existing load logic
});

// 🟢 Initial Load
window.onload = fetchReceipts;
