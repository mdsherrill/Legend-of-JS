@echo off
cls
javac Game.java View.java Controller.java Model.java Json.java Sprite.java Link.java Tile.java Pot.java Boomerang.java
if %errorlevel% neq 0 (
	echo There was an error; exiting now.	
) else (
	echo Compiled correctly!  Running Game...
	java Game	
)