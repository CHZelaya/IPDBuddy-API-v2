package beto.projects.ipdbuddyapiv2.enums;
import java.math.BigDecimal;


public enum Billables {
        INSULATION(new BigDecimal("30.00"), "Bag of Insulation"),
        DRYWALL(new BigDecimal("20.00"), "A sheet of Dry Wall"),

        //* Max of $75
        FIRE_CAULKING(new BigDecimal("5.00"), "Fire Caulking"),

        //* Scaffolding is paid at $25 per section
        SCAFFOLDING(new BigDecimal("25.00"), "Scaffolding work per section"),

        //* Garage bulkhead $35 per side
        HIGH_GARAGE_BULKHEAD(new BigDecimal("35.00"), "High Garage Bulkhead per side"),

        //* Pinch point strips $40 per Single-Family house
        //* $50 per duplex
        PINCH_POINT_STRIPS_SINGLE_FAMILY_HOME(new BigDecimal("40.00"), "Pinch Point Strips Single Family"),
        PINCH_POINT_STRIPS_DUPLEX(new BigDecimal("50.00"), "Pinch Point Strips Duplex"),

        //* Poly only $25 - $50
        POLY_ONLY_SMALL(new BigDecimal("25.00"), "Poly Only Small"),
        POLY_ONLY_LARGE(new BigDecimal("50.00"), "Poly Only Large"),

        //* Fire caulking ladder runs zero lot lines Mattamy Homes $25 per floor
        FIRE_CAULKING_MATTAMY_HOUSE(new BigDecimal("25.00"), "Fire Caulking Mattamy House"),

        SCRAP_OUT(new BigDecimal("25.00"), "Scrap Out per House"),
        SUITED_MECH_ROOM_RES_BAR(new BigDecimal("65.00"), "Suited Mech Room, Res Bar install"),

        //* Suited Mech Room Ceilings
        //* $500 for Steel, $300 for board only, if two mechs, second is $150
        STEEL_FRAMING_AND_BOARD(new BigDecimal("500.00"), "Steel Framing and Board"),
        BOARD_ONLY(new BigDecimal("300.00"), "Board Only"),
        SECOND_MECH_ROOM(new BigDecimal("150.00"), "Second Mech Room"),

        //* If two mech room ceiling, second is $100.
        FIRE_TAPING_MECH_ROOM_CEILING(new BigDecimal("225.00"), "Fire Taping Mech Room, ceiling"),
        FIRE_TAPING_SECOND_MECH_ROOM(new BigDecimal("100.00"), "Fire Taping Second Mech Room");


        private final BigDecimal rate;
        private final String description;

        Billables(BigDecimal rate, String description) {
            this.rate = rate;
            this.description = description;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public String getDescription() {
            return description;
        }


}

