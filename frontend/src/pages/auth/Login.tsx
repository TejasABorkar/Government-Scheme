import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { Link, useNavigate } from 'react-router-dom';
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function Login() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({ emailOrPhone: '', password: '' });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const res = await client.post('/auth/login', formData);
      
      localStorage.setItem('access_token', res.data.accessToken);
      localStorage.setItem('user_role', res.data.role);
      localStorage.setItem('user_name', res.data.name);

      if (res.data.role === 'ADMIN') {
        navigate('/admin'); // Admin dashboard pudhe banavnar
      } else {
        navigate('/profile'); // Farmer profile kade
      }
      
    } catch (err: any) {
      setError(err.response?.data?.message || 'Invalid credentials');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-8 rounded-2xl shadow-sm border border-gray-100">
      <div className="text-center mb-6">
        <span className="text-4xl">🔐</span>
        <h2 className="text-2xl font-bold text-green-800 mt-2">
          {t('auth.login_title')}
        </h2>
      </div>

      {error && (
        <div className="bg-red-50 text-red-600 p-3 rounded-lg mb-4 text-sm border border-red-100">
          {error}
        </div>
      )}

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Email / Mobile</label>
          <input
            type="text" required
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            value={formData.emailOrPhone}
            onChange={(e) => setFormData({ ...formData, emailOrPhone: e.target.value })}
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">{t('auth.password')}</label>
          <input
            type="password" required
            className="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            value={formData.password}
            onChange={(e) => setFormData({ ...formData, password: e.target.value })}
          />
        </div>

        <Button type="submit" fullWidth disabled={loading}>
          {loading ? '...' : t('auth.submit_login')}
        </Button>
      </form>

      <p className="text-center text-sm text-gray-600 mt-6">
        {t('auth.dont_have_account')}{' '}
        <Link to="/register" className="text-green-600 font-medium hover:underline">
          {t('auth.register_now')}
        </Link>
      </p>
    </div>
  );
}