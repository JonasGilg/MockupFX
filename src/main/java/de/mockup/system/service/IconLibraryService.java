package de.mockup.system.service;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.IconModel;

import java.util.List;

public interface IconLibraryService {


    List<IconModel> getIcons() throws SystemException;

    void storeIcon(String path, IconModel model) throws SystemException;
}
