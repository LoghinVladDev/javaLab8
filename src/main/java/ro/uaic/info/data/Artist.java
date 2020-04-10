package ro.uaic.info.data;

public class Artist {
    private String name;
    private String country;
    private int ID;

    public String toString(){
        return "Artist '" +
                this.name +
                "', originating from " +
                this.country;
    }

    public Artist(int ID, String name, String country){
        this.ID         = ID;
        this.name       = name;
        this.country    = country;
    }

    public Artist(String name, String country){
        this.name       = name;
        this.country    = country;
    }

    public int getID(){
        return this.ID;
    }

    public String getName(){
        return this.name;
    }

    public String getCountry(){
        return this.country;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setCountry(String country){
        this.country = country;
    }
}
