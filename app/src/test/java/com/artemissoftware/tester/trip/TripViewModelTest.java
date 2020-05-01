package com.artemissoftware.tester.trip;

import android.app.Application;

import androidx.databinding.ObservableField;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

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

}