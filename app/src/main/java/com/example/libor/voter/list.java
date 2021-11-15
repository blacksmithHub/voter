package com.example.libor.voter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by libor on 3/7/2018.
 */

public class list implements Serializable {
    private ArrayList<String> list;
    public ArrayList<String> getList()
    {
        return list;
    }
    public void setList (ArrayList<String> list)
    {
        this.list = list;
    }
}
