document.getElementById("registerForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const user = { email, password };

  try {
    const res = await fetch("http://localhost:8080/api/users/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });

    const text = await res.text();
    document.getElementById("registerResponse").textContent = text;

    if (text.includes("successfully")) {
      setTimeout(() => {
        window.location.href = "login.html";
      }, 2000);
    }
  } catch (error) {
    document.getElementById("registerResponse").textContent = "❌ Registration failed.";
    console.error(error);
  }
});
