package ro.uaic.info.data;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Chart {
    private int ID;
    private String name;
    private List<Pair<Album, Integer>> albumList;

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("Chart '").append(this.name).append("' with songs:\n");

        this.albumList.forEach(e->sb.append("\t").append(e.getKey().toString()).append(", rating : ").append(e.getValue()).append("\n"));

        return sb.toString();
    }

    public Chart(int ID, String name){
        this.ID = ID;
        this.name = name;
        this.albumList = new ArrayList<>();
    }

    public void addAlbum(Album album, int position){
        this.albumList.add(new Pair<>(album,position));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAlbumList(List<Pair<Album, Integer>> albumList) {
        this.albumList = albumList;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.ID;
    }

    public List<Pair<Album, Integer>> getAlbumList() {
        return this.albumList;
    }
}
