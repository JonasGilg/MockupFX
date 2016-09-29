package de.mockup.system.service;

import de.mockup.system.exceptions.SystemException;
import de.mockup.system.model.TemplateModel;

import java.util.List;

/**
 * Service to persist TempateModels
 */
public interface TemplateService {

	/**
	 * Loads all templates from application folder
	 *
	 * @return
	 * @throws SystemException
	 */
	List<TemplateModel> getTemplates() throws SystemException;

	/**
	 * Stores a template in the application folder
	 *
	 * @param template
	 * @throws SystemException
	 */
	void saveTemplate(TemplateModel template) throws SystemException;

    TemplateModel findById(int id) throws SystemException;
}
