package com.hrm.aynu_qhj.employee.handler;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hrm.aynu_qhj.beans.DeptInf;
import com.hrm.aynu_qhj.beans.EmployeeInf;
import com.hrm.aynu_qhj.beans.JobInf;
import com.hrm.aynu_qhj.employee.dao.EmployeeInfDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeHandler {
    @Autowired
    private EmployeeInfDao employeeInfDao;

    @RequestMapping("/allEmployee")
    public String allEmployee(Integer job_id, Integer dept_id, Model model, @RequestParam(defaultValue = "1") int pageNum, EmployeeInf emp) {
        System.out.println(job_id);
        System.out.println(dept_id);
        if (job_id != null) {
            JobInf temp = new JobInf();
            temp.setId(job_id);
            emp.setJob(temp);
        }
        if (dept_id != null) {
            DeptInf temp = new DeptInf();
            temp.setId(dept_id);
            emp.setDept(temp);
        }
        PageHelper.startPage(pageNum, 3);
        List<EmployeeInf> employeeInfList = employeeInfDao.selectAll(emp);
        System.out.println(employeeInfList);
        PageInfo<EmployeeInf> page = new PageInfo<EmployeeInf>(employeeInfList);
        //查询所有job
        List<JobInf> jobInfList = employeeInfDao.selectAllJob();
        //查询所有dept
        List<DeptInf> deptInfList = employeeInfDao.selectAllDept();

        model.addAttribute("empList", employeeInfList);
        model.addAttribute("jobList", jobInfList);
        model.addAttribute("deptInfList", deptInfList);
        model.addAttribute("page", page);
        return "html/employee/employee";
    }

    /**
     * @param id 传过来的emp的id
     * @return
     */
    @GetMapping("/updateEmployee")
    public String updateEmployee(int id, Model model) {
        EmployeeInf emp = employeeInfDao.selectById(id);
        //查询所有job
        List<JobInf> jobInfList = employeeInfDao.selectAllJob();
        //查询所有dept
        List<DeptInf> deptInfList = employeeInfDao.selectAllDept();
        model.addAttribute("jobList", jobInfList);
        model.addAttribute("deptInfList", deptInfList);
        model.addAttribute("employee", emp);
        return "html/employee/showUpdateEmployee";
    }

    @PostMapping("/updateEmployee")
    public String updateEmployee(EmployeeInf emp) {
        int row = employeeInfDao.update(emp);
        System.out.println(emp);

        return "error";
    }

    @GetMapping("/showAddEmployee")
    public String showAddEmployee(Model model) {
        //job
        //查询所有job
        List<JobInf> jobInfList = employeeInfDao.selectAllJob();
        //查询所有dept
        List<DeptInf> deptInfList = employeeInfDao.selectAllDept();
        model.addAttribute("jobList", jobInfList);
        model.addAttribute("deptInfList", deptInfList);
        return "html/employee/showAddEmployee";
    }

    @PostMapping("addEmployee")
    public String addEmployee(EmployeeInf emp) {
        System.out.println(emp);
        employeeInfDao.insert(emp);
        return "redirect:employee/allEmployee";
    }
}
