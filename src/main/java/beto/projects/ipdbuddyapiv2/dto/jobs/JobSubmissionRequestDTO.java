package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemsRequestDTO;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public class JobSubmissionRequestDTO {

    private LocalDate date;
    private String address;
    private List<BillableItemsRequestDTO> itemsRequests;

    public JobSubmissionRequestDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public List<BillableItemsRequestDTO> getItemsRequests() {
        return itemsRequests;
    }

    @Override
    public String toString() {
        return "JobSubmissionRequestDTO{" +
                "date=" + date +
                ", address='" + address + '\'' +
                ", itemsRequests=" + itemsRequests +
                '}';
    }
}
