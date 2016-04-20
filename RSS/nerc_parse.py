from bs4 import BeautifulSoup
import bs4

# TODO
# Metodo no completado
def getPersona(name):
	return name

# TODO
# Metodo no completado
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
		

def parse_nerc(i):
	xml = BeautifulSoup(open('temp/nerc.txt'), "xml")
	partidos = {}
	personas = {}
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
	print findBest(personas, partidos)
			
			
	
parse_nerc(0)
