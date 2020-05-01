package com.artemissoftware.tester.trip;

import android.app.Application;

import androidx.databinding.ObservableField;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class TripViewModelTest {


    //system under test
    private TripViewModel viewModel;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Application app = Mockito.mock(Application.class);

        viewModel = new TripViewModel(app);
    }


    /**
     * Verifica se um calculo é valido
     */
    @Test
    public void inserirDados_validar_ReturnTrue() {

        // Arrange

        ObservableField<String> distancia = viewModel.distancia;
        distancia.set("100");

        ObservableField<String> preco = viewModel.preco;
        preco.set("30");

        ObservableField<String> autonomia = viewModel.autonomia;
        autonomia.set("15");

        // Act


        // Assert
        assertTrue(viewModel.isValid());

    }


    @Test
    public void inserirDados_validar_ReturnFalse() {


        // Arrange

        ObservableField<String> distancia = viewModel.distancia;
        distancia.set("");

        ObservableField<String> preco = viewModel.preco;
        preco.set("");

        ObservableField<String> autonomia = viewModel.autonomia;
        autonomia.set("");

        // Act


        // Assert
        assertFalse(viewModel.isValid());

    }


    @Test
    public void inserirDados_validarHandleCalculation() {

        // Arrange

        ObservableField<String> distancia = viewModel.distancia;
        distancia.set("30");

        ObservableField<String> preco = viewModel.preco;
        preco.set("15");

        ObservableField<String> autonomia = viewModel.autonomia;
        autonomia.set("250");

        ObservableField<String> resultado = viewModel.resultado;

        viewModel.handleCalculate();

        assertEquals("Total: R$ 1.8", resultado.get());

    }





    @Test
    public void inserirDados_letras_validarHandleCalculation() {

        ObservableField<String> distancia = viewModel.distancia;
        distancia.set("asadsaddsasdsdadssd");

        ObservableField<String> preco = viewModel.preco;
        preco.set("15");

        ObservableField<String> autonomia = viewModel.autonomia;
        autonomia.set("250");

        ObservableField<String> resultado = viewModel.resultado;

        boolean numberFormatException = false;

        try {
            viewModel.handleCalculate();
            numberFormatException = false;
        } catch (Exception n) {
            numberFormatException = true;
        }

        assertTrue(numberFormatException);
    }




    @Test
    public void  espiarhandleCalculateButton_verificarHandleCalculate() {

        // Test the handleCalculateButtonClick call HandleCalculate

        ObservableField<String> distancia = viewModel.distancia;
        distancia.set("30");

        ObservableField<String> preco = viewModel.preco;
        preco.set("15");

        ObservableField<String> autonomia = viewModel.autonomia;
        autonomia.set("250");


        TripViewModel spy = spy(viewModel);

        spy.handleCalculateButtonClick();

        verify(spy, Mockito.times(1)).handleCalculate();

    }




}