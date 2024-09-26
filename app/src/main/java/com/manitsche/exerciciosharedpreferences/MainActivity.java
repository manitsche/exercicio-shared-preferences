package com.manitsche.exerciciosharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText editTextPeso, editTextAltura;
    Button buttonCalcularIMC;
    TextView textViewResultado;
    SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "IMC_PREFS";
    private static final String PESO_KEY = "PESO";
    private static final String ALTURA_KEY = "ALTURA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPeso = findViewById(R.id.editTextPeso);
        editTextAltura = findViewById(R.id.editTextAltura);
        buttonCalcularIMC = findViewById(R.id.buttonCalcularIMC);
        textViewResultado = findViewById(R.id.textViewResultado);

        // Recupera os valores armazenados
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        carregarDados();

        // Define a ação do botão de cálculo
        buttonCalcularIMC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularIMC();
            }
        });
    }

    // Método para calcular o IMC
    private void calcularIMC() {
        String pesoStr = editTextPeso.getText().toString();
        String alturaStr = editTextAltura.getText().toString();

        if (!pesoStr.isEmpty() && !alturaStr.isEmpty()) {
            // Converte os valores para números
            float peso = Float.parseFloat(pesoStr);
            float altura = Float.parseFloat(alturaStr);

            // Calcula o IMC
            float imc = peso / (altura * altura);

            // Exibe o resultado
            textViewResultado.setText(String.format("Resultado: Seu IMC é: %.2f", imc));

            // Armazena os dados usando SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat(PESO_KEY, peso);
            editor.putFloat(ALTURA_KEY, altura);
            editor.apply();

            // Exibe uma mensagem de confirmação
            Toast.makeText(MainActivity.this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            // Caso os campos estejam vazios, exibe uma mensagem de erro
            Toast.makeText(MainActivity.this, "Por favor, insira o peso e a altura.", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para carregar os dados armazenados
    private void carregarDados() {
        float peso = sharedPreferences.getFloat(PESO_KEY, 0);
        float altura = sharedPreferences.getFloat(ALTURA_KEY, 0);

        if (peso != 0 && altura != 0) {
            editTextPeso.setText(String.valueOf(peso));
            editTextAltura.setText(String.valueOf(altura));
        }
    }
}