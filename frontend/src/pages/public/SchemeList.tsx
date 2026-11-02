import { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import client from '../../api/client';

interface Scheme {
  id: number;
  name: string;
  shortDescription: string;
  level: string;
  categoryName: string;
  isActive: boolean;
}

export default function SchemeList() {
  const { t, i18n } = useTranslation();
  const [schemes, setSchemes] = useState<Scheme[]>([]);
  const [loading, setLoading] = useState(true);
  const [search, setSearch] = useState('');

  // Backend madhun data fetch karne
  useEffect(() => {
    const fetchSchemes = async () => {
      setLoading(true);
      try {
        // Backend URL: http://localhost:8080/api/v1/schemes?lang=mr
        const res = await client.get(`/schemes?lang=${i18n.language}`);
        setSchemes(res.data.content); // PagedResponse madhun content
      } catch (err) {
        console.error('Error fetching schemes:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchSchemes();
  }, [i18n.language]); // Language badlali tar punha fetch karel

  // Search filter
  const filteredSchemes = schemes.filter(s => 
    s.name.toLowerCase().includes(search.toLowerCase())
  );

  return (
    <div className="max-w-6xl mx-auto">
      <h1 className="text-3xl font-bold text-green-800 mb-6">
        {t('schemes.title')}
      </h1>

      {/* Search Bar */}
      <div className="mb-8">
        <input
          type="text"
          placeholder={t('schemes.search_placeholder')}
          className="w-full p-4 border border-gray-300 rounded-xl focus:ring-2 focus:ring-green-500 focus:border-green-500 shadow-sm"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
        />
      </div>

      {/* Loading State */}
      {loading ? (
        <div className="text-center py-12 text-gray-500 text-lg">
          {t('schemes.loading')}
        </div>
      ) : filteredSchemes.length === 0 ? (
        <div className="text-center py-12 text-gray-500 text-lg">
          {t('schemes.no_schemes')}
        </div>
      ) : (
        /* Scheme Cards Grid */
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredSchemes.map((scheme) => (
            <div key={scheme.id} className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex flex-col">
              
              {/* Badge (Central / State) */}
              <div className="flex justify-between items-start mb-3">
                <span className={`text-xs font-bold px-2 py-1 rounded-md ${
                  scheme.level === 'CENTRAL' ? 'bg-blue-100 text-blue-700' : 'bg-amber-100 text-amber-700'
                }`}>
                  {scheme.level === 'CENTRAL' ? t('schemes.central') : t('schemes.state')}
                </span>
                <span className="text-xs text-gray-500 bg-gray-100 px-2 py-1 rounded-md">
                  {scheme.categoryName}
                </span>
              </div>

              {/* Scheme Name */}
              <h3 className="text-xl font-bold text-gray-800 mb-2 flex-1">
                {scheme.name}
              </h3>

              {/* Short Description */}
              <p className="text-sm text-gray-600 mb-4 line-clamp-3">
                {scheme.shortDescription || 'No description available.'}
              </p>

              {/* View Button */}
              <Link 
                to={`/schemes/${scheme.id}`}
                className="text-green-600 font-medium text-sm hover:text-green-800 mt-auto"
              >
                {t('schemes.view_details')} →
              </Link>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}