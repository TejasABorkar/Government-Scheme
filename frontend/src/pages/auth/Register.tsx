import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function Register() {
  const { t, i18n } = useTranslation();
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    phone: '',
    password: '',
    preferredLanguage: i18n.language
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const res = await client.post('/auth/register', formData);
      
      // Token save kar
      localStorage.setItem('access_token', res.data.accessToken);
      localStorage.setItem('user_role', res.data.role);
      localStorage.setItem('user_name', res.data.name);

      // Profile kade redirect kar
      navigate('/profile');
      
    } catch (err: any) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-8 rounded-2xl shadow-sm border border-gray-100">
      <div className="text-center mb-6">
        <span className="text-4xl">🌾</span>
        <h2 className="text-2xl font-bold text-green-800 mt-2">
          {t('auth.register_title')}
        </h2>
      </div>

      {error && (
        <div className="bg-red-50 text-red-600 p-3 rounded-lg mb-4 text-sm border border-red-100">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('auth.name')}</label>
          <input
            type="text" required
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            value={formData.name}
            onChange={(e) => setFormData({ ...formData, name: e.target.value })}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('auth.email')}</label>
          <input
            type="email" required
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            value={formData.email}
            onChange={(e) => setFormData({ ...formData, email: e.target.value })}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('auth.phone')}</label>
          <input
            type="tel" required pattern="[6-9][0-9]{9}" title="10 digit Indian mobile number"
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            value={formData.phone}
            onChange={(e) => setFormData({ ...formData, phone: e.target.value })}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('auth.password')}</label>
          <input
            type="password" required minLength={8}
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
          />
          <p className="text-xs text-gray-500 mt-1">Minimum 8 characters</p>
        </div>

        <Button type="submit" fullWidth disabled={loading}>
          {loading ? '...' : t('auth.submit_register')}
        </Button>
      </form>

      <p className="text-center text-sm text-gray-600 mt-6">
        {t('auth.already_have_account')}{' '}
        <Link to="/login" className="text-green-600 font-medium hover:underline">
          {t('auth.login_now')}
        </Link>
      </p>
    </div>
  );
}