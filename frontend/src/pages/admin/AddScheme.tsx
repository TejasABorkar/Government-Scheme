import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next'; // <-- Import
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function AddScheme() {
  const { t } = useTranslation(); // <-- Hook
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const [formData, setFormData] = useState({
    level: 'CENTRAL',
    categoryId: 1,
    department: '',
    officialUrl: '',
    grReference: '',
    en: { name: '', description: '', benefits: '', eligibilitySummary: '' },
    mr: { name: '', description: '', benefits: '', eligibilitySummary: '' },
    hi: { name: '', description: '', benefits: '', eligibilitySummary: '' },
  });

  const handleBasicChange = (e: any) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleTranslationChange = (lang: string, field: string, value: string) => {
    setFormData({
      ...formData,
      [lang]: { ...formData[lang as 'en'|'mr'|'hi'], [field]: value }
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const payload = {
        level: formData.level,
        categoryId: parseInt(formData.categoryId as any),
        department: formData.department,
        officialUrl: formData.officialUrl,
        grReference: formData.grReference,
        translations: [
          { langCode: 'en', ...formData.en },
          { langCode: 'mr', ...formData.mr },
          { langCode: 'hi', ...formData.hi }
        ]
      };

      await client.post('/admin/schemes?lang=en', payload);
      alert('Scheme added successfully!');
      navigate('/admin');
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to add scheme.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto py-8">
      <h1 className="text-2xl font-bold text-green-800 mb-6">{t('admin.add_scheme_title')}</h1>

      {error && <div className="bg-red-50 text-red-600 p-3 rounded-lg mb-4">{error}</div>}

      <form onSubmit={handleSubmit} className="space-y-6">
        
        {/* Basic Details */}
        <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
          <h2 className="text-lg font-bold mb-4 border-b pb-2">{t('admin.basic_details')}</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium mb-1">{t('admin.level')}</label>
              <select name="level" value={formData.level} onChange={handleBasicChange} className="w-full p-3 border rounded-lg">
                <option value="CENTRAL">{t('schemes.central')}</option>
                <option value="STATE">{t('schemes.state')}</option>
              </select>
            </div>
            <div>
              <label className="block text-sm font-medium mb-1">{t('admin.category')}</label>
              <select name="categoryId" value={formData.categoryId} onChange={handleBasicChange} className="w-full p-3 border rounded-lg">
                <option value={1}>Agriculture</option>
                <option value={2}>Subsidy</option>
                <option value={3}>Insurance</option>
                <option value={4}>Loan</option>
                <option value={5}>Equipment</option>
                <option value={6}>Irrigation</option>
              </select>
            </div>
            <div className="md:col-span-2">
              <label className="block text-sm font-medium mb-1">{t('admin.department')}</label>
              <input type="text" name="department" required value={formData.department} onChange={handleBasicChange} className="w-full p-3 border rounded-lg" />
            </div>
            <div className="md:col-span-2">
              <label className="block text-sm font-medium mb-1">{t('admin.official_url')}</label>
              <input type="url" name="officialUrl" required value={formData.officialUrl} onChange={handleBasicChange} className="w-full p-3 border rounded-lg" />
            </div>
            <div className="md:col-span-2">
              <label className="block text-sm font-medium mb-1">{t('admin.gr_reference')}</label>
              <input type="text" name="grReference" value={formData.grReference} onChange={handleBasicChange} className="w-full p-3 border rounded-lg" />
            </div>
          </div>
        </div>

        {/* Translations */}
        {[
          { code: 'en', titleKey: 'admin.en_translation' },
          { code: 'mr', titleKey: 'admin.mr_translation' },
          { code: 'hi', titleKey: 'admin.hi_translation' }
        ].map((lang) => (
          <div key={lang.code} className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
            <h2 className="text-lg font-bold mb-4 border-b pb-2">{t(lang.titleKey)}</h2>
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">{t('admin.scheme_name')} ({lang.code})</label>
                <input type="text" required value={formData[lang.code as 'en'|'mr'|'hi'].name} onChange={(e) => handleTranslationChange(lang.code, 'name', e.target.value)} className="w-full p-3 border rounded-lg" />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">{t('admin.description')} ({lang.code})</label>
                <textarea required value={formData[lang.code as 'en'|'mr'|'hi'].description} onChange={(e) => handleTranslationChange(lang.code, 'description', e.target.value)} className="w-full p-3 border rounded-lg" rows={2} />
              </div>
              <div>
                <label className="block text-sm font-medium mb-1">{t('admin.benefits')} ({lang.code})</label>
                <textarea value={formData[lang.code as 'en'|'mr'|'hi'].benefits} onChange={(e) => handleTranslationChange(lang.code, 'benefits', e.target.value)} className="w-full p-3 border rounded-lg" rows={2} />
              </div>
            </div>
          </div>
        ))}

        <Button type="submit" variant="primary" fullWidth disabled={loading}>
          {loading ? t('admin.saving') : t('admin.create_scheme')}
        </Button>
      </form>
    </div>
  );
}