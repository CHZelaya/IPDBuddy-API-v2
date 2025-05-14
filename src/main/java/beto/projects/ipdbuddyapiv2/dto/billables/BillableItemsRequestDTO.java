package beto.projects.ipdbuddyapiv2.dto.billables;

import lombok.Builder;

@Builder
public class BillableItemsRequestDTO {

    private String billableType;
    private int quantity;


    public BillableItemsRequestDTO() {
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
}
