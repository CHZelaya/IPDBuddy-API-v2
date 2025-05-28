package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemInputDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
public class JobSubmissionRequestDTO {

    private LocalDate date;
    private String address;
    private List<BillableItemInputDTO> billables;
    private String notes;

    public JobSubmissionRequestDTO() {
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public List<BillableItemInputDTO> getBillables() {
        return billables;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {
        return "JobSubmissionRequestDTO{" +
                "date=" + date +
                ", address='" + address + '\'' +
                ", billables=" + billables +
                ", notes='" + notes + '\'' +
                '}';
    }
}
