package com.employ.employ.InputSchema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                      //they handle all get and set method
@NoArgsConstructor        //user to create constructor
@AllArgsConstructor       //create all constructor even constructor over loading
public class Employee {
    private Long id;
    private String name;
    private String number;
    private String email;
    private String city;
    private String address;
/* 
   public String getName(){
    return name;
   }

   public void setName(String name) {
       this.name = name;
   }
       */
}
