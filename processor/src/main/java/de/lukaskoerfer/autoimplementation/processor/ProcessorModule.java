package de.lukaskoerfer.autoimplementation.processor;

import dagger.Module;
import dagger.Provides;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.inject.Singleton;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@Module
class ProcessorModule {

    @Provides
    @Singleton
    public static Elements provideElements(ProcessingEnvironment environment) {
        return environment.getElementUtils();
    }

    @Provides
    @Singleton
    public static Types provideTypes(ProcessingEnvironment environment) {
        return environment.getTypeUtils();
    }

    @Provides
    @Singleton
    public static Filer provideFiler(ProcessingEnvironment environment) {
        return environment.getFiler();
    }

}
