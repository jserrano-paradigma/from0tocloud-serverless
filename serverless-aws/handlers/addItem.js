"use strict";
const AWS = require("aws-sdk");
const idgen = require("idgen");
const docClient = new AWS.DynamoDB.DocumentClient();
const config = require("../config.json");

module.exports.addItem = async (event) => {
  let body = JSON.parse(event.body);
  let table = config.TABLE_NAME;
  let id = idgen(16);
  let updateDate = new Date().toISOString().slice(0, 10);
  let params = {
    TableName: table,
    Item: {
      id: id,
      updateDate: updateDate,
      description: body.description,
      orderStatus: body.orderStatus,
      orderId: body.orderId,
    },
  };
  try {
    let result = await docClient.put(params).promise();
    return {
      headers: { "Content-Type": "application/json" },
      statusCode: 201,
      body: JSON.stringify(params.Item),
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
