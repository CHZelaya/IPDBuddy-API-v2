package beto.projects.ipdbuddyapiv2.services;


import beto.projects.ipdbuddyapiv2.repos.JobRepo;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepo jobRepo;

    public JobService(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }


}
