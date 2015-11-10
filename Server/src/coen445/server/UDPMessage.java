package coen445.server;

import java.io.Serializable;

/**
 * Created by Ahmed on 15-11-10.
 */
public class UDPMessage implements Serializable {

    private String type;
    private int number;
    private static final long serialVersionUID = 1L;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

   public UDPMessage (String type, int number){
        this.type = type;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        String result;
        result = "Type= " + getType() + " , " + " Number = " + getNumber();
        return result;
    }
}
