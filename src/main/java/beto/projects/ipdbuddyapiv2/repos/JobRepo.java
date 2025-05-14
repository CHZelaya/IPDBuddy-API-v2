package beto.projects.ipdbuddyapiv2.repos;

import beto.projects.ipdbuddyapiv2.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface JobRepo extends JpaRepository<Job, Integer> {
}
