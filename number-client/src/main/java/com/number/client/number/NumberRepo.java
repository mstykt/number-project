package com.number.client.number;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.number.client.model.NumberModel;

@Repository
public interface NumberRepo extends MongoRepository<NumberModel, Long> {

	/**
	 * For find min number
	 * @return {@link NumberModel} instance
	 */
	NumberModel findFirstByOrderByNumberAsc();
	
	/**
	 * For find max number
	 * @return {@link NumberModel} instance
	 */
	NumberModel findFirstByOrderByNumberDesc();
	
}
