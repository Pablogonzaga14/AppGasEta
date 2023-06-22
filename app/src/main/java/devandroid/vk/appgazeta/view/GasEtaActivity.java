package devandroid.vk.appgazeta.view;

import static android.app.ProgressDialog.show;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import devandroid.vk.appgazeta.R;
import devandroid.vk.appgazeta.apoio.UtilGasEta;
import devandroid.vk.appgazeta.controller.CombustivelController;
import devandroid.vk.appgazeta.model.Combustivel;

public class GasEtaActivity extends AppCompatActivity {

    CombustivelController controller;

    Combustivel combustivelGasolina;
    Combustivel combustivelEtanol;


    EditText txtGasolina;
    EditText txtEtanol;

    TextView resultado;

    Button btnCalcular;
    Button btnLimpar;
    Button btnSalvar;
    Button btnFinalizar;

    double precoGasolina;
    double precoEtanol;
    String recomendacao;

    List<Combustivel> dados;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new CombustivelController(GasEtaActivity.this);

        dados = controller.getListadeDados();

        setContentView(R.layout.activity_gaseta);

        Combustivel objAlteracao = dados.get(1);
        objAlteracao.setNomeDoCombustivel("**GASOLINA**");
        objAlteracao.setPrecoDoCombustivel(5.97);
        objAlteracao.setRecomendacao("**ABASTECER COM GASOLINA**");
        //controller.alterar(objAlteracao);

        controller.deletar(6);

        txtGasolina = findViewById(R.id.txtGasolina);
        txtEtanol = findViewById(R.id.txtEtanol);

        resultado = findViewById(R.id.resultado);

        btnCalcular = findViewById(R.id.btnCalcular);
        btnLimpar = findViewById(R.id.btnLimpar);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean  isDadosOk = true;

                if(TextUtils.isEmpty(txtGasolina.getText())){
                    txtGasolina.setError("* Obrigatorio");
                    txtGasolina.requestFocus();
                    isDadosOk = false;
                }

                if(TextUtils.isEmpty(txtEtanol.getText())){
                    txtEtanol.setError("* Obrigatorio");
                    txtEtanol.requestFocus();
                    isDadosOk = false;
                }
                if(isDadosOk ){

                    precoGasolina = Double.parseDouble(txtGasolina.getText().toString());
                    precoEtanol = Double.parseDouble(txtEtanol.getText().toString());

                    recomendacao = UtilGasEta.calucularMelhorOpcao(precoGasolina,precoEtanol);

                    resultado.setText(recomendacao);

                    btnSalvar.setEnabled(true);

                }else{
                    Toast.makeText(GasEtaActivity.this, "Ensira os dados Obritatorios", Toast.LENGTH_LONG ).show();
                    btnSalvar.setEnabled(false);
                }

            }
        });
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtEtanol.setText("");
                txtGasolina.setText("");
                resultado.setText("RESULTADO");
                btnSalvar.setEnabled(false);

                controller.limpar();
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: EditeText inputType

                combustivelGasolina = new Combustivel();
                combustivelEtanol = new Combustivel();

                combustivelGasolina.setNomeDoCombustivel("Gasolina");
                combustivelGasolina.setPrecoDoCombustivel(precoGasolina);

                combustivelEtanol.setNomeDoCombustivel("Etanol");
                combustivelEtanol.setPrecoDoCombustivel(precoEtanol);

                combustivelGasolina.setRecomendacao(UtilGasEta.calucularMelhorOpcao(precoGasolina,precoEtanol));
                combustivelEtanol.setRecomendacao(UtilGasEta.calucularMelhorOpcao(precoGasolina,precoEtanol));

                controller.salvar(combustivelGasolina);
                controller.salvar(combustivelEtanol);
                Toast.makeText(GasEtaActivity.this, "Salvo Com sucesso", Toast.LENGTH_LONG ).show();



            }
        });
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GasEtaActivity.this, "Faça uma boa escolha", Toast.LENGTH_LONG ).show();
                finish();
            }
        });

        Toast.makeText(GasEtaActivity.this,
                UtilGasEta.calucularMelhorOpcao(5.12,4.39),
                Toast.LENGTH_LONG).show();
    }
}
