package beto.projects.ipdbuddyapiv2.dto.billables;

public class BillableItemInputDTO {

    private String billableType;
    private int quantity;


    public BillableItemInputDTO() {
    }

    public BillableItemInputDTO(String billableType, int quantity) {
        this.billableType = billableType;
        this.quantity = quantity;
    }

    public static BillableItemInputDTOBuilder builder() {
        return new BillableItemInputDTOBuilder();
    }

    public String getBillableType() {
        return billableType;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "BillableItemsRequest{" +
                "billableType='" + billableType + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public static class BillableItemInputDTOBuilder {
        private String billableType;
        private int quantity;

        BillableItemInputDTOBuilder() {
        }

        public BillableItemInputDTOBuilder billableType(String billableType) {
            this.billableType = billableType;
            return this;
        }

        public BillableItemInputDTOBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public BillableItemInputDTO build() {
            return new BillableItemInputDTO(this.billableType, this.quantity);
        }

        public String toString() {
            return "BillableItemInputDTO.BillableItemInputDTOBuilder(billableType=" + this.billableType + ", quantity=" + this.quantity + ")";
        }
    }
}
