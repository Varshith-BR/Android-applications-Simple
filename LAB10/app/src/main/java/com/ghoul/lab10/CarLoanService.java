package com.ghoul.lab10;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CarLoanService extends Service {
    private final ICarLoanService.Stub binder = new ICarLoanService.Stub() {
        @Override
        public double calculateEMI(double principal, double annualRate, int months, double downPayment) {
            double P = principal - downPayment;
            double r = annualRate / (12 * 100);
            double n = months;
            double emi = (P * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
            return emi;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
