package org.blade.msg;

public class DataMessage extends Message{


    private Object data;

    public DataMessage(int status, String description, Object data) {
        super(status, description);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
