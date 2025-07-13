// ICarLoanService.aidl
package com.ghoul.lab10;

interface ICarLoanService {
    double calculateEMI(double principal, double annualRate, int months, double downPayment);
}
