/*==============================================================*/
/*	DB_Connect.php							                    */
/*	Grapp					  							        */
/*	by Hein Dauven							                    */
/*==============================================================*/
<?php
error_reporting(E_ALL ^ E_DEPRECATED);

class DB_Connect
{
    public $con; // constructor

    function __construct()
    {
    }

    function __destruct() // destructor
    {
        // $this->close();
    }
    
    public function connect() // Connecting to database
    {
		// include the configuration file
        require_once 'db_config.php';
		
        // connect to mysql
        $this->con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysqli_error($this->con));
        if (mysqli_connect_errno()) {
            die("Database connection failed");
        }
        
        return $this->con; // return database handler
    }

    public function close() // Close the database connection
    {
        mysqli_close($this->con);
    }
}
?>