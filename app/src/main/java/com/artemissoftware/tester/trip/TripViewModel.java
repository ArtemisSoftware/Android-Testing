package com.artemissoftware.tester.trip;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TripViewModel extends AndroidViewModel {

    public ObservableField<String> distancia = new ObservableField();
    public ObservableField<String> preco = new ObservableField();
    public ObservableField<String> autonomia = new ObservableField();



    public ObservableField<String> resultado = new ObservableField();
    public MutableLiveData<String> errorInformation = new MutableLiveData<String>();


    public TripViewModel(Application application){
        super(application);

    }



    public void handleCalculateButtonClick() {
        handleCalculate();
    }


    /**
     * Função responsável por realizar o cálculo dos gastos com a viagem
     * Baseado na distância percorrida * preço médio do combustível / pela autonomia do veículo
     */
    public void handleCalculate() {

        if(isValid()) {

            try {

                // (distancia * preço) / autonomia
                float distance = Float.parseFloat(distancia.get().toString());
                float price = Float.parseFloat(preco.get().toString());
                float autonomy = Float.parseFloat(autonomia.get().toString());

                // Realiza o cálculo ((distancia * preço) / autonomia)

                Float result = ((distance * price) / autonomy);

                // Seta o valor calculado na tela

                resultado.set("Total: R$ " + result);

            } catch (NumberFormatException | NullPointerException nfe) {

                // Caso ocorra erro de conversão numérica, solicita ao usuário para preencher com valores válidos

                // Toast.makeText(getApplication(), "Por Favor Informe valores válidos", Toast.LENGTH_LONG).show()

                errorInformation.setValue("Por Favor Informe valores válidos");
            }

        } else {

            // Caso não tenha preenchido todos os campos, solicita o preenchimento

            // Toast.makeText(getApplication(), "Por Favor Informe valores válidos", Toast.LENGTH_LONG).show()

            errorInformation.setValue("Por Favor Informe valores válidos");

        }


    }


    /**
     * Verifica se todos os campos foram preenchidos
     */
    public Boolean isValid() {

        return distancia.get() != ""
                && preco.get() != ""
                && autonomia.get() != ""
                && autonomia.get() != "0";
    }


    public LiveData<String> getError(){
        return errorInformation;
    }


}
