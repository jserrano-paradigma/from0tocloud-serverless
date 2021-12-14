"use strict";
const AWS = require("aws-sdk");
const docClient = new AWS.DynamoDB.DocumentClient();
const config = require("../config.json");

// Function to Delete an item
module.exports.deleteItem = async (event) => {
  let table = config.TABLE_NAME;
  let id = event.pathParameters.id;
  let params = {
    TableName: table,
    Key: {
      id: id,
    },
  };
  try {
    let result = await docClient.delete(params).promise();
    return {
      headers: { "Content-Type": "application/json" },
      statusCode: 204,
    };
  } catch (error) {
    return {
      statusCode: 500,
      body: JSON.stringify({
        status: "FAIL",
        message: error.message,
      }),
    };
  }
};
