package beto.projects.ipdbuddyapiv2.repos;

import beto.projects.ipdbuddyapiv2.entities.Contractor;
import com.jayway.jsonpath.JsonPath;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource
public interface ContractorRepo extends JpaRepository<Contractor, Integer> {


    Contractor findByEmail(String email);
}
