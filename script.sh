#!/bin/bash
function pause(){
   read -p "$*"
}
BOA_ROOT=$PWD
BOA_SERVER="$BOA_ROOT/BusOnAirServer"
BOA_WEB="$BOA_ROOT/BusOnAirWeb"
BOA_TAR="$BOA_ROOT/Tar"
NEO4J_HOME="$BOA_ROOT/neo4j"
NEO4J_DATA="$BOA_ROOT/neo4jData"
NEO4J_VERSION="1.8.M03"
export MAVEN_OPTS=-Xmx512m 
# NEO4J_PIDFILE="$NEO4J_HOME/data/neo4j-service.pid" genera pid non esatti

while true; do
    clear
    cd $BOA_ROOT
    PID=0
    echo "
        1   Compila BoaServer
        2   Inizializza neo4j
        3   Avvia neo4j
        4   Stop neo4j
        5   Importa i dati
        6   Avvia il BoaWebServer
        7   Stop BoaWebServer
    "
    echo -n "Fai la tua scelta: "
    read INPUT # Read user input and assign it to variable INPUT
 
    case $INPUT in
    1)  
        cd $BOA_SERVER
        mvn clean install
        pause 'Press [Enter] key to continue...'
        ;;
    2)  
        # if [ -f $NEO4J_PIDFILE ]              #funzione da usare nel momento in cui il pid diventa un bravo pid
        # then 
        #     PID=`cat $NEO4J_PIDFILE`
        # else 
        #     PID=0
        # fi
        
        if [ ! -d $NEO4J_HOME ]
        then
            if [ -f $BOA_TAR/neo4j-community-$NEO4J_VERSION-unix.tar.gz ]
            then
                tar xvzf $BOA_TAR/neo4j-community-$NEO4J_VERSION-unix.tar.gz
            else
                curl http://dist.neo4j.org/neo4j-community-$NEO4J_VERSION-unix.tar.gz | tar xvzf -
            fi
            mv neo4j-community-$NEO4J_VERSION neo4j
        fi
        if [ ! -d $NEO4J_DATA ]
        then
            if [ -f $BOA_TAR/neo4jData.tar.gz ]
            then
                tar xvzf $BOA_TAR/neo4jData.tar.gz
            else
                curl https://dl.dropboxusercontent.com/s/yifbg9ycucjgwg0/neo4jData.tar.gz | tar xvzf -
            fi
        fi

        PID=$(ps ax | grep -v grep | grep  neo4j | awk '{print $1}')
        if [ -n "$PID" ]
        then
            echo "$PID is running"
            sudo $NEO4J_HOME/bin/neo4j stop
        fi
        sudo rm -r $NEO4J_HOME/data/graph.db/*
        sudo rm -r $NEO4J_HOME/data/log/*
        sudo rm -r $NEO4J_HOME/data/keystore
        sudo rm -r $NEO4J_HOME/data/rrd
        sudo rm -r $NEO4J_HOME/plugins/*
 
        echo "Copying DB & Plugins to NEO4J_HOME"
        sudo chmod -R 755 $NEO4J_DATA
        sudo chmod -R 755 $NEO4J_HOME
        cp -a $BOA_ROOT/neo4jData/data/ $NEO4J_HOME/data/
        cp -a $BOA_SERVER/target/plugins/* $NEO4J_HOME/plugins/
        echo "org.neo4j.server.webserver.address=0.0.0.0" >>  $NEO4J_HOME/conf/neo4j-server.properties
        echo "org.neo4j.server.thirdparty_jaxrs_classes=boa.server=/plugin" >>  $NEO4J_HOME/conf/neo4j-server.properties
        echo "cache_type=strong" >>  $NEO4J_HOME/conf/neo4j.properties
        sudo chmod -R 755 $NEO4J_HOME
        pause 'Press [Enter] key to continue...'
        ;;

    3)  
        PID=$(ps ax | grep -v grep | grep  neo4j | awk '{print $1}')
        if [ -n "$PID" ]
        then
            echo "$PID is running"
            sudo $NEO4J_HOME/bin/neo4j stop
        fi
        sudo $NEO4J_HOME/bin/neo4j start
        pause 'Press [Enter] key to continue...'
        ;;

    4)  
        PID=$(ps ax | grep -v grep | grep  neo4j | awk '{print $1}')
        if [ -n "$PID" ]
        then
            echo "$PID is running"
            sudo $NEO4J_HOME/bin/neo4j stop
        fi
        pause 'Press [Enter] key to continue...'
        ;;

    5)  
        cd ./import
        ./testImporter.sh
        cd $BOA_ROOT
        pause 'Press [Enter] key to continue...'
        ;;
    6) 
        cd $BOA_WEB
        
        if (( $(ps ax | grep -v grep | grep  [a]pp.js | wc -l) > 0 )); then
            echo "Web Server is running"
            ps -ax | grep -v grep | grep  [a]pp.js | awk '{print $1}' | xargs kill -9 > /dev/null
            echo "Web Server install OFF"
        else
          echo "Web Server is not running"
        fi
        npm install 
        node app.js&
            echo "Web Server is ON"
        cd $BOA_ROOT
        pause 'Press [Enter] key to continue...'
       ;;

    7)
        cd $BOA_WEB

        if (( $(ps ax | grep -v grep | grep  [a]pp.js | wc -l) > 0 )); then
            echo "Web Server is running"
            ps -ax | grep -v grep | grep  [a]pp.js | awk '{print $1}' | xargs kill -9 > /dev/null
            echo "Web Server install OFF"
        else
          echo "Web Server is not running"
        fi
        pause 'Press [Enter] key to continue...'
       ;;

    *) echo "opzione non valida"
        pause 'Press [Enter] key to continue...'
       ;;
    esac
done