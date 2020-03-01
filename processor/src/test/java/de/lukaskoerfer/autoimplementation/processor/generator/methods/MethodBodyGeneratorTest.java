package de.lukaskoerfer.autoimplementation.processor.generator.methods;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class MethodBodyGeneratorTest {

    @Test
    public void generatesReturnBlockIfReturnActionIsSelected() {
        ImplementationStrategy actionSelector = mock(ImplementationStrategy.class);
        ReturnImplementationGenerator returnActionGenerator = mock(ReturnImplementationGenerator.class);

        MethodBodyGenerator generator = new MethodBodyGenerator(actionSelector, returnActionGenerator);


    }

}
