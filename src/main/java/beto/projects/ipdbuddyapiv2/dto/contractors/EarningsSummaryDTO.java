package beto.projects.ipdbuddyapiv2.dto.contractors;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;


@Builder
@AllArgsConstructor
public class EarningsSummaryDTO {
    private BigDecimal totalEarnings;
    private BigDecimal earnedThisWeek;
    private BigDecimal earnedThisMonth;
    private BigDecimal earnedThisYear;
    private BigDecimal averageJobValue;
    private BigDecimal highestJobValue;


    public BigDecimal getTotalEarnings() {
        return totalEarnings;
    }

    public BigDecimal getEarnedThisWeek() {
        return earnedThisWeek;
    }

    public BigDecimal getEarnedThisMonth() {
        return earnedThisMonth;
    }

    public BigDecimal getEarnedThisYear() {
        return earnedThisYear;
    }

    public BigDecimal getAverageJobValue() {
        return averageJobValue;
    }

    public BigDecimal getHighestJobValue() {
        return highestJobValue;
    }

    @Override
    public String toString() {
        return "EarningsSummaryDTO{" +
                "totalEarnings=" + totalEarnings +
                ", earnedThisWeek=" + earnedThisWeek +
                ", earnedThisMonth=" + earnedThisMonth +
                ", earnedThisYear=" + earnedThisYear +
                ", averageJobValue=" + averageJobValue +
                ", highestJobValue=" + highestJobValue +
                '}';
    }
}


