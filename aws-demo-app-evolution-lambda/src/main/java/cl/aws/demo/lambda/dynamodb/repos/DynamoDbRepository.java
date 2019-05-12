package cl.aws.demo.lambda.dynamodb.repos;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import cl.aws.demo.lambda.dynamodb.dao.Requirement;

@EnableScan
public interface DynamoDbRepository extends CrudRepository<Requirement, String> {
	
	

}
