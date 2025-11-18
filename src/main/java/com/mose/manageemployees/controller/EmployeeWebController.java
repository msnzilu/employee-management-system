package com.mose.manageemployees.controller;

import com.mose.manageemployees.model.Employee;
import com.mose.manageemployees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller  // Note: @Controller, not @RestController
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeWebController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Show list of all employees
    @GetMapping
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employee-list";  // Returns employee-list.html template
    }

    // Show form to create new employee
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee-form";  // Returns employee-form.html template
    }

    // Handle form submission to create employee
    @PostMapping
    public String createEmployee(@Valid @ModelAttribute Employee employee,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "employee-form";
        }
        employeeService.createEmployee(employee);
        return "redirect:/employees";  // Redirect to list page
    }

    // Show form to edit employee
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        model.addAttribute("employee", employee);
        return "employee-form";
    }

    // Handle form submission to update employee
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute Employee employee,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "employee-form";
        }
        employeeService.updateEmployee(id, employee);
        return "redirect:/employees";
    }

    // Delete employee
    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
}
