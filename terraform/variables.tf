variable "region" {
  # set aws region
  default = "eu-central-1"
}

variable "lambda_payload_filename" {
  default = "target/retouren-service-0.0.1-SNAPSHOT.jar"
}

variable "lambda_function_handler" {
  default = "de.otto.retouren.controller.RetourenController"
}

variable "lambda_runtime" {
  default = "java8"
}

variable "api_path" {
  default = "retouren"
}

variable "api_env_stage_name" {
  default = "terraform-lambda-java-stage"
}
