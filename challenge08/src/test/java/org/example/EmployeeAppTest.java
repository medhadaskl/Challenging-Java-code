package org.example;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// test case to check EmployeeApp Test
class EmployeeAppTest {

    @Test
    void testEmployeeData() {
        Employee emp = new Employee(2, "sweta", 58000.0, "IT");

        assertEquals(2, emp.getId());
        assertEquals("sweta", emp.getName());
        assertEquals(58000.0, emp.getSalary());
        assertEquals("IT", emp.getDepartment());
    }
}
