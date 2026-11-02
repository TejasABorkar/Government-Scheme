package com.govermentscheme.backend.i18n;

import com.govermentscheme.backend.scheme.entity.Language;
import com.govermentscheme.backend.scheme.entity.SchemeTranslation;
import com.govermentscheme.backend.scheme.entity.SchemeCategoryTranslation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TranslationResolver {

    /**
     * Resolve translation for a list of scheme translations.
     * Fallback: requested lang -> English -> any available
     */
    public Optional<SchemeTranslation> resolveScheme(List<SchemeTranslation> translations, String lang) {
        if (translations == null || translations.isEmpty()) return Optional.empty();
        String target = normalize(lang);
        return translations.stream()
                .filter(t -> target.equals(t.getLangCode()))
                .findFirst()
                .or(() -> translations.stream()
                        .filter(t -> Language.EN.equals(t.getLangCode()))
                        .findFirst())
                .or(() -> translations.stream().findFirst());
    }

    public Optional<SchemeCategoryTranslation> resolveCategory(
            List<SchemeCategoryTranslation> translations, String lang) {
        if (translations == null || translations.isEmpty()) return Optional.empty();
        String target = normalize(lang);
        return translations.stream()
                .filter(t -> target.equals(t.getLangCode()))
                .findFirst()
                .or(() -> translations.stream()
                        .filter(t -> Language.EN.equals(t.getLangCode()))
                        .findFirst())
                .or(() -> translations.stream().findFirst());
    }

    private String normalize(String lang) {
        if (lang == null) return Language.DEFAULT;
        String l = lang.toLowerCase();
        return Language.SUPPORTED.contains(l) ? l : Language.DEFAULT;
    }
}