from bs4 import BeautifulSoup
import bs4

# Devueleve si el puerto esta abierto o no
def check_port_open(port):
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
	result = sock.connect_ex(('127.0.0.1',port))
	if result == 0:
   		return True
	else:
   		return False


# Comprobamos que los servicios de ixa pipes estan en marcha.
if (not check_port_open(9002)):
	
	# Poner los ixa en mode server en marcha. Tardan un rato por lo que paramos unos segundos
	os.system('./ixa_init.sh')

	# Esperamos hasta que esten en marcha comprobando cada dos segundos
	ixa_ready = False
	while not ixa_ready:
		time.sleep(2)
		ixa_ready = check_port_open(9002)

# TODO
# Metodo no completado (Urkidi)
def getPersona(name):
	return name

# TODO
# Metodo no completado (Urkidi)
def getPartido(name):
	return name 


def findBest(personas, partidos):
	best = False
	best_times = 0
	print personas
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
	return best
		

def parse_nerc(path):
	xml = BeautifulSoup(open(path), "xml")
	partidos = {}
	personas = {}
	print xml
	for item in xml.entities:
		if type(item) == bs4.element.Tag:
			if item['type'] == 'PER' or item['type'] == 'ORG':
				name = str(item.references)
				name = name[name.find('<!--') + len('<!--'): name.find('-->')]
				
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

if len(sys.argv) < 2:
	sys.exit("\nError en numero de parametros\n\nPara ejecutar este script debes usar un parametros:\n1-URL de la RSS\n")
			
