import { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function AdminSchemeList() {
  const navigate = useNavigate();
  const [schemes, setSchemes] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);

  // Admin schemes fetch kar (draft pn disel)
  const fetchSchemes = async () => {
    setLoading(true);
    try {
      const res = await client.get('/admin/schemes?lang=en&size=100');
      setSchemes(res.data.content);
    } catch (err) {
      console.error('Failed to fetch admin schemes', err);
      if (err instanceof Error && (err as any).response?.status === 401) {
        navigate('/login');
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchSchemes();
  }, []);

  const handleActivate = async (id: number) => {
    try {
      await client.patch(`/admin/schemes/${id}/activate`);
      fetchSchemes(); // List refresh kar
    } catch (err) {
      alert('Failed to activate scheme');
    }
  };

  const handleDeactivate = async (id: number) => {
    try {
      await client.patch(`/admin/schemes/${id}/deactivate`);
      fetchSchemes(); // List refresh kar
    } catch (err) {
      alert('Failed to deactivate scheme');
    }
  };

  if (loading) return <div className="text-center py-12">Loading...</div>;

  return (
    <div className="max-w-6xl mx-auto py-8">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-green-800">Manage Schemes</h1>
        <Link to="/admin/schemes/new">
          <Button variant="primary">➕ Add New</Button>
        </Link>
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-gray-50 border-b border-gray-200">
              <th className="p-4 font-semibold text-gray-700">ID</th>
              <th className="p-4 font-semibold text-gray-700">Name (EN)</th>
              <th className="p-4 font-semibold text-gray-700">Level</th>
              <th className="p-4 font-semibold text-gray-700">Status</th>
              <th className="p-4 font-semibold text-gray-700">Actions</th>
            </tr>
          </thead>
          <tbody>
            {schemes.map((scheme) => (
              <tr key={scheme.id} className="border-b border-gray-100 hover:bg-gray-50">
                <td className="p-4 text-gray-500">{scheme.id}</td>
                <td className="p-4 font-medium">{scheme.name}</td>
                <td className="p-4 text-sm">{scheme.level}</td>
                <td className="p-4">
                  <span className={`px-2 py-1 rounded text-xs font-bold ${
                    scheme.isActive ? 'bg-green-100 text-green-700' : 'bg-gray-200 text-gray-600'
                  }`}>
                    {scheme.isActive ? 'ACTIVE' : 'DRAFT'}
                  </span>
                </td>
                <td className="p-4">
                  {scheme.isActive ? (
                    <button 
                      onClick={() => handleDeactivate(scheme.id)} 
                      className="text-red-600 text-sm font-medium hover:underline"
                    >
                      Deactivate
                    </button>
                  ) : (
                    <button 
                      onClick={() => handleActivate(scheme.id)} 
                      className="text-green-600 text-sm font-medium hover:underline"
                    >
                      Activate
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {schemes.length === 0 && (
          <div className="p-8 text-center text-gray-500">No schemes found.</div>
        )}
      </div>
    </div>
  );
}