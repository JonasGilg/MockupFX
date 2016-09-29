package de.mockup.system.model.components.buttons;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.components.containers.ViewModel;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ViewModelTest {
    @Test
    public void validateJSON() {
        ViewModel vm = new ViewModel();

        vm.setDirty(true);
        vm.setTitle("testtile");
        vm.setId(1);
        vm.setHeight(100);
        vm.setWidth(100);
        vm.setProperty("testkey", new Integer(7));


        JSONObject obj = null;
        try {
            obj = vm.toConfig();
        } catch (SystemException e) {
            e.printStackTrace();
            fail("System Exception!");
        }
        ViewModel res = new ViewModel();
        try {
            res.fromConfig(obj);
        } catch (SystemException e) {
            e.printStackTrace();
            fail("System Exception!");
        }
        Assert.assertEquals(res.getTitle(), vm.getTitle());
        Assert.assertEquals(res.isDirty(), true);
        Assert.assertEquals(res.getId().longValue(), 1);
        Assert.assertEquals(res.getHeight().longValue(), 100);
        Assert.assertEquals(res.getWidth().longValue(), 100);
        Assert.assertEquals(res.getPropertyMap().containsKey("testkey"), true);
        Assert.assertEquals(res.getProperty("testkey"), 7 );
        Assert.assertEquals(res.getStorageName(), vm.getStorageName());
        res.removeProperty("testkey");
        Assert.assertEquals(res.getPropertyMap().containsKey("testkey"), false);
    }


}
