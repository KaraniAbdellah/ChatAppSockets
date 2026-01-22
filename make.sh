# Here Run JavaFx Application
# Compile
javac --module-path /home/abdellah/javafx-sdk-21.0.2/lib \
      --add-modules javafx.controls,javafx.fxml \
      -cp "chatAppJava/lib/*" \
      chatAppJava/src/Main.java \

# Run
java --module-path /home/abdellah/javafx-sdk-21.0.2/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp "chatAppJava/src:chatAppJava/lib/*" \
    Main