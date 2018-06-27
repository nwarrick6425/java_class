/** create the stocks database */

DROP TABLE IF EXISTS quotes CASCADE;
CREATE TABLE quotes(
   id INT NOT NULL AUTO_INCREMENT,
   symbol VARCHAR(4) NOT NULL,
   time DATETIME NOT NULL,
   price DECIMAL NOT NULL,
   PRIMARY KEY ( id )
);

INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2004-08-19 00:00:01','85.00');
INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2015-02-03 00:00:01','527.35');
INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2015-02-04 00:00:01','535.29');
INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2015-02-05 00:00:01','536.20');
INSERT INTO quotes (symbol,time,price) VALUES ('GOOG','2015-02-06 00:00:01','540.37');
INSERT INTO quotes (symbol,time,price) VALUES ('APPL','2000-01-01 00:00:01','118.27');
INSERT INTO quotes (symbol,time,price) VALUES ('APPL','2000-01-02 00:00:01','119.39');
INSERT INTO quotes (symbol,time,price) VALUES ('APPL','2000-01-03 00:00:01','121.32');
INSERT INTO quotes (symbol,time,price) VALUES ('APPL','2000-01-04 00:00:01','125.90');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-03 00:00:01','363.21');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-04 00:00:01','365.90');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-05 00:00:01','367.31');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-06 00:00:01','366.89');
INSERT INTO quotes (symbol,time,price) VALUES ('AMZN','2015-02-07 00:00:01','375.62');
