package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//    1属于正常预定，0属于这一段记录已经过期，是之前的预定
    int booking_status;

    @JsonIgnore
    String common_key;
}
