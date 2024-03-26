package com.example.userdataexcel.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.userdataexcel.dto.ResponseStructure;
import com.example.userdataexcel.entity.User;
import com.example.userdataexcel.repository.UserRepository;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	
	public ResponseEntity<ResponseStructure<List<User>>> saveUser(MultipartFile file) {

		if(file.isEmpty()) {
			return new ResponseEntity<ResponseStructure<List<User>>>(HttpStatus.BAD_REQUEST);
		}
		ResponseStructure<List<User>> structure = null;
		try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			@SuppressWarnings("unchecked")
			List<User> users = new CsvToBeanBuilder(reader)
					.withType(User.class)
					.withIgnoreLeadingWhiteSpace(true)
					.build()
					.parse();
			
			users = userRepository.saveAll(users);
			
			structure = new ResponseStructure<List<User>>();
			structure.setStatusCode(HttpStatus.CREATED.value());
			structure.setMessage("Success");
			structure.setData(users);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<ResponseStructure<List<User>>>(structure,HttpStatus.CREATED);

	}

//	private List<User> csvToUser(InputStream inputStream) {
//		List<User> users = new ArrayList<User>();
//		try {
//			BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//			CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());// converting
//																													// csv
//																													// file
//																													// into
//																													// java
//																													// object
//																													// using
//																													// csv
//																													// parser
//																													// in
//																													// build
//																													// class
//
//			 Iterable<CSVRecord> csvRecord = csvParser.getRecords();
//			  CSVFormat csvFormat = CSVFormat.DEFAULT;
//			for (CSVRecord records : csvRecord) {
//				User user = new User(records.get("username"), records.get("email"), records.get("password"),
//						Long.parseLong(records.get("mobileNo")), records.get("role"));
//				users.add(user);
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return users;
//	}

	public boolean hasCsvFormat(MultipartFile file) {

		String type = "text/csv";
		if (!type.equals(file.getContentType()))
			return false;
		else
			return true;
	}

}
