#!/bin/bash

sudo apt update
sudo apt install -y openjdk-18-jdk-headless openjdk-18-jre-headless wget unzip
wget https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip -O commandlinetools.zip
mkdir -p ~/android-sdk/cmdline-tools
unzip commandlinetools.zip -d ~/android-sdk/cmdline-tools
echo 'export ANDROID_SDK_ROOT=$HOME/android-sdk' >> ~/.bashrc
echo 'export ANDROID_HOME=$HOME/android-sdk' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/platform-tools' >> ~/.bashrc
echo 'export PATH=$PATH:$ANDROID_HOME/emulator' >> ~/.bashrc
echo 'export JAVA_HOME=/usr/lib/jvm/java-1.18.0-openjdk-amd64' >> ~/.bashrc
JAVA_HOME=/usr/lib/jvm/java-1.18.0-openjdk-amd64
ANDROID_SDK_ROOT=$HOME/android-sdk
ANDROID_HOME=$HOME/android-sdk
PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator
JAVA_HOME=/usr/lib/jvm/java-1.18.0-openjdk-amd64 ~/android-sdk/cmdline-tools/cmdline-tools/bin/sdkmanager --install "platform-tools" "cmdline-tools;latest"
JAVA_TOOL_OPTIONS="-XX:ActiveProcessorCount=16 -Xmx20g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.country=DE -Duser.language=de -Duser.variant=DE"
JAVA_HOME=/usr/lib/jvm/java-1.18.0-openjdk-amd64
export JAVA_TOOL_OPTIONS="-XX:ActiveProcessorCount=16 -Xmx20g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.country=DE -Duser.language=de -Duser.variant=DE"
export JAVA_HOME=/usr/lib/jvm/java-1.18.0-openjdk-amd64

JAVA_TOOL_OPTIONS="-XX:ActiveProcessorCount=16 -Xmx20g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8 -Duser.country=DE -Duser.language=de -Duser.variant=DE" ANDROID_HOME=$HOME/android-sdk JAVA_HOME=/usr/lib/jvm/java-1.18.0-openjdk-amd64 PATH=/home/gitpod/.local/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/lib/jvm/java-1.18.0-openjdk-amd64/bin:/home/gitpod/android-sdk/cmdline-tools/latest/bin:/home/gitpod/android-sdk/platform-tools:/home/gitpod/android-sdk/emulator ./gradlew init