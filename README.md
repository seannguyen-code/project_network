# Project Network

A collection of Java and JavaFX applications demonstrating networked chat, quiz games, and console-based communication.

## Features

- **JavaFX Quiz Game**: Interactive quiz game with GUI, multiple screens, and user sign-up.
- **Multi-Client Chat**: JavaFX-based chat application supporting multiple clients.
- **Console Chat App**: Simple console-based chat server and client.
- **Project Console**: Additional console-based networking examples.

## Folder Structure

```
src/
  sample/
	 Main2.java
	 chatMultiClient/
		Client.java
		Server.java
	 consoleChatApp/
		Client.java
		Server.java
	 Main3/
		Main.java
		... (controllers, FXML, images)
	 projectConsole/
		Client.java
		Server.java
		TestZone.java
```

## Requirements

- Java 8 or higher
- JavaFX SDK (for GUI applications)

## How to Run

### JavaFX Quiz Game

1. Compile:
	```zsh
	javac -cp . src/sample/Main3/*.java
	```
2. Run (replace `/path/to/javafx-sdk/lib` with your JavaFX SDK path):
	```zsh
	java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp src/sample/Main3 Main
	```

### Multi-Client Chat (JavaFX)

1. Compile:
	```zsh
	javac -cp . src/sample/chatMultiClient/*.java
	```
2. Run server:
	```zsh
	java -cp src/sample/chatMultiClient Server
	```
3. Run client (in separate terminal):
	```zsh
	java -cp src/sample/chatMultiClient Client
	```

### Console Chat App

1. Compile:
	```zsh
	javac -cp . src/sample/consoleChatApp/*.java
	```
2. Run server:
	```zsh
	java -cp src/sample/consoleChatApp Server
	```
3. Run client:
	```zsh
	java -cp src/sample/consoleChatApp Client
	```

## Notes

- For JavaFX apps, ensure your JavaFX SDK is installed and referenced in your run command.
- Images and FXML files are located in `src/sample/Main3/images/` and `src/sample/Main3/`.

## License

Specify your license here.
