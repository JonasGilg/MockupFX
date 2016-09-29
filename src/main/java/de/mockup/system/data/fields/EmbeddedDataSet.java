package de.mockup.system.data.fields;

import de.mockup.system.data.DataSet;

import java.util.List;

/**
 * Defines an Embedded DataField like an Employee as ContactPerson for a Customer.
 */
public class EmbeddedDataSet extends DataField<DataSet>{

	/*
	 * TODO DataSet Management, EmbeddedDateset needs reference to other dataset
	 */

	@Override
	public List<DataSet> getData(int count) {
		//TODO
		return null;
	}

}
