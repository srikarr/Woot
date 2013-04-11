#Woot - Audio Indexing Project
A project inspired by Roy van Rijn and the course Web Intelligence and Big Data.

##About
This project aims to expand on the work of Roy van Rijn and create a database using the techniques learned in the course Web Intelligence and Big Data.

##Links

* https://class.coursera.org/bigdata-002/class/index [CourseRA - Web Intelligence and Big Data]
* http://www.redcode.nl/blog/2010/06/creating-shazam-in-java/ [Audio Indexing in Java by Roy van Rijn]
* http://laplacian.wordpress.com/2009/01/10/how-shazam-works/ [Simple overview of how shazam works]
* http://www.ee.columbia.edu/~dpwe/papers/Wang03-shazam.pdf [Research paper on how shazam works]

##Todo
* Add event listener for audioData updates
* Display audioData on JavaFX Line Graph
* Maybe clean up the display and add interpolation
* Do A FFT to get audio spectrum data
* Create spectrum analyzer display
* Implement "interesting data" logic... basically highest magnitude for certain frequency ranges within a given time slice
* Implement simple hashing based on Roy van Rijn's work
* Mock audio data
* Create unit tests (test hash lookups of mocked audio data)
* Use LSH (Locality Sensitive Hashing) to pick values to hash
* Use combinatorics to pick values to hash
* Use SDM (Sparse Distributed Memory) to pick values to hash
* Persist to DB with Hibernate (Database will be determined in class)
