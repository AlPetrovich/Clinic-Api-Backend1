package com.dh.clinica;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DentalClinicApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(DentalClinicApplication.class, args);
	}

}

//***************REPOSITORY BACKEND API-CLINIC ********************
//https://github.com/AlPetrovich/Clinic-Api-Backend1


//***************REPOSITORY VIEW WITH REACT IN PROGRESS ********************
//https://github.com/AlPetrovich/Clinic-Frontend-React


//contacts
//-------------------------alexispetrovich11@gmail.com-------------------------