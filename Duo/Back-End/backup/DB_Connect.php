<?php
//	DB_connect.php
//	Grapp
//	by Hein Dauven
class DB_Connect {
    private $conn; // constructor
    
    public function connect() { // Connecting to database
		// include the configuration file
        require_once 'db_config.php';
		
        // connect to mysql
        $this->conn = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_DATABASE);
        
        return $this->conn; // return database handler
    }
}
?>