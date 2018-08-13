package com.georgeampartzidis.bakie;

import android.arch.lifecycle.ViewModel;

import com.georgeampartzidis.bakie.model.Step;

public class RecipeDetailsViewModel extends ViewModel {
private Step mStep;

public Step getStep(){
    return mStep;
}
public void setStep(Step step){
    this.mStep= step;
}
}
