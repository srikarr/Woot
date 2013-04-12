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
* Get audio data [DONE]
* Threading [DONE]
* JavaFX UI [DONE]
* Add event listener for audioData updates [DONE]
* Display audioData on JavaFX Line Graph [DONE]
* Curve fitting might be nice http://fxexperience.com/2012/01/curve-fitting-and-styling-areachart/
* Slider control to change size of view, default to max of 1000 data points
* Slider control to move view, i.e. sliding window
* Do A FFT to get audio spectrum data [DONE]
* Create spectrum analyzer display
* Implement "interesting data" logic... basically highest magnitude for certain frequency ranges within a given time slice
* Implement simple hashing based on Roy van Rijn's work
* Mock audio data
* Create unit tests (test hash lookups of mocked audio data)
* Use LSH (Locality Sensitive Hashing) to pick values to hash
* Use combinatorics to pick values to hash
* Use SDM (Sparse Distributed Memory) to pick values to hash
* Persist to DB with Hibernate (Database will be determined in class)
