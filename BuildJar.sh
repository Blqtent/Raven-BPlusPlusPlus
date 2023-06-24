#!/bin/bash

clear
echo "A Java 8 installation is required to build."
echo "If you have a Java 8 installation, but your JAVA_HOME is set to something else,"
echo "please set it to your Java 8 installation."
echo
echo "Current JAVA_HOME version:"
java -version
echo
echo "Please only continue if your Java version is 8."
echo
read -rsp $'Press any key to continue...\n' -n1 key
clear
echo "You can find the JAR file in 'build/libs' once the build is complete."
echo
read -rsp $'Press any key to start the build...\n' -n1 key
clear
chmod +x gradlew
./gradlew clean build
echo
read -rsp $'Press any key to exit...\n' -n1 key
