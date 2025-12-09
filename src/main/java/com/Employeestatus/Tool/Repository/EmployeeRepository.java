package com.Employeestatus.Tool.Repository;

import com.Employeestatus.Tool.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE "
         + "(:country IS NULL OR LOWER(e.country) = LOWER(:country)) AND "
         + "(:state IS NULL OR LOWER(e.state) = LOWER(:state)) AND "
         + "(:department IS NULL OR LOWER(e.department) = LOWER(:department)) AND "
         + "(:status IS NULL OR LOWER(e.status) = LOWER(:status))")
    List<Employee> filterEmployees(
            @Param("country") String country,
            @Param("state") String state,
            @Param("department") String department,
            @Param("status") String status
    );
}
