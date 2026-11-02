package com.govermentscheme.backend.scheme.entity;

import java.util.Set;

public final class Language {
    public static final String EN = "en";
    public static final String HI = "hi";
    public static final String MR = "mr";
    public static final String DEFAULT = MR;
    public static final Set<String> SUPPORTED = Set.of(EN, HI, MR);

    private Language() {}
}