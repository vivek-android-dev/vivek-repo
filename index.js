var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');

var con = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'node_mysql_demo'
});

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended:true}));

app.listen(3000, () => {
    console.log('Running on port 3000..');
});

app.post('/register/', (req, res, next) => {
    // var post_data = req.body;
    var name = req.query.name;
    var email = req.query.email;
    var password = req.query.password;

    

    con.query('SELECT * FROM user WHERE email = ?', [email], function(error, result, fields) {
        if (error) {
            console.log(error);
            return res.status(200).json({ message: error });
            // Using status code 500 for Internal Server Error
        }
        if (result.length > 0) {
            return res.status(200).json({ message: 'User already exists' });
            // Using status code 409 for Conflict
        } else {
            con.query('INSERT INTO `user`(`name`, `email`, `password`) VALUES (?, ?, ?)', [name, email, password], function(error, result, fields) {
                if (error) {
                    console.log('Error:', error);
                    return res.status(200).json({ message: error });
                    // Using status code 500 for Internal Server Error
                }
                return res.status(200).json({ message: 'Registration successful' });
                // Using status code 201 for Created
            });
        }
    });
});

app.post('/login/', (req, res, next) => {
    var email = req.query.email;
    var password = req.query.password;

    con.query('SELECT * FROM user WHERE email = ? AND password = ?', [email, password], function(error, result, fields) {
        if (error) {
            console.log(error);
            return res.status(200).json({ message: error });
            // Using status code 500 for Internal Server Error
        }
        if (result.length === 0) {
            return res.status(200).json({ message: 'Invalid email or password' });
            // Using status code 401 for Unauthorized
        } else {
            return res.status(200).json({ message: 'Login successful' });
            // Using status code 200 for OK
        }
    });
});

app.post('image',(req,res,next)=>{

})