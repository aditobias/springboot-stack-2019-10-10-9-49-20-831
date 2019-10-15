package com.tw.apistackbase.controller;

import com.tw.apistackbase.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by jxzhong on 18/08/2017.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeResource {
    private List<Employee> employeeList = new ArrayList<>();
    private final Logger log = Logger.getLogger(this.getClass().getName());

    @GetMapping(path = "/allEmployee", produces = {"application/json"})
    public List<Employee> getEmployee() {

        return employeeList;
    }

    @GetMapping(path = "/searchEmployee/{id}", produces = {"application/json"})
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeList.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping(path = "/addEmployee", produces = {"application/json"})
    public ResponseEntity<String> addEmployee(@RequestBody List<Employee> employeeListCreate) {

        employeeList.addAll(employeeListCreate);

        return ResponseEntity.ok("Added new employees!");
    }

    @PutMapping(path = "/changeEmployeeData/{id}", produces = {"application/json"})
    public ResponseEntity<String> changeEmployee(@PathVariable Integer id, @RequestBody Employee employee) {
        Employee oldEmployee = employeeList.stream()
                .filter(employeeInList -> employeeInList.getId() == id)
                .findFirst().get();

        employeeList.set(employeeList.indexOf(oldEmployee), employee);

        return ResponseEntity.ok("Employee updated!");
    }

    @DeleteMapping(path = "/deleteEmployee", produces = {"application/json"})
    public ResponseEntity<String> removeEmployee(@PathVariable Integer id) {

        Employee findEmployee = employeeList.stream()
                .filter(employeeInList -> employeeInList.getId() == id)
                .findFirst()
                .orElse(null);

        if(findEmployee != null){
            employeeList.remove(findEmployee);
            return ResponseEntity.ok("Employee deleted!");
        }

        return ResponseEntity.ok("Employee not find");
    }


}
