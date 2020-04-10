package ro.uaic.info.data;

public class Album {
    private int ID;
    private String name;
    private int artistID;
    private int releaseYear;

    public String toString(){
        return "Album '" +
                this.name +
                "', released in " +
                this.releaseYear;
    }

    public Album(int ID, String name, int artistID, int releaseYear){
        this.ID             = ID;
        this.name           = name;
        this.artistID       = artistID;
        this.releaseYear    = releaseYear;
    }

    public Album(String name, int artistID, int releaseYear){
        this.name           = name;
        this.artistID       = artistID;
        this.releaseYear    = releaseYear;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public int getArtistID() {
        return this.artistID;
    }

    public int getReleaseYear() {
        return this.releaseYear;
    }

    public void setArtistID(int artistID) {
        this.artistID = artistID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
}
