package beto.projects.ipdbuddyapiv2.dto.jobs;

import beto.projects.ipdbuddyapiv2.dto.billables.BillableItemInputDTO;

import java.time.LocalDate;
import java.util.List;

public class JobSubmissionRequestDTO {

    private LocalDate date;
    private String address;
    private List<BillableItemInputDTO> billables;
    private String notes;

    public JobSubmissionRequestDTO() {
    }

    public JobSubmissionRequestDTO(LocalDate date, String address, List<BillableItemInputDTO> billables, String notes) {
        this.date = date;
        this.address = address;
        this.billables = billables;
        this.notes = notes;
    }

    public static JobSubmissionRequestDTOBuilder builder() {
        return new JobSubmissionRequestDTOBuilder();
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

    public static class JobSubmissionRequestDTOBuilder {
        private LocalDate date;
        private String address;
        private List<BillableItemInputDTO> billables;
        private String notes;

        JobSubmissionRequestDTOBuilder() {
        }

        public JobSubmissionRequestDTOBuilder date(LocalDate date) {
            this.date = date;
            return this;
        }

        public JobSubmissionRequestDTOBuilder address(String address) {
            this.address = address;
            return this;
        }

        public JobSubmissionRequestDTOBuilder billables(List<BillableItemInputDTO> billables) {
            this.billables = billables;
            return this;
        }

        public JobSubmissionRequestDTOBuilder notes(String notes) {
            this.notes = notes;
            return this;
        }

        public JobSubmissionRequestDTO build() {
            return new JobSubmissionRequestDTO(this.date, this.address, this.billables, this.notes);
        }

        public String toString() {
            return "JobSubmissionRequestDTO.JobSubmissionRequestDTOBuilder(date=" + this.date + ", address=" + this.address + ", billables=" + this.billables + ", notes=" + this.notes + ")";
        }
    }
}
