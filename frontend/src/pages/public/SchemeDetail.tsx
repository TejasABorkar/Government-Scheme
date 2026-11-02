import { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { useTranslation } from 'react-i18next';
import client from '../../api/client';
import Button from '../../components/ui/Button';
import EligibilityResult from '../farmer/EligibilityResult';

export default function SchemeDetail() {
  const { id } = useParams();
  const { t, i18n } = useTranslation();
  const navigate = useNavigate();
  
  const [scheme, setScheme] = useState<any>(null);
  const [guidance, setGuidance] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  
  const [checkingEligibility, setCheckingEligibility] = useState(false);
  const [eligibilityResult, setEligibilityResult] = useState<any>(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const schemeRes = await client.get(`/schemes/${id}?lang=${i18n.language}`);
        setScheme(schemeRes.data);

        const guidanceRes = await client.get(`/schemes/${id}/guidance?lang=${i18n.language}`);
        setGuidance(guidanceRes.data);
      } catch (err) {
        console.error('Error fetching details:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id, i18n.language]);

  const handleCheckEligibility = async () => {
    const token = localStorage.getItem('access_token');
    if (!token) {
      alert('कृपया पात्रता तपासण्यासाठी लॉगिन करा!');
      navigate('/login');
      return;
    }

    setCheckingEligibility(true);
    try {
      const res = await client.post(`/schemes/${id}/eligibility?lang=${i18n.language}`);
      setEligibilityResult(res.data);
    } catch (err: any) {
      if (err.response?.status === 400 && err.response?.data?.message?.includes('profile')) {
        alert('कृपया पात्रता तपासण्यापूर्वी तुमची प्रोफाइल पूर्ण करा!');
        navigate('/profile');
      } else {
        alert('पात्रता तपासताना त्रुटी आली.');
      }
    } finally {
      setCheckingEligibility(false);
    }
  };

  if (loading) return <div className="text-center py-12 text-lg">{t('schemes.loading')}</div>;
  if (!scheme) return <div className="text-center py-12 text-red-500">Scheme not found</div>;

  return (
    <div className="max-w-4xl mx-auto relative">
      
      {/* Eligibility Result Modal - officialUrl pass kela */}
      {eligibilityResult && (
        <EligibilityResult 
          result={eligibilityResult} 
          officialUrl={scheme.officialUrl} // <-- He mahatvache ahe
          onClose={() => setEligibilityResult(null)} 
        />
      )}

      <Link to="/schemes" className="text-green-600 font-medium text-sm mb-4 inline-block hover:underline">
        ← Back to Schemes
      </Link>

      <div className="bg-white p-6 md:p-8 rounded-2xl shadow-sm border border-gray-100 mb-6">
        <div className="flex flex-wrap gap-2 mb-4">
          <span className={`text-xs font-bold px-3 py-1 rounded-md ${
            scheme.level === 'CENTRAL' ? 'bg-blue-100 text-blue-700' : 'bg-amber-100 text-amber-700'
          }`}>
            {scheme.level === 'CENTRAL' ? t('schemes.central') : t('schemes.state')}
          </span>
          <span className="text-xs text-gray-600 bg-gray-100 px-3 py-1 rounded-md">
            {scheme.categoryName}
          </span>
        </div>

        <h1 className="text-3xl font-bold text-gray-800 mb-2">{scheme.name}</h1>
        <p className="text-gray-600 mb-4">{scheme.department}</p>
        
        {scheme.description && (
          <div className="mb-4">
            <h3 className="font-semibold text-gray-800 mb-1">Description</h3>
            <p className="text-gray-600">{scheme.description}</p>
          </div>
        )}

        {scheme.benefits && (
          <div className="mb-4">
            <h3 className="font-semibold text-gray-800 mb-1">Benefits</h3>
            <p className="text-gray-600">{scheme.benefits}</p>
          </div>
        )}

        <div className="bg-yellow-50 text-yellow-800 p-3 rounded-lg text-sm mb-6 border border-yellow-200">
          ⚠️ {scheme.disclaimer}
        </div>

        <div className="flex flex-col sm:flex-row gap-3">
          <Button 
            variant="primary" 
            fullWidth 
            onClick={handleCheckEligibility}
            disabled={checkingEligibility}
          >
            {checkingEligibility ? 'तपासत आहे...' : '✅ Check Eligibility'}
          </Button>
          
          <a href={scheme.officialUrl} target="_blank" rel="noopener noreferrer" className="w-full">
            <Button variant="outline" fullWidth>
              🌐 Go to Official Portal
            </Button>
          </a>
        </div>
      </div>

      {/* Guidance Section (Tuzha adhi cha code - same thev) */}
      {guidance && (
        <div className="space-y-6">
          {/* Documents */}
          {guidance.documents?.length > 0 && (
            <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
              <h2 className="text-xl font-bold text-gray-800 mb-4">📄 Required Documents</h2>
              <ul className="space-y-3">
                {guidance.documents.map((doc: any, i: number) => (
                  <li key={i} className="flex flex-col border-b border-gray-100 pb-2 last:border-0">
                    <span className="font-medium text-gray-800">
                      {doc.name} {doc.isMandatory && <span className="text-red-500 text-xs">*Mandatory</span>}
                    </span>
                    <span className="text-sm text-gray-500">Get from: {doc.whereToGet}</span>
                  </li>
                ))}
              </ul>
            </div>
          )}

          {/* Steps */}
          {guidance.steps?.length > 0 && (
            <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
              <h2 className="text-xl font-bold text-gray-800 mb-4">📝 How to Apply</h2>
              <ol className="list-decimal list-inside space-y-2 text-gray-700">
                {guidance.steps.map((step: any, i: number) => (
                  <li key={i}>{step.instruction}</li>
                ))}
              </ol>
            </div>
          )}

          {/* FAQs */}
          {guidance.faqs?.length > 0 && (
            <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
              <h2 className="text-xl font-bold text-gray-800 mb-4">❓ FAQs</h2>
              <div className="space-y-4">
                {guidance.faqs.map((faq: any, i: number) => (
                  <div key={i}>
                    <h4 className="font-semibold text-gray-800">Q: {faq.question}</h4>
                    <p className="text-gray-600 text-sm mt-1">A: {faq.answer}</p>
                  </div>
                ))}
              </div>
            </div>
          )}

          {/* Video */}
          {guidance.video && (
            <div className="bg-white p-6 rounded-2xl shadow-sm border border-gray-100">
              <h2 className="text-xl font-bold text-gray-800 mb-4">🎥 Guidance Video</h2>
              <div className="aspect-video rounded-lg overflow-hidden">
                <iframe
                  width="100%"
                  height="100%"
                  src={guidance.video.embedUrl.replace('youtube.com', 'youtube-nocookie.com')}
                  title="Guidance Video"
                  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                  allowFullScreen
                ></iframe>
              </div>
              <p className="text-xs text-gray-500 mt-2">⚠️ {guidance.video.notice}</p>
            </div>
          )}
        </div>
      )}
    </div>
  );
}