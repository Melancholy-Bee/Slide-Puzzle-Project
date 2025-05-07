# Slide-Puzzle-Project
Contributors: Maura Blazek, Kason Adsit, Landon Armstrong, Emma Schwarzrock, Sylas Mirabal-Smalley, Rebecca Geary

A slider puzzle game for COSC 3011. It's a desktop application where users can choose an image and solve a slider puzzle with it.


To compile, make sure you are in the root directory. Then run these commands: 

Windows:
javac -classpath ".;sqlite-jdbc-3.49.1.0.jar" PictureThis.java

java -classpath ".;sqlite-jdbc-3.49.1.0.jar" PictureThis.java

Note: Depending on your operating system, you may need to replace the semicolons(;) with colons(:) 

Mac:
javac -classpath ".:sqlite-jdbc-3.49.1.0.jar" PictureThis.java

java -classpath ".:sqlite-jdbc-3.49.1.0.jar" PictureThis.java
