import { useTranslation } from 'react-i18next';
import Button from '../../components/ui/Button';

interface Props {
  result: any;
  officialUrl: string; // <-- Navin add kela
  onClose: () => void;
}

export default function EligibilityResult({ result, officialUrl, onClose }: Props) {
  const { t } = useTranslation();

  // Result nusar colors ani icons
  const getResultStyle = () => {
    switch (result.result) {
      case 'ELIGIBLE':
        return { bg: 'bg-green-50', border: 'border-green-200', text: 'text-green-800', icon: '✅', title: 'तुम्ही पात्र आहात! (Eligible)' };
      case 'PARTIALLY_ELIGIBLE':
        return { bg: 'bg-amber-50', border: 'border-amber-200', text: 'text-amber-800', icon: '⚠️', title: 'अंशतः पात्र (Partially Eligible)' };
      case 'NOT_ELIGIBLE':
        return { bg: 'bg-red-50', border: 'border-red-200', text: 'text-red-800', icon: '❌', title: 'तुम्ही पात्र नाही आहात (Not Eligible)' };
      default:
        return { bg: 'bg-gray-50', border: 'border-gray-200', text: 'text-gray-800', icon: 'ℹ️', title: 'निकाल' };
    }
  };

  const style = getResultStyle();

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
      <div className="bg-white rounded-2xl shadow-xl max-w-lg w-full max-h-[90vh] overflow-y-auto">
        
        {/* Header */}
        <div className={`p-6 rounded-t-2xl ${style.bg} border-b ${style.border} text-center`}>
          <span className="text-5xl block mb-2">{style.icon}</span>
          <h2 className={`text-2xl font-bold ${style.text}`}>{style.title}</h2>
          <p className="text-sm text-gray-600 mt-1">Scheme: {result.schemeName}</p>
        </div>

        <div className="p-6 space-y-4">
          {/* Failed Rules */}
          {result.failedRules?.length > 0 && (
            <div>
              <h3 className="font-semibold text-gray-800 mb-2">अयशस्वी निकष (Failed Rules):</h3>
              <ul className="space-y-2">
                {result.failedRules.map((rule: any, i: number) => (
                  <li key={i} className="bg-red-50 text-red-700 p-3 rounded-lg text-sm border border-red-100">
                    ❌ {rule.message}
                  </li>
                ))}
              </ul>
            </div>
          )}

          {/* Missing Fields */}
          {result.missingFields?.length > 0 && (
            <div>
              <h3 className="font-semibold text-gray-800 mb-2">अपूर्ण माहिती (Missing Fields):</h3>
              <ul className="space-y-2">
                {result.missingFields.map((field: string, i: number) => (
                  <li key={i} className="bg-amber-50 text-amber-700 p-3 rounded-lg text-sm border border-amber-100">
                    ⚠️ {field.replace(/_/g, ' ').toUpperCase()} - कृपया तुमची प्रोफाइल पूर्ण करा.
                  </li>
                ))}
              </ul>
            </div>
          )}

          {/* Notice */}
          <div className="bg-blue-50 text-blue-800 p-3 rounded-lg text-xs border border-blue-100">
            ℹ️ {result.notice}
          </div>

          {/* Actions */}
          <div className="flex gap-3 pt-2">
            <Button variant="outline" fullWidth onClick={onClose}>
              बंद करा (Close)
            </Button>
            {result.result === 'ELIGIBLE' && (
              <a href={officialUrl} target="_blank" rel="noopener noreferrer" className="w-full">
                <Button variant="primary" fullWidth>
                  अर्ज करा (Apply Now) →
                </Button>
              </a>
            )}
          </div>
        </div>

      </div>
    </div>
  );
}