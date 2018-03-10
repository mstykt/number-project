package com.number.client.number;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.number.client.model.NumberModel;

@RestController
@RequestMapping("/")
public class NumberResource {

	@Autowired
	private NumberService numberService;

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NumberModel> save(@RequestBody NumberModel numberModel, UriComponentsBuilder builder) {

		NumberModel model = numberService.save(numberModel);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(builder.path("/find/{number}").buildAndExpand(numberModel.getNumber()).toUri());

		return new ResponseEntity<>(model, httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/find/{number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NumberModel> findByNumber(@PathVariable("number") long number) {
		NumberModel numberModel = numberService.findByNumber(number);

		return new ResponseEntity<NumberModel>(numberModel, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{number}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteByNumber(@PathVariable("number") long number) {
		numberService.delete(number);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/maxNumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NumberModel> maxNumber() {
		return new ResponseEntity<>(numberService.maximumNumber(), HttpStatus.OK);
	}

	@RequestMapping(value = "/minNumber", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<NumberModel> minNumber() {
		return new ResponseEntity<>(numberService.minimumNumber(), HttpStatus.OK);
	}

	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<NumberModel>> findAll(@RequestParam(value = "order", defaultValue = "ascending") final String order) {
		return new ResponseEntity<>(numberService.findAll(order), HttpStatus.OK);
	}
}
