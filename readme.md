# Individual TimeGuessr
A very rudimentary implementation of the game TimeGuessr (https://timeguessr.com), where it is possible to add your own pictures to play with friends and colleagues.

## Adding your own images
- Store the images in .jpg format in the src/main/resources/static/images folder
- Add the images to the src/main/resources/imageinfos/images.json file as explained in the example

## Compile
- Execute _mvnw[.cmd on windows] clean package_

## Execute/Play
- run the compiled .jar with Java 21
- open _localhost:8080[or defined port]/guesspage_ in your web browser
