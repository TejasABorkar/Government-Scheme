import { useState } from 'react';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import client from '../../api/client';
import Button from '../../components/ui/Button';

export default function ProfileWizard() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [step, setStep] = useState(1);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  // Form state
  const [formData, setFormData] = useState({
    consentGiven: false,
    socialCategory: 'OBC',
    gender: 'MALE',
    ageGroup: 'AGE_26_35',
    incomeRange: 'L1_2_5L',
    district: '',
    taluka: '',
    village: '',
    // Land details
    land: {
      areaHectares: 1.5,
      ownershipType: 'OWNED',
      irrigationType: 'IRRIGATED',
      cropType: 'Sugarcane'
    }
  });

  const handleChange = (e: any) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleLandChange = (e: any) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      land: { ...prev.land, [name]: value }
    }));
  };

  const handleSubmit = async () => {
    setError('');
    setLoading(true);

    try {
      // 1. Save Profile (Consent + Personal + Location)
      await client.put('/me/profile', {
        consentGiven: formData.consentGiven,
        socialCategory: formData.socialCategory,
        gender: formData.gender,
        ageGroup: formData.ageGroup,
        incomeRange: formData.incomeRange,
        district: formData.district,
        taluka: formData.taluka,
        village: formData.village
      });

      // 2. Add Land Holding
      await client.post('/me/land', {
        areaHectares: parseFloat(formData.land.areaHectares as any),
        ownershipType: formData.land.ownershipType,
        irrigationType: formData.land.irrigationType,
        cropType: formData.land.cropType
      });

      // Success zala var Dashboard var redirect
      navigate('/farmer-dashboard');
      
    } catch (err: any) {
      setError(err.response?.data?.message || 'Failed to save profile. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  // Step 1: Consent
  if (step === 1) {
    return (
      <div className="max-w-lg mx-auto mt-10 bg-white p-8 rounded-2xl shadow-sm border border-gray-100">
        <h2 className="text-2xl font-bold text-green-800 mb-6">{t('profile.consent_title')}</h2>
        <div className="bg-gray-50 p-4 rounded-lg mb-6">
          <label className="flex items-start gap-3 cursor-pointer">
            <input
              type="checkbox"
              name="consentGiven"
              checked={formData.consentGiven}
              onChange={handleChange}
              className="mt-1 w-5 h-5 text-green-600 rounded"
            />
            <span className="text-sm text-gray-700 leading-relaxed">
              {t('profile.consent_text')}
            </span>
          </label>
        </div>
        <Button 
          variant="primary" 
          fullWidth 
          disabled={!formData.consentGiven}
          onClick={() => setStep(2)}
        >
          Next
        </Button>
      </div>
    );
  }

  // Step 2: Form
  return (
    <div className="max-w-2xl mx-auto mt-10 bg-white p-8 rounded-2xl shadow-sm border border-gray-100">
      <h2 className="text-2xl font-bold text-green-800 mb-6">{t('profile.title')}</h2>

      {error && <div className="bg-red-50 text-red-600 p-3 rounded-lg mb-4">{error}</div>}

      <form onSubmit={(e) => { e.preventDefault(); handleSubmit(); }} className="space-y-6">
        
        {/* Personal Details */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.category')}</label>
            <select name="socialCategory" value={formData.socialCategory} onChange={handleChange} className="w-full p-3 border rounded-lg">
              <option value="GENERAL">General</option>
              <option value="OBC">OBC</option>
              <option value="SC">SC</option>
              <option value="ST">ST</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.gender')}</label>
            <select name="gender" value={formData.gender} onChange={handleChange} className="w-full p-3 border rounded-lg">
              <option value="MALE">Male</option>
              <option value="FEMALE">Female</option>
              <option value="OTHER">Other</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.age_group')}</label>
            <select name="ageGroup" value={formData.ageGroup} onChange={handleChange} className="w-full p-3 border rounded-lg">
              <option value="AGE_18_25">18-25</option>
              <option value="AGE_26_35">26-35</option>
              <option value="AGE_36_50">36-50</option>
              <option value="ABOVE_60">Above 60</option>
            </select>
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.income_range')}</label>
            <select name="incomeRange" value={formData.incomeRange} onChange={handleChange} className="w-full p-3 border rounded-lg">
              <option value="BELOW_1L">Below 1 Lakh</option>
              <option value="L1_2_5L">1 - 2.5 Lakh</option>
              <option value="L2_5_5L">2.5 - 5 Lakh</option>
              <option value="ABOVE_10L">Above 10 Lakh</option>
            </select>
          </div>
        </div>

        {/* Location Details */}
        <h3 className="text-lg font-semibold border-b pb-2 mt-6">{t('profile.location_details')}</h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.district')}</label>
            <input type="text" name="district" required value={formData.district} onChange={handleChange} className="w-full p-3 border rounded-lg" />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.taluka')}</label>
            <input type="text" name="taluka" required value={formData.taluka} onChange={handleChange} className="w-full p-3 border rounded-lg" />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.village')}</label>
            <input type="text" name="village" required value={formData.village} onChange={handleChange} className="w-full p-3 border rounded-lg" />
          </div>
        </div>

        {/* Land Details */}
        <h3 className="text-lg font-semibold border-b pb-2 mt-6">{t('profile.land_details')}</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.area_hectares')}</label>
            <input type="number" step="0.1" name="areaHectares" required value={formData.land.areaHectares} onChange={handleLandChange} className="w-full p-3 border rounded-lg" />
          </div>
          <div>
            <label className="block text-sm font-medium mb-1">{t('profile.crop_type')}</label>
            <input type="text" name="cropType" required value={formData.land.cropType} onChange={handleLandChange} className="w-full p-3 border rounded-lg" />
          </div>
        </div>

        <Button type="submit" variant="primary" fullWidth disabled={loading} className="mt-8">
          {loading ? '...' : t('profile.save_profile')}
        </Button>
      </form>
    </div>
  );
}