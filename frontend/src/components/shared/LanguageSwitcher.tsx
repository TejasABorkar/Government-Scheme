import { useTranslation } from 'react-i18next';
import { useLanguage } from '../../context/LanguageContext';

const LANGUAGES = [
  { code: 'mr', label: 'मराठी' },
  { code: 'hi', label: 'हिंदी' },
  { code: 'en', label: 'EN' },
];

export default function LanguageSwitcher() {
  const { language, changeLanguage } = useLanguage();
  const { t } = useTranslation();

  return (
    <div className="flex gap-1 bg-gray-100 rounded-lg p-1 border border-gray-200">
      {LANGUAGES.map((lang) => (
        <button
          key={lang.code}
          onClick={() => changeLanguage(lang.code as 'mr' | 'hi' | 'en')}
          className={`px-3 py-1.5 text-sm font-medium rounded-md transition-colors ${
            language === lang.code
              ? 'bg-white text-green-700 shadow-sm'
              : 'text-gray-600 hover:text-gray-900'
          }`}
          title={t(`language.${lang.code}`)}
        >
          {lang.label}
        </button>
      ))}
    </div>
  );
}