package dk.dtu.model.connector;

public abstract class Constant
{
	public static final String
			server					= "maigaard.io",  	// database-serveren
			database				= "cdio_final",  	//"jdbcdatabase", // navnet paa din database = dit studienummer
			username				= "group23", 		// dit brugernavn = dit studienummer
			password				= "gSk7j57~"; 		// dit password som du har valgt til din database

	public static final boolean
			useInitialPoolSizeSettings  = true,         // whether or not to use the custom pool settings
			useMinPoolSizeSettings      = true,         // whether or not to use the custom pool settings
			useAcquireIncrementSettings = true,         // whether or not to use the custom pool settings
			useMaxPoolSizeSettings      = true,         // whether or not to use the custom pool settings
			useMaxStatementsSettings    = true;         // whether or not to use the custom pool settings
	
	public static final int
			port                    = 3306,
			initialPoolSize         = 3,                // this setting is optional -- c3p0 can work with defaults
			minPoolSize             = 3,                // this setting is optional -- c3p0 can work with defaults
			acquireIncrement        = 3,                // this setting is optional -- c3p0 can work with defaults
			maxPoolSize             = 15,               // this setting is optional -- c3p0 can work with defaults
			maxStatements           = 180;              // this setting is optional -- c3p0 can work with defaults
	
}
