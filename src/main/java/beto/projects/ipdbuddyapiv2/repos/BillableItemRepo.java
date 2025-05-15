package beto.projects.ipdbuddyapiv2.repos;

import beto.projects.ipdbuddyapiv2.entities.BillableItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillableItemRepo extends JpaRepository<BillableItem, Integer> {

}
