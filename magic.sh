#!/bin/sh
rm -Rf dist;
rm -f MANIFEST.MF;
mkdir dist;
mkdir dist/hmbawe_lib;
#cp lib/activation.jar dist/hmbawe_lib/;
#cp lib/junit-4.10.jar dist/hmbawe_lib/;
#cp lib/mail.jar dist/hmbawe_lib/;
cp lib/eclipselink/jlib/eclipselink.jar dist/hmbawe_lib/;
cp lib/eclipselink/jlib/jpa/javax.persistence_2.0.3.v201010191057.jar dist/hmbawe_lib/;
cp -R lib/* dist/hmbawe_lib/;
cd src;
javac -d ../bin -classpath .:../lib/activation.jar:../lib/junit-4.10.jar:../lib/mail.jar:../lib/eclipselink/jlib/eclipselink.jar:../lib/eclipselink/jlib/jpa/javax.persistence_2.0.3.v201010191057.jar:../lib/jxl.jar:../lib/ojdbc6.jar fr/unice/hmabwe/vue/FenetrePremiere.java;
echo "copie du reste des fichiers";
cp -R META-INF ../bin/;
cp -R resource ../bin/;
cd ../bin;
echo "creation MANIFEST.MF"
echo "Manifest-Version: 1.0" > META-INF/MANIFEST.MF;
echo "Class-Path: . hmbawe_lib/junit-4.10.jar hmbawe_lib/jxl.jar hmbawe_lib/ojdbc6.jar hmbawe_lib/mail.jar hmbawe_lib/activation.jar hmbawe_lib/javax.persistence_2.0.3.v201010191057.jar hmbawe_lib/eclipselink.jar" >> META-INF/MANIFEST.MF;
echo "Main-Class: fr.unice.hmabwe.vue.FenetrePremiere" >> META-INF/MANIFEST.MF;
echo "" >> META-INF/MANIFEST.MF;
echo "Lancement de l'application:";
#java -classpath ../lib/activation.jar:../lib/junit-4.10.jar:../lib/mail.jar:../lib/jxl.jar:../lib/ojdbc6.jar:../lib/eclipselink/jlib/eclipselink.jar:../lib/eclipselink/jlib/jpa/javax.persistence_2.0.3.v201010191057.jar:. fr.unice.hmabwe.vue.FenetrePremiere
echo "creation du jar final";
jar cmf META-INF/MANIFEST.MF hmabwe.jar *;
cp hmabwe.jar ../dist;
cd ../dist;
java -jar hmabwe.jar;