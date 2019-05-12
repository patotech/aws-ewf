package cl.aws.demo.lambda.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import cl.aws.demo.lambda.dynamodb.dao.Requirement;
import cl.aws.demo.lambda.dynamodb.repos.DynamoDbRepository;
import cl.aws.demo.lambda.jwt.JwtParserToolkit;
import io.jsonwebtoken.Claims;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@EnableWebMvc
@CrossOrigin(origins = "*")
public class RequirementsController {
	
	private static final String EMAIL_CLAIM = "email";
	
	@Autowired
	private DynamoDbRepository reqRepo;
	
	@Autowired
	private JwtParserToolkit jwtParser;
	
	private String getEmailFromJwt( String jwtToken ) {
		final Claims body = jwtParser.parseClaims( jwtToken ).getBody();
		return body.get( EMAIL_CLAIM, String.class );
	}

	@GetMapping("/webappevolution-api/rds/requirement")
	public List<Requirement> allRequirements() {
		final List<Requirement> returnList = new ArrayList<>();
		final Iterator<Requirement> requirements = reqRepo.findAll().iterator();
		while( requirements.hasNext() ) {
			returnList.add( requirements.next() );
		}
		return returnList;
	}

	@GetMapping("/webappevolution-api/rds/requirement/{id}")
	public Requirement getRequirement(@PathVariable String id) {
		return reqRepo.findById(id).orElseThrow(() -> new NullPointerException());
	}

	@PostMapping("/webappevolution-api/rds/requirement")
	public Requirement newRequirement( @RequestHeader("Authorization") String jwtToken
			, @RequestBody Requirement newReq) {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//newReq.setUser(authentication.getName());
		
		newReq.setUser( getEmailFromJwt( jwtToken ) );
		return reqRepo.save(newReq);
	}

	@PutMapping("/webappevolution-api/rds/requirement/{id}")
	public Requirement replaceRequirement( @RequestHeader("Authorization") String jwtToken
			, @RequestBody Requirement newRequirement
			, @PathVariable String id) {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return reqRepo.findById(id).map(requirement -> {
			requirement.setDate(newRequirement.getDate());
			requirement.setReason(newRequirement.getReason());
			requirement.setStatus(newRequirement.getStatus());
			requirement.setUser( getEmailFromJwt( jwtToken ) );
			return reqRepo.save(requirement);
		}).orElseGet(() -> {
			newRequirement.setId(id);
			newRequirement.setUser( getEmailFromJwt( jwtToken ) );
			return reqRepo.save(newRequirement);
		});
	}

	@DeleteMapping("/webappevolution-api/rds/requirement/{id}")
	public void deleteRequirement(@PathVariable String id) {
		reqRepo.deleteById(id);
	}
}
