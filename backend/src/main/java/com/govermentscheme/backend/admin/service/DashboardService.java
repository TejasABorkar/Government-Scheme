package com.govermentscheme.backend.admin.service;

import com.govermentscheme.backend.admin.dto.DashboardStatsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public DashboardStatsResponse getStats() {
        Long totalFarmers = (Long) em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.role = 'FARMER'").getSingleResult();
        Long totalSchemes = (Long) em.createQuery("SELECT COUNT(s) FROM Scheme s").getSingleResult();
        Long activeSchemes = (Long) em.createQuery(
                "SELECT COUNT(s) FROM Scheme s WHERE s.isActive = true").getSingleResult();
        Long totalChecks = (Long) em.createQuery(
                "SELECT COUNT(h) FROM EligibilityCheckHistory h").getSingleResult();
        Long totalBookmarks = (Long) em.createQuery("SELECT COUNT(b) FROM Bookmark b").getSingleResult();

        @SuppressWarnings("unchecked")
        List<Object[]> checksRows = em.createNativeQuery(
                "SELECT h.scheme_id, st.name, COUNT(*) as cnt " +
                        "FROM eligibility_check_history h " +
                        "JOIN scheme_translations st ON st.scheme_id = h.scheme_id AND st.lang_code = 'en' " +
                        "GROUP BY h.scheme_id, st.name ORDER BY cnt DESC LIMIT 5").getResultList();

        List<DashboardStatsResponse.SchemeCheckCount> checks = checksRows.stream()
                .map(r -> DashboardStatsResponse.SchemeCheckCount.builder()
                        .schemeId(((Number) r[0]).longValue())
                        .schemeName((String) r[1])
                        .count(((Number) r[2]).longValue())
                        .build())
                .collect(Collectors.toList());

        @SuppressWarnings("unchecked")
        List<Object[]> bmRows = em.createNativeQuery(
                "SELECT b.scheme_id, st.name, COUNT(*) as cnt " +
                        "FROM bookmarks b " +
                        "JOIN scheme_translations st ON st.scheme_id = b.scheme_id AND st.lang_code = 'en' " +
                        "GROUP BY b.scheme_id, st.name ORDER BY cnt DESC LIMIT 5").getResultList();

        List<DashboardStatsResponse.SchemeBookmarkCount> bms = bmRows.stream()
                .map(r -> DashboardStatsResponse.SchemeBookmarkCount.builder()
                        .schemeId(((Number) r[0]).longValue())
                        .schemeName((String) r[1])
                        .count(((Number) r[2]).longValue())
                        .build())
                .collect(Collectors.toList());

        return DashboardStatsResponse.builder()
                .totalFarmers(totalFarmers)
                .totalSchemes(totalSchemes)
                .activeSchemes(activeSchemes)
                .totalEligibilityChecks(totalChecks)
                .totalBookmarks(totalBookmarks)
                .checksPerScheme(checks)
                .topBookmarkedSchemes(bms)
                .build();
    }
}