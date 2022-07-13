package com.example.demo;

import com.example.demo.model.Booking;
import com.scalar.db.api.DistributedTransaction;
import com.scalar.db.api.DistributedTransactionManager;
import com.scalar.db.api.Put;
import com.scalar.db.config.DatabaseConfig;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;


public class InitDataBase {

    private DistributedTransactionManager manager;

    private DistributedTransaction tx;

    public void initData(){
        String databaseProp = "database.properties";
        DatabaseConfig scalarDBConfig =
                null;
        try {
            scalarDBConfig = new DatabaseConfig(new URL("classpath:" + databaseProp).openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TransactionFactory factory = new TransactionFactory(scalarDBConfig);
        manager=factory.getTransactionManager();

        insertRoom();
        insertHotel();
//        insertBooking(new Booking(1,1,1,1,"2022-07-13","2022-07-15",1));
        insertGuest();
    }

    public void insertRoom(){

    }

    public void insertBooking(Booking booking){
        try {
            tx = manager.start();
            Key key = new Key("booking_id",booking.getBooking_id());
            Put put = new Put(key)
                    .forNamespace("booking")
                    .forTable("booking")
                    .withValue("hotel_id",booking.getHotel_id())
                    .withValue("room_id",booking.getRoom_id())
                    .withValue("guest_id",booking.getGuest_id())
                    .withValue("date_from",booking.getDate_from())
                    .withValue("date_to",booking.getDate_to())
                    .withValue("booking_status",booking.getBooking_status());
            System.out.println("insertBooking:"+booking.toString());
            tx.put(put);
            tx.commit();
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertGuest(){

    }

    public void insertHotel(){

    }
}
