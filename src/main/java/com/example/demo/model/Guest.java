package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guest {
    int guest_id;
    String guest_name;
//    其实是电话号码
    String guest_number;
    String guest_mail_address;
}
