package com.smartgrid.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 
 * Provides DB logging capabilities for simulation data.
 * 
 * @author Panos Katseas
 * @version 1.1
 * @since 2012-03-01
 */
public class Logger {

	/**
	 * Object providing connection to the DB.
	 */
	private Connection con;

	/**
	 * The connection URL to the DB.
	 */
	private String connectionURL;

	/**
	 * Statement object, for executing queries on the DB.
	 */
	private Statement stmt;

	/**
	 * Default Constructor
	 * 
	 * Assumes default connection URL since no parameters are given.
	 */
	public Logger() {
		con = null;
		stmt = null;
		connectionURL = "jdbc:mysql://localhost:3306/smartgrid?"
				+ "user=smartgrid&password=smartgrid";
	}

	/**
	 * Parameterized Constructor
	 * 
	 * Initializes connection URL depending on the parameters given.
	 * 
	 * @param database
	 *            the database that will be accessed.
	 * @param hostname
	 *            the database hostname (localhost or otherwise).
	 * @param port
	 *            the database port
	 * @param username
	 *            the database username used for accessing the database.
	 * @param password
	 *            the database user's password.
	 */
	public Logger(String hostname, String database, String username,
			String password) {
		con = null;
		stmt = null;
		this.connectionURL = "jdbc:mysql://" + hostname + "/" + database + "?"
				+ "user=" + username + "&password=" + password;
	}

	/**
	 * Opens DB connection and creates a statement to execute queries on the DB.
	 * Also creates the tables necessary for storing data on the DB.
	 * 
	 * @return true if the connection is established, false otherwise.
	 */
	public boolean open() {
		try {
			con = DriverManager.getConnection(connectionURL);
		} catch (SQLException e) {
			System.out.println("Connection SQL Exception: " + e.toString());
			e.printStackTrace();
			System.exit(-1);
		}

		try {
			stmt = con.createStatement();
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
		} catch (Exception e) {
			System.out.println("General Exception: " + e.toString());
		}

		createTables();

		return true;
	}

	/**
	 * Closes the connection and the statement.
	 */
	public void close() {
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
		}
	}

	/**
	 * Creates the necessary tables for storing the data on the DB, if they
	 * don't already exist.
	 */
	private void createTables() {
		String query = new String();

		query = "CREATE TABLE IF NOT EXISTS `aggregator_log` "
				+ "(`run_id` int(11) NOT NULL,  "
				+ "`tick` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  "
				+ "`supply` double NOT NULL,  "
				+ "`overallDemand` double NOT NULL,  "
				+ "`price` double NOT NULL,"
				+ "PRIMARY KEY (`run_id`,`tick`)) " + "ENGINE=MyISAM "
				+ "DEFAULT CHARSET=latin1 " + "COLLATE=latin1_general_ci;";

		executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS `aggregator_policy` ("
				+ "`aggregator_policy_id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`author` text CHARACTER SET latin1 NOT NULL,"
				+ "`name` text CHARACTER SET latin1 NOT NULL,"
				+ "`version` double NOT NULL,"
				+ "PRIMARY KEY (`aggregator_policy_id`)) "
				+ "ENGINE=MyISAM "
				+ "DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;";

		executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS `household_log` ("
				+ "`household_id` bigint(20) NOT NULL,"
				+ "`run_id` int(11) NOT NULL,"
				+ "`tick` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
				+ "`demand` double NOT NULL,"
				+ "`appliancesOn` int(11) NOT NULL,"
				+ "PRIMARY KEY (`household_id`,`run_id`,`tick`)) "
				+ "ENGINE=MyISAM "
				+ "DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;";

		executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS `household_policy` ("
				+ "`household_policy_id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`author` text CHARACTER SET latin1 NOT NULL,"
				+ "`name` text CHARACTER SET latin1 NOT NULL,"
				+ "`version` double NOT NULL,"
				+ "PRIMARY KEY (`household_policy_id`)) "
				+ "ENGINE=MyISAM "
				+ "DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;";

		executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS `run` ("
				+ "`run_id` int(11) NOT NULL AUTO_INCREMENT,"
				+ "`aggregator_policy_id` int(11) NOT NULL,"
				+ "`date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,"
				+ "PRIMARY KEY (`run_id`)) "
				+ "ENGINE=MyISAM "
				+ "DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;";

		executeUpdate(query);

		query = "CREATE TABLE IF NOT EXISTS `run_household_log_household_policy` ("
				+ "`run_id` int(11) NOT NULL,"
				+ "`household_id` bigint(20) NOT NULL,"
				+ "`household_policy_id` int(11) NOT NULL,"
				+ "PRIMARY KEY (`run_id`,`household_id`,`household_policy_id`)) "
				+ "ENGINE=MyISAM "
				+ "DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;";

		executeUpdate(query);

	}

	/**
	 * Executes an update on the DB.
	 * 
	 * Receives an update query (example: insert statements) in String format
	 * and executes it on the DB.
	 * 
	 * @param query
	 *            the update query to be executed.
	 * @return true if the connection is established, false otherwise.
	 */
	private boolean executeUpdate(String query) {
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e.toString());
			e.printStackTrace();
			System.exit(-1);
		}
		return true;
	}

	/**
	 * Logs data on the aggregator_policy table.
	 * 
	 * Aggregator Policy data given is logged on the table only if the same
	 * triplet of <author,name,version> do not already exist on the table
	 * (prevent duplicate insertion).
	 * 
	 * @param author
	 *            the author of the Policy.
	 * @param name
	 *            the name of the Policy.
	 * @param version
	 *            the version of the Policy.
	 * 
	 * @return true if the query is executed correctly, false otherwise.
	 */
	public boolean logAggregatorPolicy(String author, String name,
			double version) {

		String query = new String();

		query = "INSERT INTO aggregator_policy (author, name, version) SELECT "
				+ "'"
				+ author
				+ "',"
				+ "'"
				+ name
				+ "',"
				+ version
				+ "FROM dual WHERE NOT EXISTS (SELECT aggregator_policy_id FROM aggregator_policy WHERE "
				+ " author = '" + author + "' AND name = '" + name
				+ "' AND version = '" + version + "')" + ";";

		if (executeUpdate(query)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Logs data on the aggregator_log table.
	 * 
	 * Aggregator data given is logged on the aggregator_log table. run_id is
	 * taken straight from the last run_id inserted in the run table (ie the
	 * current run).
	 * 
	 * @param tick
	 *            the current tick.
	 * @param supply
	 *            the current electricity supply.
	 * @param overallDemand
	 *            the current overall demand from all households.
	 * 
	 * @return true if the query is executed correctly, false otherwise.
	 */
	public boolean logAggregator(Date tick, double supply,
			double overallDemand, double price) {

		Timestamp tstamp = new Timestamp(tick.getTime());

		String query = new String();

		query = "INSERT INTO aggregator_log (run_id, tick, supply, overallDemand, price) VALUES ("
				+ " ((SELECT Auto_increment FROM information_schema.tables WHERE table_name='run') - 1)"
				+ ",'"
				+ tstamp.toString()
				+ "',"
				+ supply
				+ ","
				+ overallDemand + "," + price + ");";

		if (executeUpdate(query)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Logs data on the household_policy table.
	 * 
	 * Household Policy data given is logged on the table only if the same
	 * triplet of <author,name,version> do not already exist on the table
	 * (prevent duplicate insertion).
	 * 
	 * @param author
	 *            the author of the Policy.
	 * @param name
	 *            the name of the Policy.
	 * @param version
	 *            the version of the Policy.
	 * 
	 * @return true if the query is executed correctly, false otherwise.
	 */
	public boolean logHouseholdPolicy(String author, String name, double version) {

		String query = new String();

		query = "INSERT INTO household_policy (author, name, version) SELECT "
				+ "'"
				+ author
				+ "',"
				+ "'"
				+ name
				+ "',"
				+ version
				+ "FROM dual WHERE NOT EXISTS (SELECT household_policy_id FROM household_policy WHERE "
				+ " author = '" + author + "' AND name = '" + name
				+ "' AND version = '" + version + "')" + ";";

		if (executeUpdate(query)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Logs data on the household_log table.
	 * 
	 * Household demand data given is logged on the aggregator_log table. run_id
	 * is taken straight from the last run_id inserted in the run table (ie the
	 * current run).
	 * 
	 * @param householdID
	 *            the household ID.
	 * @param tick
	 *            the current tick.
	 * @param demand
	 *            the current electricity demand for the house.
	 * @param appliancesOn
	 *            the current number of appliances that are activated in the
	 *            household.
	 * 
	 * @return true if the query is executed correctly, false otherwise.
	 */
	public boolean logHouseholdDemand(long householdID, Date tick,
			double demand, int appliancesOn) {

		Timestamp tstamp = new Timestamp(tick.getTime());

		String query = new String();

		query = "INSERT INTO household_log (household_id, run_id, tick, demand, appliancesOn) VALUES ("
				+ householdID
				+ ","
				+ " ((SELECT Auto_increment FROM information_schema.tables WHERE table_name='run') - 1)"
				+ ",'"
				+ tstamp.toString()
				+ "',"
				+ demand
				+ ","
				+ appliancesOn
				+ ");";

		if (executeUpdate(query)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Logs data on the run table.
	 * 
	 * aggregator_policy_id is taken straight from the last aggregator_policy_id
	 * inserted in the aggregator_policy table (ie the current aggregator
	 * policy).
	 * 
	 * @return true if the query is executed correctly, false otherwise.
	 */
	public boolean logRun() {

		String query = new String();

		query = "INSERT INTO run (aggregator_policy_id) VALUES ("
				+ "((SELECT Auto_increment FROM information_schema.tables WHERE table_name='aggregator_policy') - 1)"
				+ ");";

		if (executeUpdate(query)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Logs data on the run_household_log_household_policy table.
	 * 
	 * household_policy_id is found from the policy's <author,name,version>
	 * triplet and run_id is taken straight from the last run_id inserted in the
	 * run table (ie the current run).
	 * 
	 * @param household_id
	 *            the household ID.
	 * @param author
	 *            the author of the Policy.
	 * @param name
	 *            the name of the Policy.
	 * @param version
	 *            the version of the Policy.
	 * 
	 * @return true if the query is executed correctly, false otherwise.
	 */
	public boolean logRunHouseholdConnection(long household_id, String author,
			String name, double version) {

		String query = new String();

		query = "INSERT INTO run_household_log_household_policy (run_id, household_id, household_policy_id) VALUES ("
				+ "((SELECT Auto_increment FROM information_schema.tables WHERE table_name='run') - 1)"
				+ ","
				+ household_id
				+ ","
				+ "(SELECT household_policy_id FROM household_policy WHERE"
				+ " author = '"
				+ author
				+ "' AND name = '"
				+ name
				+ "' AND version = '" + version + "')" + ");";

		if (executeUpdate(query)) {
			return true;
		} else {
			return false;
		}
	}

}
