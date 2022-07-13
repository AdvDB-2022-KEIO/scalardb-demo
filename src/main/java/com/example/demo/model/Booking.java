package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
    int booking_id;
    int hotel_id;
    int guest_id;
    int room_id;
    String date_from;
    String date_to;
    int booking_status;
}
