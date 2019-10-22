package com.szhr.shortmessage.model;

import java.io.Serializable;
import java.util.Date;

public class Sms implements Serializable {
    public int id;
    public String address;
    public String content;
    public Date date;
    public int type;
    public boolean fromSim;
    public int status;
}
