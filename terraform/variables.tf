variable "aws_access_key" {
  # set aws access key
  default = ""
}

variable "aws_secret_key" {
  # set aws secret key
  default = ""
}

variable "region" {
  # set aws region
  default = ""
}

variable "lambda_payload_filename" {
  default = "target/retouren-service-0.0.1-SNAPSHOT"
}

variable "lambda_function_handler" {
  default = "handler.LambdaHandler"
}

variable "lambda_runtime" {
  default = "java13"
}

variable "api_path" {
  default = "{proxy+}"
}

variable "api_env_stage_name" {
  default = "terraform-lambda-java-stage"
}
