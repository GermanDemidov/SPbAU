JFLAGS = -g -server
JC = javac
MAIN = ru.spbau.demidovg.geometry.chan_algo.Main

src = src
builddir = out
package = ru/spbau/demidovg/geometry/chan_algo

.SUFFIXES: .java .class
CLASSES = $(wildcard $(src)/$(package)/*.java)

default: $(builddir) classes

$(builddir):
	mkdir -p $(builddir)

classes: $(CLASSES:.java=.class)

.java.class:
	$(JC) $(JFLAGS) $*.java -d $(builddir) -cp $(src)

run:
	java -cp $(builddir) $(MAIN)

clean:
	rm -rf $(builddir)

.PHONY: clean
