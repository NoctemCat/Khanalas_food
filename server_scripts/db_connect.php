<?php
 
/**
 * A class file to connect to database
 */
class DB_CONNECT {
    public $con;
 
    // constructor
    function __construct() {
        // import database connection variables
        require_once __DIR__ . '/db_config.php';
        // connecting to database
        $this->con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
    }
 
    // destructor
    function __destruct() {
        // closing db connection
        $this->close();
    }
 
    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        require_once __DIR__ . '/db_config.php';
 
        // Connecting to mysql database
        $this->con = mysqli_connect(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error());
 
        // Selecing database
        $db = mysqli_select_db($con, DB_DATABASE) or die(mysqli_error()) or die(mysqli_error());
 
        // returing connection cursor
    }
 
    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysqli_close($this->con);
    }
 
}
 
?>