// package com.employ.employ.serviceImp;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.BeanUtils;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.employ.employ.EmployApplication;
// import com.employ.employ.InputSchema.Employee;
// import com.employ.employ.entity.EmployEntity;
// import com.employ.employ.repository.EmployeeRepository;
// import com.employ.employ.services.EmployService;

// import ch.qos.logback.core.joran.util.beans.BeanUtil;

// @Service  
// public class EmployServiceImp implements EmployService {

//     private final EmployApplication employApplication;
//     @Autowired
//    private  EmployeeRepository employeeRepository;

//     EmployServiceImp(EmployApplication employApplication) {
//         this.employApplication = employApplication;
//     }  //use repository 
//     // List<Employee> employees = new ArrayList<>(); //we save local data
//     @Override
//     public String createEmployee(Employee empData) {
//         //yha par ike Employee type ka data aa raha hai par muja table ma add kar na hai  tho hama Entity ma convert kar na pada ga
//        EmployEntity employeeEntity = new EmployEntity();
//        BeanUtils.copyProperties(empData, employeeEntity);  // kha sa kha copy kar na hai data
//        employeeRepository.save(employeeEntity);  //to save in db
//         // employees.add(empData);   //local save
//         return "empData data have been created successfully...";
//     }

//     @Override
//     public List<Employee> readAllEmployees() {
//         List<EmployEntity> employeesList = employeeRepository.findAll();
//         List<Employee> employees = new ArrayList<>();
//         for (EmployEntity employEntity : employeesList) {
//             Employee emp  = new Employee();
//             emp.setId(employEntity.getId());
//             emp.setName(employEntity.getName());
//             emp.setNumber(employEntity.getNumber());
//             emp.setEmail(employEntity.getEmail());
//             emp.setCity(employEntity.getCity());
//             emp.setAddress(employEntity.getAddress());
//             employees.add(emp);
//         }
//         return employees;
//     //    return employees; //local save data
//     }

//     @Override
//     public Employee readEmployeeById(Long id){
//          EmployEntity emp = employeeRepository.findById(id).get();
//          Employee employee = new Employee();
//          BeanUtils.copyProperties(emp, employee);
//          return employee;
//     }

//     @Override
//     public boolean deleteEmployee(Long id) {
//         /* 
//          if (id >= 0 && id < employees.size()) {         //for local data 
//             employees.remove(id);
//             return true;
//         }
//         return false;
//         */
//       //revoistory delete work on  a entity so we need to first find entity 
//       EmployEntity emp = employeeRepository.findById(id).get();
//       employeeRepository.delete(emp);
//       return true;
//     }
//     @Override
//      public String updateEmployee(Long id, Employee updatedEmployee) {
//         /* 
//        for (Employee emp : employees) {
//             if (emp.getId().equals(id)) {
//                 if (updatedEmployee.getName() != null) {
//                     emp.setName(updatedEmployee.getName());
//                 }
//                 if (updatedEmployee.getNumber() != null) {
//                     emp.setNumber(updatedEmployee.getNumber());
//                 }
//                 if (updatedEmployee.getEmail() != null) {
//                     emp.setEmail(updatedEmployee.getEmail());
//                 }
//                 return true;
//             }
//         }
//         return false;
//         */
//         EmployEntity existingEmployee = employeeRepository.findById(id).get();
   
//             existingEmployee.setEmail(updatedEmployee.getEmail());
//         existingEmployee.setName(updatedEmployee.getName());
//         existingEmployee.setNumber(updatedEmployee.getNumber());
//         existingEmployee.setAddress(updatedEmployee.getAddress());
//         existingEmployee.setCity(updatedEmployee.getCity());
//         employeeRepository.save(existingEmployee);
//         return "Successfully update";
//     }
// }


package com.employ.employ.serviceImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employ.employ.InputSchema.Employee;
import com.employ.employ.entity.EmployEntity;
import com.employ.employ.repository.EmployeeRepository;
import com.employ.employ.services.EmployService;
import com.employ.employ.utils.ApiException;

@Service  
public class EmployServiceImp implements EmployService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public String createEmployee(Employee empData) {
        EmployEntity employeeEntity = new EmployEntity();
        BeanUtils.copyProperties(empData, employeeEntity);
        employeeRepository.save(employeeEntity);
        return "Employee data has been created successfully...";
    }

    @Override
    public List<Employee> readAllEmployees() {
        List<EmployEntity> employeesList = employeeRepository.findAll();
        List<Employee> employees = new ArrayList<>();
        for (EmployEntity employEntity : employeesList) {
            Employee emp = new Employee();
            BeanUtils.copyProperties(employEntity, emp);
            employees.add(emp);
        }
        return employees;
    }

    @Override
    public Employee readEmployeeById(Long id) {
        Optional<EmployEntity> empOpt = employeeRepository.findById(id);
        if (empOpt.isEmpty()) {
            throw new ApiException("Employee not found", "NOT_FOUND", 404);
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(empOpt.get(), employee);
        return employee;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<EmployEntity> empOpt = employeeRepository.findById(id);
        if (empOpt.isEmpty()) {
            throw new ApiException("Employee not found", "NOT_FOUND", 404);
        }
        employeeRepository.delete(empOpt.get());
        return true;
    }

    @Override
    public String updateEmployee(Long id, Employee updatedEmployee) {
        Optional<EmployEntity> empOpt = employeeRepository.findById(id);
        if (empOpt.isEmpty()) {
            throw new ApiException("Employee not found", "NOT_FOUND", 404);
        }

        EmployEntity existingEmployee = empOpt.get();
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setNumber(updatedEmployee.getNumber());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setCity(updatedEmployee.getCity());
        employeeRepository.save(existingEmployee);

        return "Employee updated successfully";
    }
}
