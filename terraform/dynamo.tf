resource "aws_dynamodb_table" "retoure-dynamodb-table" {
  name = "Retoure"
  read_capacity = 5
  write_capacity = 5
  hash_key = "CustomerId"
  range_key = "OrderId"

  attribute {
    name = "CustomerId"
    type = "S"
  }

  attribute  {
    name = "OrderId"
    type = "S"
  }
}

