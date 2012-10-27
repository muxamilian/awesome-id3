This file lies in the root directory and its name is "README.md"
Language is Markdown...

Using this documentation: [http://id3.org/id3v2.3.0]

# TODO

* Not very Model-View-Controller-ish
  
## Und noch ein paar Sachen auf Deutsch... ;)
* ich fände eine Klasse MusicLibrary sinnvoll, die eine Referenz auf den Musikordner hält (das gehört nicht in die View wie jetzt) und für XML-Caching und Suche innerhalb des Baums zuständig ist, das konkrete Objekt würde man sich vom Controller holen über ein static getMusicLibrary()
* ~~der Baum lässt sich noch verschönern, z.B. mit schöneren Icons~~
* evt. ein Save-Button, der die Änderungen an der einzelnen Datei sichert
* der Image-Container ist noch ausbaufähig, u.a. 
  * Aussehen, 
  * Platzierung und 
  * Autom. Skalierung für große Cover.
  * MAX: minimal verbessert durch zweites Panel drumrum. Ist aber verbuggt wie alles in Swing
* Wir können das ID3 Tag schreiben und lesen auch gleich implementieren, ich bin für ID3 v2.2 (zwar nicht ganz aktuell aber einfach zu implementieren ;) )
Yannick: Das wird nix, die Projektbeschreibung sieht Version 2.3 vor, die aber auch nicht schwer zu implementieren ist.