package com.employ.employ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employ.employ.entity.EmployEntity;
import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<EmployEntity,Long>{  //kis par operatuin perform kar na hai (EmployEntity) & ganric (LONG)
    //-----custom meyhod
    List<EmployEntity> findByName(String name);  //find by name repository
}
