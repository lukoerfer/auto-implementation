package de.lukaskoerfer.autoimplementation.processor;

import dagger.BindsInstance;
import dagger.Component;

import javax.annotation.processing.ProcessingEnvironment;

@Component(modules = ProcessorModule.class)
interface ProcessorComponent {

    void setup(AutoImplementationProcessor processor);

    @Component.Builder
    interface Builder {
        @BindsInstance Builder processingEnvironment(ProcessingEnvironment processingEnvironment);
        ProcessorComponent build();
    }

}
