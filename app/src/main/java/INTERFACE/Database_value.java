package INTERFACE;

public class Database_value {

    //private variables
    String _id;
    String _name;
    String _phone_number;
    String _TITTLE;
    String _TIME;
    String _DETAIL;
    String _DATE;
    String _DATE_with_month;


    // Empty constructor
    public Database_value() {

    }

    // constructor
//    public Database_value(int id, String name, String _phone_number) {
//        this._id = id;
//        this._name = name;
//        this._phone_number = _phone_number;
//    }

    //	// constructor
    public Database_value(String id, String Tittle, String Date, String Time, String Detail) {
        this._id = id;
        this._TITTLE = Tittle;
        this._DATE = Date;
        this._TIME = Time;
        this._DETAIL = Detail;

    }

    // getting ID
    public String getID() {
        return this._id;
    }

    // setting id
    public void setID(String id) {
        this._id = id;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber() {
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number) {
        this._phone_number = phone_number;
    }

    // setting getting tittle
    public String get_TITTLE() {
        return this._TITTLE;
    }

    // setting tittle
    public void set_TITTLE(String tittle) {
        this._TITTLE = tittle;
    }


    // setting getting time
    public String get_time() {
        return this._TIME;
    }

    // setting time
    public void set_TIME(String time) {
        this._TIME = time;
    }

    // setting getting DETAIL
    public String get_DETAIL() {
        return this._DETAIL;
    }

    // setting DETAIL
    public void set_DETAIL(String detail) {
        this._DETAIL = detail;
    }

    // setting getting DATE
    public String get_DATE() {
        return this._DATE;
    }

    // setting DATE
    public void set_DATE(String DATE) {this._DATE = DATE;
    }
    public String get_DATE_with_month() {
        return this._DATE_with_month;
    }

    // setting DATE
    public void set_DATE_with_month(String DATE) {
        this._DATE_with_month = DATE;
    }
}
