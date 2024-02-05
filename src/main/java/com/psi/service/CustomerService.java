package com.psi.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psi.model.dto.CustomerDto;
import com.psi.model.po.Customer;
import com.psi.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public void add(CustomerDto customerDto) {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		customerRepository.save(customer);
	}
	
	public void update(CustomerDto customerDto, Long id) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
		if(customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			customer.setName(customerDto.getName());
			customerRepository.save(customer);
		}
	}
	
	public void delete(Long id) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
		if(customerOpt.isPresent()) {
			customerRepository.deleteById(id);
		}
	}
	
	public CustomerDto getCustomerDtoById(Long id) {
		Optional<Customer> customerOpt = customerRepository.findById(id);
		if(customerOpt.isPresent()) {
			Customer customer = customerOpt.get();
			CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
			return customerDto;
		}
		return null;
	}
	
	public List<CustomerDto> findAll(){
		List<Customer> customers = customerRepository.findAll();
		
		List<CustomerDto> customerDtos = customers.stream()
				.map(customer -> modelMapper.map(customer, CustomerDto.class))
				.toList();
		return customerDtos;
	}
}
