package beto.projects.ipdbuddyapiv2.dto.contractors;


import java.math.BigDecimal;


public class EarningsSummaryDTO {
    private BigDecimal totalEarnings;
    private BigDecimal earnedThisWeek;
    private BigDecimal earnedThisMonth;
    private BigDecimal earnedThisYear;
    private BigDecimal averageJobValue;
    private BigDecimal highestJobValue;

    public EarningsSummaryDTO(BigDecimal totalEarnings, BigDecimal earnedThisWeek, BigDecimal earnedThisMonth, BigDecimal earnedThisYear, BigDecimal averageJobValue, BigDecimal highestJobValue) {
        this.totalEarnings = totalEarnings;
        this.earnedThisWeek = earnedThisWeek;
        this.earnedThisMonth = earnedThisMonth;
        this.earnedThisYear = earnedThisYear;
        this.averageJobValue = averageJobValue;
        this.highestJobValue = highestJobValue;
    }

    public static EarningsSummaryDTOBuilder builder() {
        return new EarningsSummaryDTOBuilder();
    }


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

    public static class EarningsSummaryDTOBuilder {
        private BigDecimal totalEarnings;
        private BigDecimal earnedThisWeek;
        private BigDecimal earnedThisMonth;
        private BigDecimal earnedThisYear;
        private BigDecimal averageJobValue;
        private BigDecimal highestJobValue;

        EarningsSummaryDTOBuilder() {
        }

        public EarningsSummaryDTOBuilder totalEarnings(BigDecimal totalEarnings) {
            this.totalEarnings = totalEarnings;
            return this;
        }

        public EarningsSummaryDTOBuilder earnedThisWeek(BigDecimal earnedThisWeek) {
            this.earnedThisWeek = earnedThisWeek;
            return this;
        }

        public EarningsSummaryDTOBuilder earnedThisMonth(BigDecimal earnedThisMonth) {
            this.earnedThisMonth = earnedThisMonth;
            return this;
        }

        public EarningsSummaryDTOBuilder earnedThisYear(BigDecimal earnedThisYear) {
            this.earnedThisYear = earnedThisYear;
            return this;
        }

        public EarningsSummaryDTOBuilder averageJobValue(BigDecimal averageJobValue) {
            this.averageJobValue = averageJobValue;
            return this;
        }

        public EarningsSummaryDTOBuilder highestJobValue(BigDecimal highestJobValue) {
            this.highestJobValue = highestJobValue;
            return this;
        }

        public EarningsSummaryDTO build() {
            return new EarningsSummaryDTO(this.totalEarnings, this.earnedThisWeek, this.earnedThisMonth, this.earnedThisYear, this.averageJobValue, this.highestJobValue);
        }

        public String toString() {
            return "EarningsSummaryDTO.EarningsSummaryDTOBuilder(totalEarnings=" + this.totalEarnings + ", earnedThisWeek=" + this.earnedThisWeek + ", earnedThisMonth=" + this.earnedThisMonth + ", earnedThisYear=" + this.earnedThisYear + ", averageJobValue=" + this.averageJobValue + ", highestJobValue=" + this.highestJobValue + ")";
        }
    }
}


