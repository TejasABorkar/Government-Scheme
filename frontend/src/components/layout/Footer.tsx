import { useTranslation } from 'react-i18next';

export default function Footer() {
  const { t } = useTranslation();
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gray-100 border-t border-gray-200 mt-auto">
      <div className="max-w-6xl mx-auto px-4 py-6 text-center">
        <p className="text-sm text-gray-600 mb-2 font-medium">
          ⚠️ {t('footer.disclaimer')}
        </p>
        <p className="text-xs text-gray-400">
          © {currentYear} {t('app_name')}. {t('footer.rights')}
        </p>
      </div>
    </footer>
  );
}