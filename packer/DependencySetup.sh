#!/bin/bash

#Install JAVA
JAVA_VERSION="17.0.9"
JAVA_DOWNLOAD_URL="https://download.oracle.com/java/17/archive/jdk-17.0.9_linux-x64_bin.tar.gz"

INSTALL_DIR="/opt/java"
sudo mkdir -p $INSTALL_DIR
sudo curl -L -O $JAVA_DOWNLOAD_URL
sudo tar -xzpvf jdk-${JAVA_VERSION}_linux-x64_bin.tar.gz -C $INSTALL_DIR
echo "export JAVA_HOME=$INSTALL_DIR/jdk-${JAVA_VERSION}" | sudo tee -a /etc/profile
echo "export PATH=\$PATH:\$JAVA_HOME/bin" | sudo tee -a /etc/profile
source /etc/profile
java -version
sudo rm jdk-${JAVA_VERSION}_linux-x64_bin.tar.gz

#Install Maven
MAVEN_VERSION="3.9.5"
INSTALL_DIR="/opt/maven"

sudo mkdir -p $INSTALL_DIR
DOWNLOAD_URL="https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz"

sudo wget $DOWNLOAD_URL -O $INSTALL_DIR/maven.tar.gz
sudo tar -xzvf $INSTALL_DIR/maven.tar.gz -C $INSTALL_DIR

sudo ln -s $INSTALL_DIR/apache-maven-$MAVEN_VERSION $INSTALL_DIR/latest

echo "export M2_HOME=$INSTALL_DIR/latest" | sudo tee -a /etc/profile
echo "export PATH=\$PATH:\$M2_HOME/bin" | sudo tee -a /etc/profile

source /etc/profile

mvn -version
sudo rm $INSTALL_DIR/maven.tar.gz

mkdir ~/opt
mv users.csv ~/opt/users.csv

#Install Cloud Watch Agent
mkdir ~/logs
touch ~/logs/webapp.log

wget https://amazoncloudwatch-agent.s3.amazonaws.com/debian/amd64/latest/amazon-cloudwatch-agent.deb
sudo dpkg -i -E ./amazon-cloudwatch-agent.deb
rm -f ./amazon-cloudwatch-agent.deb

sudo mv CloudWatchConfig.json /opt/aws/amazon-cloudwatch-agent/bin/CloudWatchConfig.json

sudo mv ~/webapp.service /etc/systemd/system/webapp.service
sudo mv ~/cloudwatch.service /etc/systemd/system/cloudwatch.service
sudo systemctl daemon-reload
sudo systemctl enable webapp.service
sudo systemctl enable cloudwatch.service
