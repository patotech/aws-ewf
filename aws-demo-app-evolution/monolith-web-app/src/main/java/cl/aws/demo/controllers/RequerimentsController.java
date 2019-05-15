package cl.aws.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.aws.demo.rds.jpa.Requirement;
import cl.aws.demo.rds.jpa.RequirementRepository;

@RestController
public class RequerimentsController {

	@Autowired
	private RequirementRepository reqRepo;

	@GetMapping("/webappevolution-api/rds/requirement")
	public List<Requirement> allRequirements() {
		return reqRepo.findAll();
	}

	@GetMapping("/webappevolution-api/rds/requirement/{id}")
	public Requirement getRequirement(@PathVariable Long id) {
		return reqRepo.findById(id).orElseThrow(() -> new NullPointerException());
	}

	@PostMapping("/webappevolution-api/rds/requirement")
	public Requirement newRequirement(@RequestBody Requirement newReq) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		newReq.setUser(authentication.getName());
		return reqRepo.save(newReq);
	}

	@PutMapping("/webappevolution-api/rds/requirement/{id}")
	public Requirement replaceRequirement(@RequestBody Requirement newRequirement, @PathVariable Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return reqRepo.findById(id).map(requirement -> {
			requirement.setDate(newRequirement.getDate());
			requirement.setReason(newRequirement.getReason());
			requirement.setStatus(newRequirement.getStatus());
			requirement.setUser(authentication.getName());
			return reqRepo.save(requirement);
		}).orElseGet(() -> {
			newRequirement.setId(id);
			return reqRepo.save(newRequirement);
		});
	}

	@DeleteMapping("/webappevolution-api/rds/requirement/{id}")
	public void deleteRequirement(@PathVariable Long id) {
		reqRepo.deleteById(id);
	}
}
