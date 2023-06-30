package fr.luigi.calculatrice;

import androidx.appcompat.app.AppCompatActivity;
import fr.luigi.calculatrice.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public enum Ops {PLUS, MOINS, FOIS, DIV}

    private TextView screen;
    private TextView screen2;
    private TextView operatorScreen;
    private int op1 = 0;
    private int op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = (TextView) findViewById(R.id.screen);
        screen2 = (TextView) findViewById(R.id.screen2);
        operatorScreen = (TextView) findViewById(R.id.operatorScreen);

        Button btnEgal = (Button)findViewById(R.id.btnEgal);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                compute();
            }
        });
    }

    public void compute() {
        if (!isOp1) {
            switch (operator) {
                case PLUS:
                    op1 = op1 + op2;
                    break;
                case MOINS:
                    op1 = op1 - op2;
                    break;
                case FOIS:
                    op1 = op1 * op2;
                    break;
                case DIV:
                    op1 = op1 / op2;
                    break;
                default:
                    return; // do nothing if no operator
            }
            op2 = 0;
            isOp1 = true;
            updateDisplay();
            operatorScreen.setText("");
            screen2.setText("");
        }
    }
    public void setOperator(View v) {
        if (v.getId() == R.id.btnPlus) {
            operator = Ops.PLUS;
            operatorScreen.setText("+");
        } else if (v.getId() == R.id.btnMoins) {
            operator = Ops.MOINS;
            operatorScreen.setText("-");
        } else if (v.getId() == R.id.btnMulti) {
            operator = Ops.FOIS;
            operatorScreen.setText("*");
        } else if (v.getId() == R.id.btnDiviser) {
            operator = Ops.DIV;
            operatorScreen.setText("/");
        } else {
            Toast.makeText(this, "Opérateur non reconnu", Toast.LENGTH_LONG).show();
            return;
        }
        isOp1 = false;
        updateDisplay();
    }
    public void addNumber(View v) {
        try {
            int val = Integer.parseInt(((Button)v).getText().toString());
            if (isOp1) {
                op1 = op1 * 10 + val;
                updateDisplay();
            } else {
                op2 = op2 * 10 + val;
                updateDisplay();
            }
        } catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée", Toast.LENGTH_LONG);
        }
    }
    private void updateDisplay() {
        int v = op1;
        screen.setText(String.format("%9d",v));
        if(!isOp1) {
            v=op2;
            screen2.setText(String.format("%9d",v));
        }

    }
    public void resetScreen(View v) {
        op1 = 0;
        op2 = 0;
        isOp1 = true;
        operator = null;
        operatorScreen.setText("");
        screen.setText("0");
        screen2.setText("");
    }
}
