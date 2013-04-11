#Woot - Audio Spectrogram Indexing "Big Data" Project
##About
A project inspired by CourseRA - Web Intelligence and Big Data

This project aims to expand on the work of Roy van Rijn http://www.redcode.nl/blog/2010/06/creating-shazam-in-java/ and create a database using the techniques learned in the course Web Intelligence and Big Data.

##TODO
* Add event listener for audioData updates
* Display audioData on JavaFX Line Graph
* Maybe clean up the display and add interpolation
* Do A FFT to get Spectrogram data
* Create spectrum analyzer display
* Implement "interesting data" logic... basically highest magnitude for certain frequency ranges within a given time slice
* Implement simple hashing based on Roy van Rijn's work
* Mock audio data
* Create unit tests
* Use LSH to pick values to hash
* Use combinatorics to pick values to hash
* Persist to DB with Hibernate (Database will be determined in class)
