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



		List<Driver> driverList=driverRepository2.findAll();
        if(driverList.size()==0){
            throw new Exception("No cab available!");
        }

		//Creating List for storing the All the drivers Id
		List<Integer> adminIds=new ArrayList<>();
		for(Driver driver:driverList){
			adminIds.add(driver.getDriverId());
		}

		Collections.sort(adminIds);
		int bill=0;
		boolean isAvailable=false;

		for(int i=0;i<adminIds.size();i++){
			Driver driver=driverRepository2.findById(adminIds.get(i)).get();
			if(driver.getCab().getAvailable()==true){
				TripBooking tripBooking=new TripBooking(fromLocation,toLocation,distanceInKm,TripStatus.CONFIRMED);
				List<TripBooking> driverTripBookingList=driver.getTripBookingList();

                //calculating bill
				bill=distanceInKm*driver.getCab().getPerKmRate();

				tripBooking.setBill(bill);

                driver.getCab().setAvailable(false);
                tripBooking.setCustomer(customer);
				tripBooking.setDriver(driver);


//				customerTripBookingList.add(tripBooking);
//				driverTripBookingList.add(tripBooking);
				//customerRepository2.save(customer);
				isAvailable=true;
				//driverRepository2.save(driver);

				tripBookingRepository2.save(tripBooking);
				return  tripBooking;
			}
		}


		if(isAvailable==false){
			throw new Exception("No cab available!");
		}
		return null;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
		tripBooking.setBill(0);

        Driver driver=tripBooking.getDriver();
        driver.getCab().setAvailable(true);
		tripBookingRepository2.save(tripBooking);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
        if(tripBooking!=null){
            tripBooking.setStatus(TripStatus.COMPLETED);
            Driver driver=tripBooking.getDriver();
            driver.getCab().setAvailable(true);
            tripBookingRepository2.save(tripBooking);
        }

	}

	public List<Customer> listOfCustomers(){
		List<Customer> customers=customerRepository2.findAll();
		return customers;
	}
}
