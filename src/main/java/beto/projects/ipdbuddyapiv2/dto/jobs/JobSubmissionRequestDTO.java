package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemsRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
public class JobSubmissionRequestDTO {

    private LocalDate date;
    private String address;
    private List<BillableItemsRequestDTO> billables;

    public JobSubmissionRequestDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public List<BillableItemsRequestDTO> getItemsRequests() {
        return billables;
    }

    @Override
    public String toString() {
        return "JobSubmissionRequestDTO{" +
                "date=" + date +
                ", address='" + address + '\'' +
                ", itemsRequests=" + billables +
                '}';
    }
}
