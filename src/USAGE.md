# CSV Parser

* Given the CSV from the resources folder, the purpose of the application is to extract at any time a boolean flag for the given Jurisdiction Name and Action type.

* Examples of Financial Jurisdictions include EU-EMIR, JFSA, UK-EMIR, etc. 

* At any given time, the CSV can be modified, adding more jurisdictions, action types or fields (rows)

* The purpose for this application is to obtain the boolean flag for a given field name, jurisdiction and action type, in order for it to be used further on and decide upon this value if the field should or not be mapped to certain regulatory reporting practices.

* Example usage:  
  IsoFieldValidator isoFieldValidator = IsoFieldValidator.getInstance();  
  Boolean b0 = isoFieldValidator.retrieveFlag("Reporting timestamp", "EUEMIR", NEWT);  
