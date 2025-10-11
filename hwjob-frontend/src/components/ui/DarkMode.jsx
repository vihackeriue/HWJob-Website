import React, { useEffect, useState } from "react";

function DarkMode() {
  const [theme, setTheme] = useState(
    () => localStorage.getItem("theme") === "dark"
  );
  useEffect(() => {
    if (theme) {
      document.documentElement.classList.add("dark");
      localStorage.setItem("theme", "dark");
    } else {
      document.documentElement.classList.remove("dark");
      localStorage.setItem("theme", "light");
    }
  }, [theme]);
  return (
    <button onClick={() => setTheme(!theme)}>
      {theme ? "Light Mode" : "Dark Mode"}
    </button>
  );
}

export default DarkMode;
