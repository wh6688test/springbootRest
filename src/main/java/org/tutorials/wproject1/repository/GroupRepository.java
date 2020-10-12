package org.tutorials.wproject1.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tutorials.wproject1.model.Group;

@Repository
public interface  GroupRepository extends CrudRepository<Group, Long> {



}
