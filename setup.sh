#!/usr/bin/env sh

echo '======================================================================'
echo '          _                                              _            '
echo '       __| |  ___    ___  _   _  _ __ ___    ___  _ __  | |_          '
echo '      / _` | / _ \  / __|| | | || '_ ` _ \  / _ \| '_ \ | __|         '
echo '     | (_| || (_) || (__ | |_| || | | | | ||  __/| | | || |_          '
echo '      \__,_| \___/  \___| \__,_||_| |_| |_| \___||_| |_| \__|         '
echo '                                                                      '
echo ' _ __   _ __   ___   _ __    __ _   __ _   __ _ | |_ (_)  ___   _ __  '
echo '| '_ \ | '__| / _ \ | '_ \  / _` | / _` | / _` || __|| | / _ \ | '_ \ '
echo '| |_) || |   | (_) || |_) || (_| || (_| || (_| || |_ | || (_) || | | |'
echo '| .__/ |_|    \___/ | .__/  \__,_| \__, | \__,_| \__||_| \___/ |_| |_|'
echo '|_|                 |_|            |___/                              '
echo '                                                                      '
echo 'version: 0.0.1                                                        '
echo 'release date: 29-05-2019                                              '
echo '======================================================================

##clone project
git clone https://github.com/JimSP/document-propagation.git

##build project
cd document-propagation
./gradlew distDocker

##start microservices
docker-compose up -d

### open browser
x-www-browser http://localhost:8000/swagger-ui.html
