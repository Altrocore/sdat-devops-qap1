package com.clover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

    @Test
    public void testGetAnnualSalary() {
        Employee employeeUnderTest = new Employee(1, "Jamie", "Cornick", 100);

        Assertions.assertTrue(employeeUnderTest.getAnnualSalary() == (100 * 12));
    }

    @Test
    public void testEmployeesAreEqual() {
        Employee employeeUnderTest1 = new Employee(1, "Jamie", "Cornick", 100);
        Employee employeeUnderTest2 = new Employee(2, "Jamie", "Cornick", 100);

        Assertions.assertEquals(employeeUnderTest1, employeeUnderTest2);
    }

    @Test
    public void testRaiseSalaryBy20Percent() {
        Employee employeeUnderTest1 = new Employee(1, "Jamie", "Cornick", 4500);
        Employee employeeUnderTest2 = new Employee(2, "Jamie", "Cornick", 5000);

        employeeUnderTest1.raiseSalary(20);
        employeeUnderTest2.raiseSalary(20);

        Assertions.assertFalse(employeeUnderTest1.getMonthlySalary() == (100 * 1.20));
        Assertions.assertFalse(employeeUnderTest2.getMonthlySalary() == (100 * 1.20));
    }
}
