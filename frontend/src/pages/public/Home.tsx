import { useTranslation } from 'react-i18next';
import Button from '../../components/ui/Button';
import { Link } from 'react-router-dom';

export default function Home() {
  const { t } = useTranslation();

  return (
    <div className="flex flex-col items-center justify-center py-12 text-center">
      <div className="bg-green-100 p-6 rounded-full mb-6">
        <span className="text-6xl">🌾</span>
      </div>
      
      <h1 className="text-3xl md:text-5xl font-bold text-green-800 mb-4">
        {t('home.title')}
      </h1>
      
      <p className="text-lg text-gray-600 mb-8 max-w-2xl">
        {t('home.subtitle')}
      </p>

      <div className="flex flex-col sm:flex-row gap-4 w-full max-w-md">
        {/* Fakt ekda Browse Schemes button thevla ahe */}
        <Link to="/schemes" className="w-full">
          <Button variant="primary" fullWidth>
            🔍 {t('home.browse_btn')}
          </Button>
        </Link>
        
        <Link to="/login" className="w-full">
          <Button variant="outline" fullWidth>
            🔐 {t('home.login_btn')}
          </Button>
        </Link>
      </div>
    </div>
  );
}