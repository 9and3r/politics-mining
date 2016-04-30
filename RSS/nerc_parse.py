from bs4 import BeautifulSoup
import bs4
import os
import socket
import time
import sys
from subprocess import Popen, PIPE

# Devueleve si el puerto esta abierto o no
def check_port_open(port):
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	result = sock.connect_ex(('127.0.0.1',port))
	if result == 0:
   		return True
	else:
   		return False

# TODO
# Metodo no completado (Urkidi)
def getPersona(name):
	return name

# TODO
# Metodo no completado (Urkidi)
def getPartido(name):
	return name 

# Busca la persona/partido que mas se menciona. Hay que pasarle dos diccionarios. 
# personas: diccionario que contiene como keys cada una de las personas del texto y el valor es el numero de veces que aparece
# partidos: diccionario que contiene como keys cada una de las partidos del texto y el valor es el numero de veces que aparece
def findBest(personas, partidos):
	best = False
	best_times = 0
	for key in personas:
		if best:
			if best_times < personas[key]:
				best = key
				best_times = personas[key]
		else:
			best = key
			best_times = personas[key]
	if not best:
		for key in personas:
			if best:
				if best_times < partidos[key]:
					best = key
					best_times = partidos[key]
			else:
				best = key
				best_times = partidos[key]

# Procesa el fichero generado por ixa-pipes-nerc y devuelve de quien/que se habla
def parse_nerc(path):
	xml = BeautifulSoup(open(path), "xml")
	partidos = {}
	personas = {}
	if xml.entities:
		for item in xml.entities:
			if type(item) == bs4.element.Tag:
				if item['type'] == 'PER' or item['type'] == 'ORG':
					name = str(item.references)
					name = name[name.find('<!--') + len('<!--'): name.find('-->')]
					print name
				
					if item['type'] == 'PER':
						key = getPersona(name)
						try:
							personas[key] += 1
						except KeyError:
							personas[key] = 1
					else:
						try:
							partidos[name] += 1
						except KeyError:
							partidos[name] = 1
	return findBest(personas, partidos)


# Procesa el fichero que le pasamos y añade de quien/que se habla como informacion extra al fichero
def nerc(path):

	# Leemos el fichero
	f = open(path, 'r')
	text = f.read()
	
	# Dejamos solo el texto eliminando la informacion extra
	text = text[:text.index('##########')]

	# Pasamos los ixa pipes para detectar nombres
	try:
		p2 = Popen('echo "' + text + '" | java -jar ./ixa-pipes/ixa-pipe-tok.jar client -p 9000', stdout=PIPE, shell=True)
		p3 = Popen('java -jar ./ixa-pipes/ixa-pipe-pos.jar client -p 9001', stdin=p2.stdout, stdout=PIPE, shell=True)
		p4 = Popen('java -jar ./ixa-pipes/ixa-pipe-nerc.jar client -p 9002 > temp/nerc.txt', stdin=p3.stdout, shell=True)
		p4.wait()
	except:
	f.close()

	# Comprobamos de quien se habla en el texto usando la informacion del ixa-pipe-nerc
	hablaDe = parse_nerc('temp/nerc.txt')

	if hablaDe:
		# Añadimos la nueva informacion al fichero
		f = open(path, 'a')
		f.write('about=' + hablaDe + '\n')
		f.close()
	else:
		# El fichero no nos interesa por lo que sera eliminado
		os.remove(path)
		print "No se ha encontrado ningun politico o partido. El archivo se ha eliminado: " + path + "\n"

if len(sys.argv) != 2:
	sys.exit("\nError en numero de parametros\n\nPara ejecutar este script debes usar un parametros:\n1-Carpeta de ficheros de entrada\n")

# Comprobamos que los servicios de ixa pipes estan en marcha.
if (not check_port_open(9002)):
	
	# Poner los ixa en mode server en marcha. Tardan un rato por lo que paramos unos segundos
	os.system('./ixa-pipes/ixa_init.sh')

	# Esperamos hasta que esten en marcha comprobando cada dos segundos
	ixa_ready = False
	while not ixa_ready:
		time.sleep(2)
		ixa_ready = check_port_open(9002)

# Procesamos todos los ficheros que se encuentran en el path
for currentFile in os.listdir(sys.argv[1]):
	nerc(sys.argv[1] + currentFile)



			
