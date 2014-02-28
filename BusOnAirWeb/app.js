/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , http = require('http')
  , path = require('path');
var app = express();
var neo4jURL='http://localhost:7474/plugin';
var boaURL='http://localhost:8080/';
// Configuration



app.configure(function(){
  app.set('port', process.env.PORT || 8080);
  app.set('background', true);
  app.set('views', __dirname + '/views');
  app.set('view engine', 'jade');
  app.use(express.favicon());
  app.use(express.logger('dev'));
  app.use(express.bodyParser());
  app.use(express.methodOverride());
  app.use(express.cookieParser());
  app.use(express.session({ secret: 'your secret here' }));
  app.use(app.router);
  app.use(express.static(path.join(__dirname, 'public')));
});

app.configure('development', function(){
  app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
});

app.configure('production', function(){
  app.use(express.errorHandler());
});

// Routes

app.get('/', routes.index);
app.get('/home', routes.home);



app.get(/\/directions\/([^\/]+)\/?/, function (req, res) {
   http.get(neo4jURL+"/directions/"+req.params[0], function(response) {
    console.log("Got response: " + response.statusCode);
    response.pipe(res);
  }).on('error', function(e) {
    console.log("Got error: " + e.message);
    
    res.send(e.message)
  });
});

app.get(/\/stations\/([^\/]+)\/?/, function (req, res) {
   http.get(neo4jURL+"/stations/"+req.params[0], function(response) {
    console.log("Got response: " + response.statusCode);
    response.pipe(res);
  }).on('error', function(e) {
    console.log("Got error: " + e.message);
    
    res.send(e.message)
  });
});

app.get(/\/routesearch\/([^\/]+)\/?/, function (req, res) {
   http.get(neo4jURL+"/routesearch/"+req.params[0], function(response) {
    console.log("Got response: " + response.statusCode);
    response.pipe(res);
  }).on('error', function(e) {
    console.log("Got error: " + e.message);
    
    res.send(e.message)
  });
});


http.createServer(app).listen(app.get('port'), function(){
  console.log("Express server listening on port " + app.get('port'));
});