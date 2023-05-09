# CS308 Coursework Team 19

![image](https://user-images.githubusercontent.com/106979580/237048675-10791f73-60f8-4d10-9253-65c6a08acbb2.png)

## Welcome! 👋

This is Team 19's solution to the Graph Coursework.

The GraphADT project or Graph game is a simple one player game that prompts the player to find the correct distance between two nodes in a Graph. It has many useful features like difficulty, hints or even scoreboard, to make the game more enjoyable for any kind of player. The frontend is ReactJS-based and the backend uses Java.

This project has been done as part of our studies at the University of Strathclyde. To teach us the significance of abstraction and decoupling in software engineering.

Here is a [link to our Github repository](https://github.com/abahraoui/GraphADT) where you will be able to see all our commits.

## Running the project

Since our application is a web application, you need to run both the server and the client at the same time.

### Backend

Open up a terminal, navigate to the root folder of the project and then run the prebuilt JAR file provided by entering the following command. This will host the API on port 8080.

```
java -jar .\out\artifacts\GraphADT_Team19_jar\GraphADT_Team19.jar
```

Alternatively, if you would like to run the code directly and have `mvn` installed, you could run this.

```
mvn jooby:run
```

### Frontend

After having the server up, you should run the client server. This can be done using the following command **assuming having Node and NPM installed** on the system.

After this, open up [localhost:5173](http://127.0.0.1:5173/) in a web browser to see the project running.

Congratulation! You now have everthing set up! Have fun playing our game! 🎉

```
cd frontend
npm install
npm start
```

The project was developed and tested on Node version 16.18.0 and npm version 6.9.0.
