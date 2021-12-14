"use strict";
const AWS = require("aws-sdk");
const docClient = new AWS.DynamoDB.DocumentClient();
const config = require("../config.json");

// Function to getOneItems from DB
module.exports.getOneItem = async (event) => {
  let table = config.TABLE_NAME;
  let id = event.pathParameters.id;
  let params = {
    TableName: table,
    Key: {
      id: id,
    },
  };

  try {
    let result = await docClient.get(params).promise();
    if (result.Item) {
      return {
        headers: { "Content-Type": "application/json" },
        statusCode: 200,
        body: JSON.stringify(result.Item),
      };
    } else {
      return {
        headers: { "Content-Type": "application/json" },
        statusCode: 404,
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
