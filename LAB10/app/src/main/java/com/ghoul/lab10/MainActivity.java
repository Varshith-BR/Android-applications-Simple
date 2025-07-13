package com.ghoul.lab10;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ICarLoanService carLoanService;
    EditText p, r, n, d;
    TextView resultText;
    Button calculateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        p = findViewById(R.id.editPrincipal);
        r = findViewById(R.id.editInterestRate);
        n = findViewById(R.id.editTenure);
        d = findViewById(R.id.editDownPayment);
        calculateBtn = findViewById(R.id.btnCalculate);
        resultText = findViewById(R.id.textResult);

        calculateBtn.setOnClickListener(v -> {
            try {
                double emi = carLoanService.calculateEMI(
                        Double.parseDouble(p.getText().toString()),
                        Double.parseDouble(r.getText().toString()),
                        Integer.parseInt(n.getText().toString()),
                        Double.parseDouble(d.getText().toString())
                );
                resultText.setText("EMI: Rs " + emi);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Intent intent = new Intent(this, CarLoanService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            carLoanService = ICarLoanService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            carLoanService = null;
        }
    };
}