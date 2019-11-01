# Retouren-Service-lambda-terraform
This is the prototype project for the retouren-service as a lamda function in aws 

Creating the Retouren Java Lambda in aws using terraform

From the root of the project run the ```build_and_deploy.sh``` script.
This compiles the Java application into the ```target``` directory
and runs Terraform to upload and configure the Lambda function, API Gateway and DynamoDB.

```
$ ./build_and_deploy.sh
```
You can also execute the steps manually.

Run the following mvn commands to build jar.
```
mvn clean package shade:shade
```  

Run the following terraform commands to deploy our code to AWS.
```
terraform init
terraform plan
terraform apply
```

# Hint for terraform and aws credentials

The provider block in "main.tf" specifies that we are deploying on AWS.
You also have the possibility to mention credentials that will be used for deploying here. If you have correctly set up the AWS CLI on your machine there will be default credentials in your .aws folder. If no credentials are specified, Terraform will use these default credentials.