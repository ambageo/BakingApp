package com.georgeampartzidis.bakie;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.georgeampartzidis.bakie.model.Step;

public class RecipeDetailsViewModel extends ViewModel {
    private static final String TAG= RecipeDetailsViewModel.class.getSimpleName();
private Step mStep;

public Step getStep(){
    return mStep;
}
public void setStep(Step step){
    this.mStep= step;
    Log.d(TAG, "Short description: " + step.getShortDescription() + "\n");
    Log.d(TAG, "Detailed description: " + step.getDetailedDescription() + "\n");
    Log.d(TAG, "videoUrl: " + step.getVideoUrl() + "\n");
    Log.d(TAG, "thumbnailUrl: " + step.getThumbnailUrl());

}
}
