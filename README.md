# Retouren-Service-lambda-terraform
This is the prototype project for the retouren-service as a lamda function in aws 
# java-lambda-terraform
Creating Java Lambda in aws using terraform

Run the following gradle commands to build jar.
```
mvn clean package shade:shade
```  

Run the following terraform commands to deploy our code to AWS.
```
terraform init
terraform plan
terraform apply
```
