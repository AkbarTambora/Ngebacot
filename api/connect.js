import mysql from "mysql"

export const db = mysql.createConnection({
    host: "aws-bacot.cfai24megohk.us-east-1.rds.amazonaws.com",
    user: "admin",
    password: "Anna302829",
    database: "ngdb"
})