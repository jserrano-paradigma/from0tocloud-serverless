"use strict";
const AWS = require("aws-sdk");
const docClient = new AWS.DynamoDB.DocumentClient();
const config = require("../config.json");

// Function to getAllItems from DB
module.exports.getAllItems = async (event) => {
  let orderId = event.queryStringParameters.orderId;
  let table = config.TABLE_NAME;
  var params = {
    TableName: table,
    IndexName: "order_index",
    KeyConditionExpression: "orderId = :orderId",
    ExpressionAttributeValues: {
      ":orderId": orderId,
    },
  };

  try {
    let result = await docClient.query(params).promise();
    if (result.Items) {
      return {
        headers: { "Content-Type": "application/json" },
        statusCode: 200,
        body: JSON.stringify(result.Items),
      };
    } else {
      return {
        headers: { "Content-Type": "application/json" },
        statusCode: 204,
      };
    }
  } catch (error) {
    return {
      statusCode: 500,
      body: JSON.stringify({
        message: error.message,
      }),
    };
  }
};
