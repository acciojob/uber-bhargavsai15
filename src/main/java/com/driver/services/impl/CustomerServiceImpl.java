package com.driver.services.impl;

import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query

		Customer customer=customerRepository2.findById(customerId).get();

		List<TripBooking> customerTripBookingList=customer.getTripBookingList();


		TripBooking tripBooking=new TripBooking();

		List<Driver> driverList=driverRepository2.findAll();

		//Creating List for storing the All the drivers Id
		List<Integer> adminIds=new ArrayList<>();
		for(Driver driver:driverList){
			adminIds.add(driver.getDriverId());
		}
		Collections.sort(adminIds);
		int bill=0;
		boolean isAvailable=false;
		try{
			for(int i=0;i<adminIds.size();i++){
				Driver driver=driverRepository2.findById(adminIds.get(i)).get();
				if(driver.getCab().getAvailable()==true){
					List<TripBooking> driverTripBookingList=driver.getTripBookingList();
					bill=distanceInKm*driver.getCab().getPerKmRate();
					tripBooking.setBill(bill);
					tripBooking.setCustomer(customer);
					tripBooking.setFromLocation(fromLocation);
					tripBooking.setToLocation(toLocation);
					tripBooking.setDistanceInKm(distanceInKm);
					tripBooking.setStatus(TripStatus.CONFIRMED);
					tripBooking.setDriver(driver);

					//calculating bill

					customerTripBookingList.add(tripBooking);
					driverTripBookingList.add(tripBooking);
					customerRepository2.save(customer);
					tripBookingRepository2.save(tripBooking);
					isAvailable=true;
					driverRepository2.save(driver);
				}
				if(isAvailable){
					break;
				}
			}
		}catch (Exception e){
			throw new Exception("No cab available!");
		}

		return  tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly

	}

	public List<Customer> listOfCustomers(){
		List<Customer> customers=customerRepository2.findAll();
		return customers;
	}
}
