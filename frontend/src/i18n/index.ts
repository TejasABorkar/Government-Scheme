import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import HttpBackend from 'i18next-http-backend';

i18n
  .use(HttpBackend)
  .use(initReactI18next)
  .init({
    fallbackLng: 'mr',
    supportedLngs: ['mr', 'hi', 'en'],
    backend: {
      loadPath: '/locales/{{lng}}/translation.json', // Public folder madhun load karel
    },
    interpolation: {
      escapeValue: false,
    },
    lng: localStorage.getItem('app_language') || 'mr',
   debug: import.meta.env.DEV,

  });

export default i18n;