package com.govermentscheme.backend.user.entity;

public enum IncomeRange {
    BELOW_1L(1),
    L1_2_5L(2),
    L2_5_5L(3),
    L5_10L(4),
    ABOVE_10L(5);

    private final int rank;
    IncomeRange(int rank) { this.rank = rank; }
    public int getRank() { return rank; }
}