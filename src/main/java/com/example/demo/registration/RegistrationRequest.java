package com.example.demo.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@NoArgsConstructor(force = true)

public class RegistrationRequest {
    private  String firstName;
    private  String lastName;
    private  String email;
    private  String password;



}
