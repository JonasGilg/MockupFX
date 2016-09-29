package de.mockup.system;

import org.junit.Assert;
import org.junit.Test;

public class BundleTest {

    @Test
    public void registerServiceTest() {
        Assert.assertNull(Bundle.getService(String.class));
        Bundle.registerService(String.class, "");
        Assert.assertNotNull(Bundle.getService(String.class));
    }
}
