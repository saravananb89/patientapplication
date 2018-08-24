package com.zeiss.patient.client.gui.generate;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Provider;

public class DataGenerationService extends Service<DataGenerationOutput> {

    private DataGenerationInputParameters input;

    @Inject
    private Provider<DataGenerationOutputTask> dataGenerationOutputTaskProvider;

    public void setInput(DataGenerationInputParameters input) {
        this.input = input;
    }

    @Override
    protected Task<DataGenerationOutput> createTask() {
        DataGenerationOutputTask dataGenerationOutputTask = dataGenerationOutputTaskProvider.get();
        dataGenerationOutputTask.setInput(input);
        return dataGenerationOutputTask;
    }

}
