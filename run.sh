#!/bin/bash
sed -i 's/mysql-exercise/localhost/' .env
$(awk '{print "export ",$0}' .env) 
env 
sed -i 's/localhost/mysql-exercise/' .env
java -jar target/encryption-1.0.0.jar -Dspring.profiles.active=default
