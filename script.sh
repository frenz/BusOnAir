#!/bin/bash
function pause(){
   read -p "$*"
}
BOA_ROOT=$PWD
BOA_SERVER="$BOA_ROOT/BusOnAirServer"
BOA_WEB="$BOA_ROOT/BusOnAirWeb"
NEO4J_HOME="$BOA_ROOT/neo4j"
NEO4J_DATA="$BOA_ROOT/neo4jData"
NEO4J_VERSION="1.8.M03"
export MAVEN_OPTS=-Xmx512m 
NEO4J_PIDFILE="$NEO4J_HOME/neo4j-service.pid"

while true; do
    clear
    cd $BOA_ROOT
    echo "
        1   Compila BoaServer
        2   Inizializza neo4j
        3   Importa i dati
        4   Avvia il BoaWebServer
        5   Stop BoaWebServer
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
        if [ -f $NEO4J_PIDFILE ]
        then 
            PID=`cat $NEO4J_PIDFILE`
        else 
            if ps -ax | grep -v grep | grep  [n]eo4j | wc -l > /dev/null
            then
                echo "Neo4j is running"
                ps -ax | grep -v grep | grep [n]eo4j | awk '{print $1}' | xargs sudo kill -9
                echo "WNeo4j is OFF"
            fi
        fi
        
        if [ ! -d $NEO4J_HOME ]
        then
            curl http://dist.neo4j.org/neo4j-community-$NEO4J_VERSION-unix.tar.gz | tar xvzf -
            mv neo4j-community-$NEO4J_VERSION neo4j
        fi
        if [ ! -d $NEO4J_DATA ]
        then
            curl https://dl.dropboxusercontent.com/s/yifbg9ycucjgwg0/neo4jData.tar.gz | tar xvzf -
        fi

        if ps -p $PID > /dev/null
        then
            echo "$PID is running"
            sudo $NEO4J_HOME/bin/neo4j stop
        fi
        sudo rm -r $NEO4J_HOME/data/graph.db/*\
            $NEO4J_HOME/data/log/*\
            $NEO4J_HOME/data/keystore\
            $NEO4J_HOME/data/rrd\
            $NEO4J_HOME/plugins/*
 
        echo "Copying DB & Plugins to NEO4J_HOME"
        sudo chmod 777 $NEO4J_DATA -R
        sudo chmod 777 $NEO4J_HOME -R
        cp -a $BOA_ROOT/neo4jData/data/ $NEO4J_HOME/data/
        cp -a $BOA_SERVER/target/plugins/* $NEO4J_HOME/plugins/
        echo "org.neo4j.server.thirdparty_jaxrs_classes=boa.server=/plugin" >>  $NEO4J_HOME/conf/neo4j-server.properties
        echo "cache_type=strong" >>  $NEO4J_HOME/conf/neo4j.properties
        sudo chmod 777 $NEO4J_HOME -R
        sudo $NEO4J_HOME/bin/neo4j start
        pause 'Press [Enter] key to continue...'
        ;;

    3)  
        cd ./import
        ./testImporter.sh
        cd $BOA_ROOT
        pause 'Press [Enter] key to continue...'
        ;;
    4) 
        cd $BOA_WEB
        
        if ps -ax | grep -v grep | grep  [a]pp.js | wc -l > /dev/null
        then
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

    5)
        cd $BOA_WEB
        if ps -ax | grep -v grep | grep  [a]pp.js | wc -l > /dev/null
        then
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