import sys
import os
import urllib2
from bs4 import BeautifulSoup

if len(sys.argv) != 3:
	sys.exit("\nError en numero de parametros\n\nPara ejecutar este script debes usar dos parametros:\n1-URL de la RSS\n2-Carpeta donde se guardaran los resultados\n")

# http://www.crummy.com/software/BeautifulSoup/bs4/doc/

# Leer el codigo html
rss_xml = urllib2.urlopen(sys.argv[1]).read()

rss = BeautifulSoup(rss_xml, "xml")

i = 0

# Cada item representa una noticia
for item in rss.findAll('item'):
	page = urllib2.urlopen(item.link.get_text()).read()

	soup = BeautifulSoup(page, 'html.parser')

	# No estamos interesados en los estilos y codigos de script
	# Eliminamos todo lo relacionado con css y javascript
	for script in soup(["script", "style"]):
    		script.extract()

	# Buscar el itemprop="articleBody". Aqui se encuentra el articulo. ElPais, ElMundo, ABC parece que hacen uso de este indicador
	# https://developers.google.com/structured-data/
	#
	body = soup.find(itemprop="articleBody")
	if body:
		result = body.get_text()

		f = open(sys.argv[2]+str(i)+'.txt', 'w')
		f.write(result.encode('utf8'))
		f.close
		i+=1
