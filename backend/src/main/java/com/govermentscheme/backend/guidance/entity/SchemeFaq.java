package com.govermentscheme.backend.guidance.entity;

import com.govermentscheme.backend.scheme.entity.Scheme;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scheme_faqs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeFaq {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @OneToMany(mappedBy = "faq", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SchemeFaqTranslation> translations = new ArrayList<>();

}