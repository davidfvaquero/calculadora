package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final int[] botonesNumeros = {R.id.Btn0, R.id.Btn1, R.id.Btn2, R.id.Btn3, R.id.Btn4, R.id.Btn5, R.id.Btn6, R.id.Btn7, R.id.Btn8, R.id.Btn9};
    private final int[] botonesOperaciones = {R.id.BtnClear, R.id.BtnDiv, R.id.BtnMas, R.id.BtnMenos, R.id.BtnMult, R.id.BtnResultado};

    private TextView txtResultado, txtCuentas;
    private String numero1 = "";
    private String numero2 = "";
    private String operacion = "";
    private double resultado = 0;

    /**
     * Método que se llama al lanzar la actividad.
     * Establece los ClickListener en cada botón.
     *
     * @param savedInstanceState El estado previamente guardado de la actividad (si existe).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        txtResultado = findViewById(R.id.txtResultado);
        txtCuentas = findViewById(R.id.txtCuenta);

        for (int idBoton : botonesNumeros) {
            findViewById(idBoton).setOnClickListener(this);
        }
        for (int idBotonOp : botonesOperaciones) {
            findViewById(idBotonOp).setOnClickListener(this);
        }

        txtResultado.setText("0");
        txtCuentas.setText("");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    /**
     * Método que controla los clicks de los botones.
     * Dependiendo del botón pulsado, agrega números, limpia la pantalla, asigna una operación o calcula el resultado.
     *
     * @param view La vista que fue pulsada.
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.BtnResultado) {
            procesarResultado();
        } else if (id == R.id.BtnClear) {
            limpiarPantallas();
        } else if (esOperacion(id)) {
            asignarOperacion(id);
        } else {
            agregarNumero(id);
        }
    }

    /**
     * Comprueba si el boton pulsado es de una operacion.
     *
     * @param id El ID del botón.
     * @return true si el botón es una operación (+, -, *, /), false si no lo es.
     */
    private boolean esOperacion(int id) {
        return id == R.id.BtnMas || id == R.id.BtnMenos || id == R.id.BtnMult || id == R.id.BtnDiv;
    }

    /**
     * Agrega el número seleccionado al cálculo. Si aún no se ha seleccionado ninguna operación,
     * el número se añade al primer operando (numero1). Si ya se ha seleccionado una operación,
     * el número se añade al segundo operando (numero2).
     *
     * @param id El ID del botón de número pulsado.
     */
    private void agregarNumero(int id) {
        String numero = obtenerNumero(id);
        if (operacion.isEmpty()) {
            numero1 += numero;
            txtCuentas.setText(numero1);
        } else {
            numero2 += numero;
            txtCuentas.setText(numero2);
        }
    }

    /**
     * Devuelve el número correspondiente al botón pulsado.
     *
     * @param id El ID del botón pulsado.
     * @return El número como un String.
     */
    private String obtenerNumero(int id) {
        if (id == R.id.Btn0) return "0";
        else if (id == R.id.Btn1) return "1";
        else if (id == R.id.Btn2) return "2";
        else if (id == R.id.Btn3) return "3";
        else if (id == R.id.Btn4) return "4";
        else if (id == R.id.Btn5) return "5";
        else if (id == R.id.Btn6) return "6";
        else if (id == R.id.Btn7) return "7";
        else if (id == R.id.Btn8) return "8";
        else if (id == R.id.Btn9) return "9";
        else return "";
    }

    /**
     * Asigna la operación seleccionada (+, -, *, /) si ya se ha ingresado el primer número.
     *
     * @param id El ID del botón de operación pulsado.
     */
    private void asignarOperacion(int id) {
        if (!numero1.isEmpty()) {
            if (id == R.id.BtnMas) {
                operacion = "+";
            } else if (id == R.id.BtnMenos) {
                operacion = "-";
            } else if (id == R.id.BtnMult) {
                operacion = "*";
            } else if (id == R.id.BtnDiv) {
                operacion = "/";
            }
        }
    }

    /**
     * Calcula el resultado de la operación entre los dos números ingresados.
     * El resultado se muestra en la pantalla y se almacena para realizar más operaciones.
     */
    private void procesarResultado() {
        if (!numero1.isEmpty() && !numero2.isEmpty()) {
            double valor1 = Double.parseDouble(numero1);
            double valor2 = Double.parseDouble(numero2);
            switch (operacion) {
                case "+":
                    resultado = valor1 + valor2;
                    break;
                case "-":
                    resultado = valor1 - valor2;
                    break;
                case "*":
                    resultado = valor1 * valor2;
                    break;
                case "/":
                    resultado = valor1 / valor2;
                    break;
            }
            txtResultado.setText(String.valueOf(resultado));
            numero1 = String.valueOf(resultado);
            numero2 = "";
            operacion = "";
        }
    }

    /**
     * Limpia la pantalla y reinicia todos los valores.
     */
    private void limpiarPantallas() {
        numero1 = "";
        numero2 = "";
        operacion = "";
        resultado = 0;
        txtResultado.setText("0");
        txtCuentas.setText("");
    }
}
