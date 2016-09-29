package de.mockup.system.service;


import de.mockup.system.Activator;
import de.mockup.system.model.IconModel;
import de.mockup.system.service.impl.IconLibraryServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IconLibraryServiceTest {

    @Before
    public void setUp(){
        new Activator().start();
    }

    private IconLibraryService iconLibraryService = new IconLibraryServiceImpl();

    @Test
    public void testIconLibrary() throws Throwable {
        List<IconModel> icons = iconLibraryService.getIcons();
        System.out.println("FOUND: " + icons.size());
        for(IconModel model : icons){
            System.out.println(model.getName());
            System.out.println(model.getPath());
        }
    }
}
