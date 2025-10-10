import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";

import en from "./en.json";
import vi from "./vi.json";

i18n
  .use(LanguageDetector) // tự phát hiện ngôn ngữ
  .use(initReactI18next)
  .init({
    resources: {
      en: { translation: en },
      vi: { translation: vi },
    },
    fallbackLng: "en", // nếu không tìm thấy ngôn ngữ
    interpolation: {
      escapeValue: false, // react tự xử lý XSS
    },
    detection: {
      order: ["localStorage", "navigator"],
      caches: ["localStorage"],
    },
  });

export default i18n;
