package com.govermentscheme.backend.guidance.entity;

import com.govermentscheme.backend.scheme.entity.Scheme;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scheme_steps")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SchemeStep {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scheme_id", nullable = false)
    private Scheme scheme;

    @Column(name = "step_no", nullable = false)
    private Integer stepNo;

    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<SchemeStepTranslation> translations = new ArrayList<>();
}