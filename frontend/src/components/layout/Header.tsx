import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import LanguageSwitcher from '../shared/LanguageSwitcher';

export default function Header() {
  const { t } = useTranslation();
  const location = useLocation();
  const navigate = useNavigate();
  
  const isHome = location.pathname === '/';
  
  // Auth State
  const token = localStorage.getItem('access_token');
  const role = localStorage.getItem('user_role');
  const userName = localStorage.getItem('user_name');

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
    window.location.reload(); // Header refresh sathi
  };

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

        {/* Right Side */}
        <div className="flex items-center gap-4">
          <LanguageSwitcher />
          
          {token ? (
            <>
              {/* Logged In Menu */}
              {role === 'ADMIN' && (
                <Link to="/admin" className="text-sm font-medium text-green-700 hover:text-green-800">
                    Admin Dashboard
                </Link>
                )}
                <Link to="/farmer-dashboard" className="text-sm font-medium text-green-700 hover:text-green-800">
                    My Dashboard
                </Link>
                
              <button 
                onClick={handleLogout}
                className="text-sm font-medium text-red-600 hover:text-red-700"
              >
                {t('nav.logout')}
              </button>
            </>
          ) : (
            <>
              {/* Logged Out Menu */}
              <Link to="/login" className="text-sm font-medium text-gray-600 hover:text-green-700">
                {t('nav.login')}
              </Link>
              <Link 
                to="/register" 
                className="bg-green-600 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-green-700"
              >
                {t('nav.register')}
              </Link>
            </>
          )}
        </div>
      </div>
    </header>
  );
}