package ness.jmx.jolokia;

import org.junit.Assert;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.nesscomputing.config.ConfigModule;

public class TestJolokiaModule
{
    @Test
    public void testSpinup()
    {
        final Injector injector = Guice.createInjector(Stage.PRODUCTION,
                                                       ConfigModule.forTesting(),
                                                       new JolokiaModule());


        Assert.assertNotNull(injector.getInstance(JolokiaServlet.class));
    }
}
