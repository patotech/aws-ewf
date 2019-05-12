package cl.aws.demo.lambda.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

@Configuration
@EnableDynamoDBRepositories("cl.aws.demo.lambda.dynamodb.repos")
public class DynamoDbConfig {

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;
    
    @Value("${amazon.dynamodb.region}")
    private String amazonDynamoDBRegion;
 
    @Value("${amazon.aws.accesskey:}")
    private String amazonAWSAccessKey;
 
    @Value("${amazon.aws.secretkey:}")
    private String amazonAWSSecretKey;
 
    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
    	final AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
    			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration( amazonDynamoDBEndpoint, amazonDynamoDBRegion ) )
    			// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
    			// AWSCredentialsProvider implementation that provides credentials by looking at the: 
    			// AWS_ACCESS_KEY_ID (or AWS_ACCESS_KEY) and AWS_SECRET_KEY (or AWS_SECRET_ACCESS_KEY) 
    			// environment variables. If the AWS_SESSION_TOKEN environment variable is also 
    			// set then temporary credentials will be used.
    			//.withCredentials( new EnvironmentVariableCredentialsProvider() ) 
    			.build();
        return amazonDynamoDB;
    }
 
}
