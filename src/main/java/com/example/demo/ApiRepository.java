package com.example.demo;

import com.example.demo.exception.RepositoryConflictException;
import com.example.demo.exception.RepositoryCrudException;
import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.model.Room;
import com.scalar.db.api.*;
import com.scalar.db.exception.transaction.AbortException;
import com.scalar.db.exception.transaction.CrudConflictException;
import com.scalar.db.exception.transaction.CrudException;
import com.scalar.db.io.IntValue;
import com.scalar.db.io.Key;
import com.scalar.db.io.TextValue;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.print.Book;

@Repository
public class ApiRepository {

    public void addRoom(DistributedTransaction tx, Room room) {
        try {
            System.out.println("############################################"+room.toString());
            Key pk = new Key("room_id", room.getRoom_id());
            Put put = new Put(pk)
                    .forNamespace("hotel")
                    .forTable("room")
                    .withValue("room_status",room.getRoom_status())
                    .withValue("room_number",room.getRoom_number())
                    .withValue("hotel_id",room.getHotel_id())
                    .withValue("common_key","common_key");
            tx.put(put);
        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }

    public Room getRoom(DistributedTransaction tx, Integer id) {
        try {
            Key pk = new Key("room_id", id);
            Get get = new Get(pk)
                    .forNamespace("hotel")
                    .forTable("room");
            tx.get(get);
            Optional<Result> room = tx.get(get);
            int room_id = room.get().getValue("room_id").get().getAsInt();
            int room_status = room.get().getValue("room_status").get().getAsInt();
            int room_number = room.get().getValue("room_number").get().getAsInt();
            int hotel_id = room.get().getValue("hotel_id").get().getAsInt();
            Room roomResult = new Room();
            roomResult.setRoom_id(room_id);
            roomResult.setRoom_number(room_number);
            roomResult.setRoom_status(room_status);
            roomResult.setHotel_id(hotel_id);
            return roomResult;
            
        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }

    public List<Room> getRoomList(DistributedTransaction tx) {
        try {

            Scan scan = new Scan(new Key(new TextValue("common_key", "common_key")))
                    .forNamespace("hotel")
                    .forTable("room");

            List<Result> result = tx.scan(scan);

            List<Room> roomList = new ArrayList<Room>();

            for (Result value : result) {
                Room room = new Room();
                room.setRoom_id(value.getValue("room_id").get().getAsInt());
                room.setRoom_status(value.getValue("room_status").get().getAsInt());
                room.setRoom_number(value.getValue("room_number").get().getAsInt());
                room.setHotel_id(value.getValue("hotel_id").get().getAsInt());
                roomList.add(room);
            }

            return roomList;

        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }

    public List<Hotel> getHotelList(DistributedTransaction tx){
        try {

            Scan scan = new Scan(new Key(new TextValue("common_key", "common_key")))
                    .forNamespace("hotel")
                    .forTable("hotel");

            List<Result> result = tx.scan(scan);

            List<Hotel> hotelList = new ArrayList<>();

            for (Result value : result) {
                Hotel hotel = new Hotel();
                hotel.setHotel_id(value.getValue("hotel_id").get().getAsInt());
                hotel.setHotel_name(value.getValue("hotel_name").get().getAsString().get());
                hotelList.add(hotel);
            }

            return hotelList;

        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }

    public List<Booking> getBookingList(DistributedTransaction tx){
        try {

            Scan scan = new Scan(new Key(new TextValue("common_key", "common_key")))
                    .forNamespace("booking")
                    .forTable("booking");

            List<Result> result = tx.scan(scan);

            List<Booking> bookingList = new ArrayList<>();

            for (Result booking : result) {
                int booking_id = booking.getValue("booking_id").get().getAsInt();
                int hotel_id = booking.getValue("hotel_id").get().getAsInt();
                int guest_id = booking.getValue("guest_id").get().getAsInt();
                int room_id = booking.getValue("room_id").get().getAsInt();
                String date_from = booking.getValue("date_from").get().getAsString().get();
                String date_to = booking.getValue("date_to").get().getAsString().get();
                int booking_status = booking.getValue("booking_status").get().getAsInt();
                Booking bookingResult = new Booking(booking_id,hotel_id,guest_id,room_id,date_from,date_to,booking_status,"common_key");
                bookingList.add(bookingResult);
                System.out.println(bookingResult.toString());
            }
            return bookingList;

        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }




    public Booking getBooking(DistributedTransaction tx, Integer id) {
        try {
            Key pk = new Key("booking_id", id);
            Get get = new Get(pk)
                    .forNamespace("booking")
                    .forTable("booking");
            tx.get(get);
            Optional<Result> booking = tx.get(get);
            int booking_id = booking.get().getValue("booking_id").get().getAsInt();
            int hotel_id = booking.get().getValue("hotel_id").get().getAsInt();
            int guest_id = booking.get().getValue("guest_id").get().getAsInt();
            int room_id = booking.get().getValue("room_id").get().getAsInt();
            String date_from = booking.get().getValue("date_from").get().getAsString().get();
            String date_to = booking.get().getValue("date_to").get().getAsString().get();
            int booking_status = booking.get().getValue("booking_status").get().getAsInt();
            Booking bookingResult = new Booking(booking_id,hotel_id,guest_id,room_id,date_from,date_to,booking_status,"common_key");
            System.out.println("getBooking:"+booking.toString());
            return bookingResult;

        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }

    public Booking booking(DistributedTransaction tx, Booking bookingReq) throws AbortException, CrudException {
        List<Booking> bookingList = getBookingList(tx);
        int id = Integer.MIN_VALUE;
        for (Booking booking : bookingList) {
            id = Math.max(id,booking.getBooking_id());
            int booking_status = booking.getBooking_status();
            if(booking_status==0 || booking.getRoom_id()!=bookingReq.getRoom_id()){ //过期的预订记录或者不是当前房间，跳过
                continue;
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFrom = null;
            Date dateTo = null;
            Date dateReqFrom = null;
            Date dateReqTo = null;
            try {
                dateFrom = format.parse(booking.getDate_from());
                dateTo = format.parse(booking.getDate_to());
                dateReqFrom = format.parse(bookingReq.getDate_from());
                dateReqTo = format.parse(bookingReq.getDate_to());
                int x = dateFrom.compareTo(dateReqFrom);
                int y = dateTo.compareTo(dateReqTo);
                long dateFromTime = dateFrom.getTime();
                long dateToTime = dateTo.getTime();
                long dateReqFromTime = dateReqFrom.getTime();
                long dateReqToTime = dateReqTo.getTime();
                System.out.println(booking.getDate_from() + " " + booking.getDate_to() + " " +  " " + x + " " + y+" "+bookingReq.getDate_from() + " " + bookingReq.getDate_to());
                if(dateReqFromTime>dateToTime || dateReqToTime<dateFromTime){
                    continue;
                }else {
                    tx.abort();
                    throw new AbortException("can not booking this room");
                }
            } catch (ParseException e) {
                throw new AbortException("can not booking this room");
            }
        }
//            查room表是否可用
        Optional<Result> room = tx.get(new Get(new Key("room_id", bookingReq.getRoom_id()))
                .forNamespace("hotel")
                .forTable("room"));
        int room_status = room.get().getValue("room_status").get().getAsInt();
        if (room_status == 0) {
            //  如果room不可用直接抛出异常
            System.out.println("###########" + room_status);
            tx.abort();
            throw new AbortException("can not booking this room");
        }   

        //写booking表
        Key pk = new Key("booking_id", id+1);
        Put put = new Put(pk)
                .forNamespace("booking")
                .forTable("booking")
                .withValue("guest_id", bookingReq.getGuest_id())
                .withValue("date_from", bookingReq.getDate_from())
                .withValue("date_to", bookingReq.getDate_to())
                .withValue("room_id", bookingReq.getRoom_id())
                .withValue("booking_status", 1)
                .withValue ( "common_key" , "common_key")
                .withValue("hotel_id", bookingReq.getHotel_id());
        tx.put(put);
        bookingReq.setBooking_id(id+1);
        bookingReq.setBooking_status(1);
        return bookingReq;
    }
}
