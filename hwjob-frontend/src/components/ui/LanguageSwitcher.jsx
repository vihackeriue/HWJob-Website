import React from "react";
import { useTranslation } from "react-i18next";
import { LANGUAGES } from "../../constants/language";

const LanguageSwitcher = () => {
  const { i18n, t } = useTranslation();

  const currentLang =
    i18n.language || localStorage.getItem("i18nextLng") || "vi";

  const changeLanguage = (e) => {
    const selectedLang = e.target.value;
    if (!selectedLang) return;
    i18n.changeLanguage(selectedLang);
    localStorage.setItem("i18nextLng", selectedLang);
  };
  console.log(currentLang);

  return (
    <div style={{ marginBottom: 20 }}>
      <label htmlFor="language-select" style={{ marginRight: 8 }}>
        {t("language")}:
      </label>
      <select
        id="language-select"
        value={currentLang}
        onChange={changeLanguage}
        style={{ padding: "4px 8px", borderRadius: 4 }}
      >
        {LANGUAGES.map(({ code, label }) => (
          <option key={code} value={code}>
            {label}
          </option>
        ))}
      </select>
    </div>
  );
};

export default LanguageSwitcher;
