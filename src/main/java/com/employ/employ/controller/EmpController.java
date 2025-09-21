// package com.employ.employ.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RestController;

// import com.employ.employ.InputSchema.Employee;
// import com.employ.employ.services.EmployService;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestParam;




// @CrossOrigin("http://localhost:5173")
// @RestController
// @RequestMapping("/api/v1")
// public class EmpController {
//     // List<Employee> employees = new ArrayList<>();
    
//     //  EmployService employeesService = new EmployServiceImp(); //at every time new key word will create object and memort will take

//     //---dependency ingection--
//     @Autowired   //optional (if we write so we understand that they have a dav dapendies..)
//     EmployService employeesService;  //ma kisi ko aapna controle dana chaita hoo object bana ka (IOC container)
    
//     //---GetMapping to read data----
//   @GetMapping("employees")
//   public List<Employee> getAllEmployees() {
//     /* 
//     List<Employee> employees= new ArrayList<>();
//     Employee emp = new Employee();
//     emp.setName("shreyash");
//     employees.add(null);
//     employees.add(emp);
//       return employees;
//       */

//     //  return employees;

//     return employeesService.readAllEmployees();
//   }
  

//   //to create PostMapping

//   @PostMapping("create-employees")
//   public String createEmploy(@RequestBody Employee employee) {    //"@RequestBody" is to fetch data from body
//     //   employees.add(employee);
//      return employeesService.createEmployee(employee);
//   }
  
//   @GetMapping("employee-by-id/{id}")
//   public Employee getMethodName(@PathVariable Long id) {
//       return employeesService.readEmployeeById(id);
//   }
  

//   @DeleteMapping("employees/{id}")   //wher we write {they have path variable }
//   public String deleteEmployeeById(@PathVariable Long id){    //"@pathVariable" is used to fetch data from url
//     if(employeesService.deleteEmployee(id)){
//        return "Delete Successfully...";
//     }
//     return "Not found";
//   }  
 
//   @PutMapping("employee/{id}")
//   public String updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
//     return employeesService.updateEmployee(id, employee);
//   }
// }

package com.employ.employ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employ.employ.InputSchema.Employee;
import com.employ.employ.services.EmployService;
import com.employ.employ.utils.ApiException;
import com.employ.employ.utils.ApiResponse;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/api/v1")
public class EmpController {

    @Autowired
    private EmployService employeesService;

    // --- Get all employees
    @GetMapping("employees")
    public ResponseEntity<ApiResponse> getAllEmployees() {
        List<Employee> employees = employeesService.readAllEmployees();
        return ResponseEntity.ok(
            new ApiResponse(HttpStatus.OK.value(), "Employees fetched successfully", employees, false)
        );
    }

    // --- Create employee
    @PostMapping("create-employees")
    public ResponseEntity<ApiResponse> createEmploy(@RequestBody Employee employee) {
        String result = employeesService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponse(HttpStatus.CREATED.value(), result, null, false)
        );
    }

    // --- Get employee by ID
    @GetMapping("employee-by-id/{id}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable Long id) {
        Employee emp = employeesService.readEmployeeById(id);
        if (emp == null) {
            throw new ApiException("Employee not found", "NOT_FOUND", 404);
        }
        return ResponseEntity.ok(
            new ApiResponse(HttpStatus.OK.value(), "Employee fetched successfully", emp, false)
        );
    }

    // --- Delete employee by ID
    @DeleteMapping("employees/{id}")
    public ResponseEntity<ApiResponse> deleteEmployeeById(@PathVariable Long id) {
        boolean deleted = employeesService.deleteEmployee(id);
        if (!deleted) {
            throw new ApiException("Employee not found", "NOT_FOUND", 404);
        }
        return ResponseEntity.ok(
            new ApiResponse(HttpStatus.OK.value(), "Employee deleted successfully", null, false)
        );
    }

    // --- Update employee
    @PutMapping("employee/{id}")
    public ResponseEntity<ApiResponse> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        String result = employeesService.updateEmployee(id, employee);
        if ("Not found".equalsIgnoreCase(result)) {
            throw new ApiException("Employee not found", "NOT_FOUND", 404);
        }
        return ResponseEntity.ok(
            new ApiResponse(HttpStatus.OK.value(), result, null, false)
        );
    }
}
