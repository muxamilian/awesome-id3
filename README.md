This file lies in the root directory and its name is "README.md"
Language is Markdown...

# TODO

* Not very Model-View-Container-ish
* Tree on the left is buggy (at least on Mac OS X)
  * When clicking onto a node multiple times
  * It only shows the "folder icon" if you hover over a node
  * When clicking on a file, its ID3 stuff should be displayed on the right
  
# Und noch ein paar Sachen auf Deutsch... ;)
* ich fände eine Klasse MusicLibrary sinnvoll, die eine Referenz auf den Musikordner hält (das gehört nicht in die View wie jetzt) und für XML-Caching und Suche innerhalb des Baums zuständig ist, das konkrete Objekt würde man sich vom Controller holen über ein static getMusicLibrary()
* der Baum lässt sich noch verschönern, z.B. mit schöneren Icons
* evt. ein Save-Button, der die Änderungen an der einzelnen Datei sichert
* der Image-Container ist noch ausbaufähig, u.a. 
  * Aussehen, 
  * Platzierung und 
  * Autom. Skalierung für große Cover.
* vieles mehr