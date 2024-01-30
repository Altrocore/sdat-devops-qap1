package com.clover;

import java.util.Objects;

public class Employee {
    private int id;
    private String firstname;
    private String lastname;
    private int monthlySalary;

    public Employee(int id, String firstname, String lastname, int monthlySalary){
        this.id=id;
        this.firstname =firstname;
        this.lastname =lastname;
        this.monthlySalary = monthlySalary;
    }

    public int getID(){
        return this.id;
    }

    public String getFirstName(){
        return this.firstname;
    }

    public String getLastName(){
        return this.lastname;
    }

    public String getName(){
        return(
                this.firstname +" "+this.lastname
        );
    }

    public int getMonthlySalary(){
        return this.monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary){
        this.monthlySalary = monthlySalary;
    }

    public int getAnnualSalary() {
        return(
                this.monthlySalary *12
        );
    }

    public int raiseSalary(int percent) {
        percent = this.monthlySalary *percent;
        this.monthlySalary = this.monthlySalary +percent;
        return(
                this.monthlySalary
        );
    }

    public String toString(){
        return(
                "Employee[id="+getID()+",name="+getName()+",salary="+ getMonthlySalary()+"]"
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Employee employee = (Employee) o;

        return firstname.equals(employee.getFirstName()) && lastname.equals(employee.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}