package de.mockup.system.service.impl;

import de.mockup.system.exceptions.SystemErrorCodes;
import de.mockup.system.exceptions.SystemException;
import de.mockup.system.json.JSONObject;
import de.mockup.system.model.BaseModel;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * BaseClass for PersistenceServices
 */
public class PersistenceServiceBaseImpl {

	protected void loadModel(File file, BaseModel model) throws SystemException {
		if (file != null && file.exists()) {
			try {
				JSONObject config = new JSONObject(FileUtils.readFileToString(file));
				model.fromConfig(config);
			} catch (IOException e) {
				throw new SystemException(SystemErrorCodes.ENTITY_NOT_FOUND);
			}
		}
	}

	protected void saveModel(File file, BaseModel model) throws SystemException {
		try {
			if (!file.exists()) {
				if (!file.createNewFile()) {
					throw new SystemException(SystemErrorCodes.ENTITY_WRITE_ERROR);
				}
			}
			FileUtils.writeStringToFile(file, model.toConfig().toString());
		} catch (IOException e) {
            e.printStackTrace();
			throw new SystemException(SystemErrorCodes.ENTITY_WRITE_ERROR);
		}
	}

}
