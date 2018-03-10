package com.number.client.number;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.number.client.model.NumberModel;

/**
 * This {@link Service} class performs service operations for {@link NumberModel}
 * 
 * @author mesut
 *
 */
@Service
public class NumberServiceImpl implements NumberService {

	private NumberRepo repo;
	
	@Autowired
	public NumberServiceImpl(NumberRepo repo) {
		this.repo = repo;
	}

	@Override
	public NumberModel save(NumberModel numberModel) {
		return repo.save(numberModel);
	}

	@Override
	public NumberModel findByNumber(Long number) {
		return repo.findById(number).orElse(null);
	}
	
	@Override
	public List<NumberModel> findAll(String order) {
		List<NumberModel> numberList;
		
		if ("descending".equalsIgnoreCase(order)) {
			numberList = repo.findAll(new Sort(Sort.Direction.DESC, "number"));
		} else {
			numberList = repo.findAll(new Sort(Sort.Direction.ASC, "number"));
		}
		
		return numberList;
	}

	@Override
	public NumberModel maximumNumber() {
		return repo.findFirstByOrderByNumberDesc();
	}

	@Override
	public NumberModel minimumNumber() {
		return repo.findFirstByOrderByNumberAsc();
	}

	@Override
	public void delete(long number) {
		repo.deleteById(number);
		
	}

	@Override
	public boolean isNumberExist(long number) {
		return findByNumber(number) != null;
	}

	@Override
	public boolean isNumberExist() {
		return repo.count() > 0;
	}

}
