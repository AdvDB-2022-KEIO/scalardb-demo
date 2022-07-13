package com.example.demo;

import com.example.demo.exception.RepositoryConflictException;
import com.example.demo.exception.RepositoryCrudException;
import com.example.demo.model.Booking;
import com.example.demo.model.Room;
import com.scalar.db.api.*;
import com.scalar.db.exception.transaction.CrudConflictException;
import com.scalar.db.exception.transaction.CrudException;
import com.scalar.db.io.Key;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ApiRepository {

    public void addRoom(DistributedTransaction tx, Room room) {
//        try {
//            Key pk = createPk(userId);
//            Delete delete = new Delete(pk)
//                    .forNamespace(NAMESPACE)
//                    .forTable(TABLE_NAME);
//            tx.delete(delete);
//        } catch (CrudConflictException e) {
//            throw new RepositoryConflictException(e.getMessage(), e);
//        } catch (CrudException e) {
//            throw new RepositoryCrudException("Deleting User failed", e);
//        }

        try {
            System.out.println("############################################"+room.toString());
            Key pk = new Key("room_id", room.getRoom_id());
            Put put = new Put(pk)
                    .forNamespace("hotel")
                    .forTable("room")
//                    .withValue("room_id",room.getRoom_id())
                    .withValue("room_status",room.getRoom_status())
                    .withValue("room_number",room.getRoom_number())
                    .withValue("hotel_id",room.getHotel_id());
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
//            room.get().getValue("room_id").get().getAsString().get();
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

    public Booking getBooking(DistributedTransaction tx, Integer id) {
        try {
            Key pk = new Key("booking_id", id);
            Get get = new Get(pk)
                    .forNamespace("booking")
                    .forTable("booking");
            tx.get(get);
            Optional<Result> booking = tx.get(get);
//            booking.get().getValue("room_id").get().getAsString().get();
            int booking_id = booking.get().getValue("booking_id").get().getAsInt();
            int hotel_id = booking.get().getValue("hotel_id").get().getAsInt();
            int guest_id = booking.get().getValue("guest_id").get().getAsInt();
            int room_id = booking.get().getValue("room_id").get().getAsInt();
//            int room_id = 1;
            String date_from = booking.get().getValue("date_from").get().getAsString().get();
            String date_to = booking.get().getValue("date_to").get().getAsString().get();
            int booking_stattus = booking.get().getValue("booking_status").get().getAsInt();
            Booking bookingResult = new Booking(booking_id,hotel_id,guest_id,room_id,date_from,date_to,booking_stattus);
            System.out.println("getBooking:"+booking.toString());
            return bookingResult;

        } catch (CrudConflictException e) {
            throw new RepositoryConflictException(e.getMessage(), e);
        } catch (CrudException e) {
            throw new RepositoryCrudException("Reading User failed", e);
        }
    }
}
