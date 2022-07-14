package com.example.demo;

import com.example.demo.model.Booking;
import com.example.demo.model.Guest;
import com.example.demo.model.Hotel;
import com.example.demo.model.Room;
import com.scalar.db.api.*;
import com.scalar.db.config.DatabaseConfig;
import com.scalar.db.exception.transaction.TransactionException;
import com.scalar.db.io.Key;
import com.scalar.db.service.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;


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

        insertRoom( new Room(1,101,1,1,"common_key")) ;
        insertRoom( new Room(2,102,1,1,"common_key"));//1可预订
        insertRoom( new Room(3,201,0,1,"common_key")); //0为被定出
        insertRoom( new Room(4,202,1,1,"common_key"));

        insertRoom( new Room(5,101,1,2,"common_key"));
        insertRoom( new Room(6,102,1,2,"common_key"));
        insertRoom( new Room(7,201,1,2,"common_key"));
        insertRoom( new Room(8,202,1,2,"common_key"));
        insertRoom( new Room(9,301,0,2,"common_key"));//0为被定出


        insertHotel(new Hotel(1,"yagami hotel","common_key"));
        insertHotel(new Hotel(2,"hiyoshi hotel","common_key"));

        insertBooking(new Booking(1,2,5,7,"2022-07-01","2022-07-03",0,"common_key")); //之前的预定记录，到现在已经过期所以改状态为0
        insertBooking(new Booking(2,1,3,3,"2022-07-18","2022-07-19",1,"common_key"));//1号hotelid为3的房间
        insertBooking(new Booking(3,2,4,9,"2022-07-20","2022-07-22",1,"common_key"));//2号hotelid为9的房间


        insertGuest(new Guest(1,"kiki","0800000000","kiki@keio.jp","common_key"));
        insertGuest(new Guest(2,"Ayu","0801234567","Ayu@keio.jp","common_key"));
        insertGuest(new Guest(3,"Mira","080014312","mira@keio.jp","common_key"));
        insertGuest(new Guest(4,"Zhai","0800567788","zhai@keio.jp","common_key"));
        insertGuest(new Guest(5,"ck","08007543246","ck@keio.jp","common_key"));
    }

    public void insertRoom(Room room){
        try {
            tx = manager.start();
            Optional<Result> roomResult =
                    tx.get(new Get(new Key("room_id", room.getRoom_id()))
                                    .forNamespace("hotel")
                                    .forTable("room"));
            if (!roomResult.isPresent()) {
                Key key = new Key("room_id" , room.getRoom_id()) ;
                Put put = new Put(key)
                                .forTable("room")
                                .forNamespace ("hotel")
                                .withValue ("hotel_id" , room.getHotel_id() )
                                .withValue ( "room_status" , room.getRoom_status( ) )
                                .withValue ( "room_number" , room.getRoom_number() )
                                .withValue ( "common_key" , room.getCommon_key() );
                System.out.println("insertRoom:"+room.toString());
                tx.put(put);
            }
            tx.commit();
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertBooking(Booking booking){
        try {
            tx = manager.start();
            Optional<Result> bookingResult =
                    tx.get(new Get(new Key("booking_id", booking.getBooking_id()))
                                    .forNamespace("booking")
                                    .forTable("booking"));
            if (!bookingResult.isPresent()) {
                Key key = new Key("booking_id",booking.getBooking_id());
                Put put = new Put(key)
                        .forNamespace("booking")
                        .forTable("booking")
                        .withValue("hotel_id",booking.getHotel_id())
                        .withValue("room_id",booking.getRoom_id())
                        .withValue("guest_id",booking.getGuest_id())
                        .withValue("date_from",booking.getDate_from())
                        .withValue("date_to",booking.getDate_to())
                        .withValue("booking_status",booking.getBooking_status())
                        .withValue ( "common_key" , booking.getCommon_key() );
                System.out.println("insertBooking:"+booking.toString());
                tx.put(put);
            }
            tx.commit();
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertGuest(Guest guest){
        try {
            tx = manager.start();
            Optional<Result> guestResult =
                    tx.get(new Get(new Key("guest_id", guest.getGuest_id()))
                                    .forNamespace("guest")
                                    .forTable("guest"));
            if (!guestResult.isPresent()) {
                Key key = new Key("guest_id" , guest.getGuest_id()) ;
                Put put = new Put(key)
                        .forTable("guest")
                        .forNamespace ("guest")
                        .withValue ("guest_mail_address" , guest.getGuest_mail_address() )
                        .withValue ( "guest_name" , guest.getGuest_name( ) )
                        .withValue ( "guest_number" , guest.getGuest_number() )
                        .withValue ( "common_key" , guest.getCommon_key() );

                System.out.println("insertGuest:"+guest.toString());
                tx.put(put);
            }
            tx.commit();
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertHotel(Hotel hotel){
        try {
            tx = manager.start();
            Optional<Result> hotelResult =
                    tx.get(new Get(new Key("hotel_id", hotel.getHotel_id()))
                                    .forNamespace("hotel")
                                    .forTable("hotel"));
            if (!hotelResult.isPresent()) {
                Key key = new Key("hotel_id", hotel.getHotel_id());
                Put put = new Put(key)
                        .forTable("hotel")
                        .forNamespace("hotel")
                        .withValue("hotel_name", hotel.getHotel_name())
                        .withValue ( "common_key" , hotel.getCommon_key() );

                System.out.println("insertHotel:" + hotel.toString());
                tx.put(put);
            }
            tx.commit();
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }
    }
}
