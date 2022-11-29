package com.xls.snak;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class DataStore implements Serializable {
    public int last_chosen_size = 15;
    public long highscore = 0;

    public DataStore() {

    }
}
