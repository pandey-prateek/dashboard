package com.orange.models;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class RowRecord {
    
    int _id;
    LocalDateTime ts;
    Double value;
    public int get_id() {
        return _id;
    }
    public void set_id(int _id) {
        this._id = _id;
    }
    public RowRecord() {
        
    }
    
    public RowRecord(int id,LocalDateTime ts, Double value) {
        this._id = _id;
        this.ts = ts;
        this.value = value;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public LocalDateTime getTs() {
        return ts;
    }
    public void setTs(LocalDateTime ts) {
        this.ts = ts;
    }
}
