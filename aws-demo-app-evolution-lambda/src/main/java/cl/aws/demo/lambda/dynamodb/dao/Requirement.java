package cl.aws.demo.lambda.dynamodb.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Requirements")
public class Requirement {
	private String id;
	private String user;
	private String date;
	private String status;
	private String reason;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    @DynamoDBAttribute
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

    @DynamoDBAttribute
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

    @DynamoDBAttribute
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    @DynamoDBAttribute
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
