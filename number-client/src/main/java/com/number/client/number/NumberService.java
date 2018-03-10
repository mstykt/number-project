package com.number.client.number;

import java.util.List;

import com.number.client.model.NumberModel;

public interface NumberService {

	/**
	 * This method persists none exist number with insert date
	 * @param numberModel
	 * @return saved {@link NumberModel} instance
	 */
	NumberModel save(NumberModel numberModel);
	
	/**
	 * This method finds {@link NumberModel} instance via number attribute
	 * @param number
	 * @return {@link NumberModel} instance
	 */
	NumberModel findByNumber(Long number);
	
	/**
	 * This method finds all number with order, if order has not given it works as ascending by default
	 * @param order
	 * @return {@link NumberModel} list
	 */
	List<NumberModel> findAll(String order);
	
	/**
	 * This method finds maximum number
	 * @return {@link NumberModel} instance
	 */
	NumberModel maximumNumber();
	
	/**
	 * This method finds minimum number
	 * @return {@link NumberModel} instance
	 */
	NumberModel minimumNumber();
	
	/**
	 * This method deletes number via given id
	 * @param number
	 */
	void delete(long number);
	
	/**
	 * This method checks number is already persist or not
	 * @param number
	 * @return {@link Boolean}
	 */
	boolean isNumberExist(long number);
	
	/**
	 * This method checks, is there any number in db or not added yet
	 * 
	 * @return {@link Boolean}
	 */
	boolean isNumberExist();
	
}
