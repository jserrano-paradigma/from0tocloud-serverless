"use strict";
const AWS = require("aws-sdk");
const docClient = new AWS.DynamoDB.DocumentClient();
const config = require("../config.json");

// Function to update an Item in DB
module.exports.updateItem = async (event) => {
  let body = JSON.parse(event.body);
  let id = event.pathParameters.id;
  let table = config.TABLE_NAME;
  let params = {
    TableName: table,
    Key: {
      id: id,
    },
    UpdateExpression: "set orderStatus = :s, description=:d, updateDate=:u",
    ExpressionAttributeValues: {
      ":s": body.status,
      ":d": body.description,
      ":u": body.updateDate,
    },
    ReturnValues: "UPDATED_NEW",
  };
  try {
    let result = await docClient.update(params).promise();
    console.log(">>>>>>>>> Update Result: ", result);
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
