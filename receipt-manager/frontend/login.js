document.getElementById("loginForm").addEventListener("submit", async function (e) {
  e.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const user = { email, password };

  try {
    const res = await fetch("http://localhost:8080/api/users/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(user),
    });

    const text = await res.text();
    document.getElementById("loginResponse").textContent = text;

    if (text.toLowerCase().includes("successful")) {
      sessionStorage.setItem("loggedInEmail", email);
      setTimeout(() => {
        window.location.href = "index.html";
      }, 2000);
    }
  } catch (error) {
    document.getElementById("loginResponse").textContent = "❌ Login failed.";
    console.error(error);
  }
});
