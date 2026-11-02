import { Link, useLocation } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import LanguageSwitcher from '../shared/LanguageSwitcher'; 

export default function Header() {
  const { t } = useTranslation();
  const location = useLocation();
  const isHome = location.pathname === '/';

  return (
    <header className="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-50">
      <div className="max-w-6xl mx-auto px-4 h-16 flex items-center justify-between">
        
        {/* Logo */}
        <Link to="/" className="flex items-center gap-2">
          <span className="text-2xl">🌾</span>
          <span className="font-bold text-green-700 text-lg hidden sm:block">
            {t('app_name')}
          </span>
        </Link>

        {/* Right Side: Language + Auth Buttons */}
        <div className="flex items-center gap-4">
          <LanguageSwitcher />
          
          {!isHome && (
            <Link 
              to="/" 
              className="text-sm font-medium text-gray-600 hover:text-green-700"
            >
              {t('nav.home')}
            </Link>
          )}
        </div>
      </div>
    </header>
  );
}