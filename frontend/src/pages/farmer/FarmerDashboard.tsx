import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function FarmerDashboard() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const userName = localStorage.getItem('user_name') || 'Farmer';
  const [profile, setProfile] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Profile madhun data fetch kar (completeness check sathi)
    const fetchProfile = async () => {
      try {
        const res = await client.get('/me/profile');
        setProfile(res.data);
      } catch (err) {
        console.error('Profile not found or error', err);
      } finally {
        setLoading(false);
      }
    };
    fetchProfile();
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  if (loading) return <div className="text-center py-12 text-lg">Loading...</div>;

  return (
    <div className="max-w-4xl mx-auto py-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row justify-between items-center mb-8 bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <div>
          <h1 className="text-2xl font-bold text-green-800">नमस्कार, {userName}! 🙏</h1>
          <p className="text-gray-500">तुमचे farmer dashboard मध्ये स्वागत आहे.</p>
        </div>
        <Button variant="outline" onClick={handleLogout} className="mt-4 sm:mt-0">
          {t('nav.logout')}
        </Button>
      </div>

      {/* Profile Status Card */}
      <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100 mb-6">
        <h2 className="text-xl font-bold text-gray-800 mb-4">प्रोफाइल स्थिती</h2>
        
        {profile ? (
          <div>
            <div className="flex items-center gap-4 mb-2">
              <div className="w-full bg-gray-200 rounded-full h-4">
                <div 
                  className="bg-green-600 h-4 rounded-full" 
                  style={{ width: `${profile.completenessPercentage}%` }}
                ></div>
              </div>
              <span className="font-bold text-green-700">{profile.completenessPercentage}%</span>
            </div>
            <p className="text-sm text-gray-600 mb-4">
              {profile.completenessPercentage === 100 
                ? 'तुमची प्रोफाइल पूर्ण झाली आहे! आता तुम्ही योजनांसाठी पात्रता तपासू शकता.' 
                : 'कृपया पात्रता तपासण्यासाठी तुमची प्रोफाइल पूर्ण करा.'}
            </p>
            <div className="flex gap-2 flex-wrap">
              <Link to="/schemes">
                <Button variant="primary">🔍 योजना पहा</Button>
              </Link>
              <Button variant="secondary">✅ पात्रता तपासा</Button>
            </div>
          </div>
        ) : (
          <div>
            <p className="text-sm text-gray-600 mb-4">तुमची प्रोफाइल अजून तयार केलेली नाही.</p>
            <Link to="/profile">
              <Button variant="primary">प्रोफाइल पूर्ण करा</Button>
            </Link>
          </div>
        )}
      </div>
    </div>
  );
}