Zu dem Projekt
**************

Matrisk ist eine Android-Implementierung des Spiels "Risiko".

Der Spieler kämpft auf einer fiktiven Landkarte gegen einen Bot. Die Länder 
sind in Kontinente gruppiert, welche miteinander verbunden sind.


Spielregeln
***********

Ziel des Spiels: Die Spieler müssen alle Länder auf einer Spielkarte erobern. 

Ablauf:

1)	Initialisierung:
	Zuerst werden die Länder auf der Karte zufällig auf die Spieler verteilt. Es
	wird dabei darauf geachtet, dass die Länder gleichmässig verteilt werden. 
	Könnten nicht alle Länder gleichmässig auf die Spieler verteilt werden, so 
	wird der Rest (Anzahl Länder % Anzahl Spieler) zufällig verteilt.

	Nach der Initialisierung wird abwechselnd gespielt. In dieser Version von 
	MatRisk beginnt der Benutzer der Applikation immer zuerst. (Bei einer
	Erweiterung der Applikation mit einem Multiplayer-Modus, müsste die
	Reihenfolge in der Initialisierungsphase zufällig erhoben werden und
	benachteiligte Spieler mit zusätzlichen Truppen kompensiert werden).

2)	Runden-basierte Hauptphase:
	Ist ein Spieler an der Reihe so hat er drei Phasen zu durchspielen:

	a) Verstärkung: 
		Hier erhält der Spieler basierend auf der Anzahl Länder und Kontinente 
		(wenn ein Spieler alle Länder eines Kontinents kontrolliert) Verstärkung
		in Form von neuen Truppen. Diese Truppen kann er frei auf alle Länder
		unter seiner Kontrolle verteilen. Die Verstärkung wird in dieser Version
		wie folgt berechnet: Truppen = (Länder/2  + Kontinente*3)
	
	b) Angriff:
		Nach der Verstärkung kann der Spieler gegnerische Länder angreifen.
		Dafür wählt er ein Ausgangsland unter seiner Kontrolle und ein
		gegnerisches Zielland aus.
		Es kann ein Ausgangsland nur auswählen, wenn er dort mehr als eine 
		Truppeneinheit dort stationiert hat, das Zielland seines Angriffs muss 
		direkt benachbart sein.

		Danach wählt der Spieler die Anzahl Truppen aus, mit denen er einen 
		Angriff durchführen will. Er kann mit mindestens mit einer 
		Truppeneinheit und maximal mit der Anzahl Truppen minus 1, welche auf
		dem Ausgangsland stationiert sind, angreifen. Dies weil er mindestens 
		eine Truppeneinheit zurücklassen muss.
		Der Verteidiger im Zielland verteidigt immer mit der vollen Anzahl 
		Truppen in seinem Land.
		
		Sind Angriffs- und Verteidigungsland sowie die Anzahl angreifender
		Truppen festgelegt so wird ausgewürfelt.
		Der Angreifer hat maximal drei Würfel zum kämpfen, wenn er drei oder 
		mehr Truppen im Angriff zur Verfügung hat. Ansonsten ist die Anzahl 
		Würfel Analog der Anzahl Truppen.
		Der Verteidiger hat maximal zwei Würfel zum kämpfen, ansonsten wird die 
		Anzahl Würfel analog wie beim Angreifer berechnet.
		
		Ist die Anzahl Würfel festgelegt, werden diese nun gerollt. 
		Danach werden die Ergebnisse Paarweise verglichen: 
		
		Höchster Würfel Angreifer 		<->  Höchster Würfel Verteidiger
		Zweit-höchster Würfel Angreifer	<->  Zweit-höchster Würfel Verteidiger
		
		(Das zweite Paar gibt es nur wenn Angreifer und Verteidiger mehr
		als je ein Würfel bzw. eine Truppe haben)

		Hat in einem Paar der Angreifer ein höheres Ergebnis als der 
		Verteidiger, so verliert der Verteidiger eine Truppe. Hat der 
		Verteidiger ein gleiches oder höheres Ergebnis als der Angreifer, so 
		verliert der Angreifer eine Truppe. Pro Wurf können somit maximal zwei
		Truppen verloren werden. Es ist auch möglich, dass beide Seiten je eine
		Truppe verlieren
		
		Folgende drei Beispiele sollen dies verdeutlichen. Der Angreifer hat 
		jeweils 3 oder mehr Truppen, der Verteidiger 2 oder mehr Truppen. 
		Daher wird immer mit der maximalen Anzahl an Würfeln gespielt:

		*****************************************************
		*	A   :   V				    *
		*	---------				    *
		*	5   |   4				    *
		*	4   |   3				    *
		*	1   |   -	--> V verliert 2 Truppen    *
		*						    *
		*****************************************************
		*	A   :   V				    *
		*	---------				    *
		*	6   |   6				    *
		*	4   |   5				    *
		*	4   |	-	--> A verliert 2 Truppen    *
		*						    *
		*****************************************************
		*	A   :	V				    *
		*	---------				    *
		*	4   |	3				    *
		*	2   |	3				    *
		*	1   |	-	--> A verliert 1 Truppe     *
		*			    V verliert 1 Truppe	    *
		*						    *
		*****************************************************

		Der Angriff kann im normalen Modus jederzeit abgebrochen (->withdraw) 			
		werden. 
		Es gibt für den Benutzer auch die Möglichkeit im Schnelldurchlauf
		(-> Fast Attack) anzugreifen. Dabei werden die einzelnen Würfel- 
		Ergebnisse dem Spieler nicht gezeigt und er kann dabei seinen Angriff
		nicht abbrechen.

		Pro Angriffsphase kann der Spieler so oft angreifen wie er will oder
		auch keinen Angriff starten

	c)Truppenverschiebung:
		Die letzte Phase in einem Spielzug ist die Truppenverschiebung. Dabei 
		kann ein Spieler Truppen von einem Land in ein anderes Land verschieben.
		Voraussetzung dafür ist, dass beide Länder sowie die Länder dazwischen
		unter seiner Kontrolle sind. Ausgangs und Zielland der Verschiebung 
		müssen also entweder direkt benachbart oder indirekt durch Länder
		verbunden sein.
		In einem Spielzug kann ein Spieler maximal nur eine Truppenverschiebung 
		durchführen.

	Gespielt wird abwechslungsweise bis jemand alle Länder auf der Karte unter 
	seine Kontrolle gebracht hat.

Aufbau Applikation:
*******************

Der Teil der Applikation, welcher sich um den Spielablauf und die Interaktion 
mit dem Benutzer kümmert ist in Kotlin, die künstliche Intelligenz des Gegners 
ist in Java geschrieben. Weitere Informationen zu Klassen sind jeweils in 
den Kommentaren des Sourcecodes zu finden. 


Paket minimalrisk
*****************

Das Paket minimalrisk enthält eine Implementierung des Spiels ohne grafische 
Benutzeroberfläche. Dieser Teil ermöglicht es einem Anwender, gegen den 
Computer zu spielen.

Die Spielzüge sind zufällig generierte zulässige Spielzüge und sind nicht 
optimiert.

Die MinimalRisk-Klasse implementiert ein API mit statischen Methoden. 
An diese Methoden wird ein JSON-String mit einer Repräsentation des 
Spielzustands übergeben, und der Rückgabewert ist in fast allen Fällen 
entweder ein JSON-String mit einer Repräsentation des neuen Spielzustands 
oder ein JSON-String mit einer Länderliste.

Da alle Methoden statisch sind und zwischen aufeinanderfolgenden 
Methodenaufrufen kein Objektzustand verwaltet wird, ist diese Klasse 
auch als Basis für ein einfaches zustandsloses Web-API geeignet. 
So könnte in einem Folgeprojekt ein Multi-Player-Modus implementiert werden.

Die Verwendung von JSON hat aber auch für die Verwendung in einer nicht 
verteilten Anwendung Vorteile:

- Der Teil des Projekts, der die MinimalRisk-Klasse nutzt, ist 
  von der Implementierung der Datenstrukturen in dieser Klasse vollkommen 
  entkoppelt.
- Die Konfiguration des Spielfelds (Länder, Kontinente, Verbindungen) 
  kann in einer JSON-Konfigurationsdatei abgelegt und direkt eingelesen 
  werden. 
 
Für die Umwandlung zwischen Java-Ojekten und JSON wurde die Gson-Bibliothek 
von Google verwendet. Das Paket minimalrisk enthält hierfür Template-
Klassen, die die Datenstruktur definieren.

Die Implementierung des Graphenmodells und der Suche eines kürzesten Pfads 
(Breitensuche) orientieren sich an in einem Online-Kurs des MIT 
(MITx - 6.00.2x) vorgestellten Beispielen in Python.

