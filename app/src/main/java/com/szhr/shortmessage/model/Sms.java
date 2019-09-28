package com.szhr.shortmessage.model;

import java.io.Serializable;
import java.util.Date;

public class Sms implements Serializable {
    public int id;
    public String sender;
    public String receiver;
    public String content;
    public Date date;
    public int type;
    public boolean fromSim;
    public int status; // 1 - 已发送，2 - 未发送， 3 - 未查看， 4 - 已查看
}
