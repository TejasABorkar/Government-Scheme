import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function AdminDashboard() {
  const navigate = useNavigate();
  const userName = localStorage.getItem('user_name') || 'Admin';
  const [stats, setStats] = useState<any>(null);

  useEffect(() => {
    // Dashboard stats fetch kar
    const fetchStats = async () => {
      try {
        const res = await client.get('/admin/dashboard/stats');
        setStats(res.data);
      } catch (err) {
        console.error('Failed to fetch stats', err);
      }
    };
    fetchStats();
  }, []);

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <div className="max-w-6xl mx-auto py-8">
      {/* Header */}
      <div className="flex flex-col sm:flex-row justify-between items-center mb-8 bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <div>
          <h1 className="text-2xl font-bold text-green-800">Admin Dashboard</h1>
          <p className="text-gray-500">Welcome back, {userName}</p>
        </div>
        <Button variant="outline" onClick={handleLogout} className="mt-4 sm:mt-0">
          Logout
        </Button>
      </div>

      {/* Stats Grid */}
      {stats && (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <p className="text-gray-500 text-sm font-medium mb-1">Total Schemes</p>
            <h3 className="text-3xl font-bold text-green-700">{stats.totalSchemes}</h3>
          </div>
          <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <p className="text-gray-500 text-sm font-medium mb-1">Registered Farmers</p>
            <h3 className="text-3xl font-bold text-blue-700">{stats.totalFarmers}</h3>
          </div>
          <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <p className="text-gray-500 text-sm font-medium mb-1">Eligibility Checks</p>
            <h3 className="text-3xl font-bold text-amber-600">{stats.totalEligibilityChecks}</h3>
          </div>
        </div>
      )}

      {/* Actions */}
      <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
        <h2 className="text-xl font-bold text-gray-800 mb-4">Quick Actions</h2>
        <div className="flex flex-wrap gap-4">
          <Link to="/admin/schemes/new">
            <Button variant="primary">➕ Add New Scheme</Button>
          </Link>
          <Link to="/admin/audit">
            <Button variant="secondary">📊 Audit Logs</Button>
          </Link>
          <Link to="/admin/schemes">
            <Button variant="primary">📋 Manage Schemes</Button>
          </Link>
        </div>
      </div>
    </div>
  );
}