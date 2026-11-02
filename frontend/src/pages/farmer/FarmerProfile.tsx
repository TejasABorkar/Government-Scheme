import { useNavigate } from 'react-router-dom';
import Button from '../../components/ui/Button';

export default function FarmerProfile() {
  const navigate = useNavigate();
  const userName = localStorage.getItem('user_name') || 'Farmer';

  const handleLogout = () => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('user_role');
    localStorage.removeItem('user_name');
    navigate('/login');
  };

  return (
    <div className="max-w-4xl mx-auto py-10">
      <div className="bg-white p-8 rounded-2xl shadow-sm border border-gray-100 text-center">
        <div className="text-6xl mb-4">👨‍🌾</div>
        <h1 className="text-2xl font-bold text-green-800 mb-2">नमस्कार, {userName}!</h1>
        <p className="text-gray-600 mb-6">तुमची प्रोफाइल पूर्ण करा आणि योजनांसाठी पात्रता तपासा.</p>
        <div className="flex justify-center gap-4">
          <Button variant="primary">Complete Profile</Button>
          <Button variant="outline" onClick={handleLogout}>Logout</Button>
        </div>
      </div>
    </div>
  );
}